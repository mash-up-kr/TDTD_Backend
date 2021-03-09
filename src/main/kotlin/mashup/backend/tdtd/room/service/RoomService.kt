package mashup.backend.tdtd.room.service

import mashup.backend.tdtd.comment.dto.CommentResponse
import mashup.backend.tdtd.comment.repository.CommentRepository
import mashup.backend.tdtd.comment.service.CommentService
import mashup.backend.tdtd.common.util.DeepLinkGenerator
import mashup.backend.tdtd.common.util.UuidManager
import mashup.backend.tdtd.participation.entity.Participation
import mashup.backend.tdtd.participation.repository.ParticipationRepository
import mashup.backend.tdtd.room.dto.CreateRoomRequest
import mashup.backend.tdtd.room.dto.CreateRoomResponse
import mashup.backend.tdtd.room.dto.RoomDetailResponse
import mashup.backend.tdtd.room.dto.RoomResponse
import mashup.backend.tdtd.room.entity.Room
import mashup.backend.tdtd.room.entity.RoomType
import mashup.backend.tdtd.room.repository.RoomRepository
import mashup.backend.tdtd.user.entity.User
import mashup.backend.tdtd.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException
import java.security.InvalidParameterException

@Service
class RoomService(
    private val roomRepository: RoomRepository,
    private val participationRepository: ParticipationRepository,
    private val commentRepository: CommentRepository,
    private val userService: UserService,
    private val commentService: CommentService,
) {

    fun createRoom(
        deviceId: String,
        createRoomRequest: CreateRoomRequest,
    ): CreateRoomResponse {
        val roomCode: String = UuidManager.getBase64Uuid()
        val savedRoom: Room = roomRepository.save(
            Room(
                hostId = userService.getUserByDeviceId(deviceId).id!!,
                title = createRoomRequest.title,
                type = RoomType.valueOf(createRoomRequest.type),
                roomCode = roomCode,
                shareUrl = DeepLinkGenerator.getDeepLink(roomCode)
            )
        )

        return CreateRoomResponse(roomCode = savedRoom.roomCode)
    }

    fun getRoomByRoomCode(roomCode: String): Room {
        return roomRepository.findByRoomCode(roomCode)!!
    }

    fun getRooms(deviceId: String): List<RoomResponse> {
        val user: User = userService.getUserByDeviceId(deviceId)
        val participationList: List<Participation> = participationRepository.findByUserId(user.id!!)
        val roomIdList: List<Long> = participationList.map { it.roomId }.toList()
        val roomList: List<Room> = roomRepository.findByIdIn(roomIdList)
        val roomMap: Map<Long, Room> = roomList.map { it.id!! to it }.toMap()
        return participationList.map { participation ->
            RoomResponse(
                isHost = roomMap[participation.roomId]?.hostId == user.id,
                title = roomMap[participation.roomId]?.title,
                roomCode = roomMap[participation.roomId]?.roomCode!!,
                isBookmark = participation.bookmark,
                createdAt = roomMap[participation.roomId]?.createdAt!!,
            )
        }
    }

    fun getRoomById(roomId: Long): Room =
        roomRepository.findById(roomId).orElseThrow{ IllegalArgumentException() }

    fun getRoomDetailByRoomCode(deviceId: String, roomCode: String): RoomDetailResponse {
        val room: Room = this.getRoomByRoomCode(roomCode)
        val user: User = userService.getUserByDeviceId(deviceId)
        val comments: List<CommentResponse> = commentService.getCommentListByRoomId(deviceId, room.id!!)

        return RoomDetailResponse(
            title = room.title,
            type = room.type,
            isHost = (room.hostId == user.id),
            comments = comments
        )
    }

    @Transactional
    fun deleteRoom(deviceId: String, roomCode: String) {
        val userId: Long = userService.getUserByDeviceId(deviceId).id!!
        val room: Room = this.getRoomByRoomCode(roomCode)
        userService.validateHostUser(userId, room.hostId)
        commentRepository.deleteAllByRoomId(room.id!!)
        participationRepository.deleteAllByRoomId(room.id!!)
        roomRepository.deleteById(room.id!!)
    }
}