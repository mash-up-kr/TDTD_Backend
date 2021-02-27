package mashup.backend.tdtd.room.service

import mashup.backend.tdtd.common.util.UuidManager
import mashup.backend.tdtd.room.dto.CreateRoomRequest
import mashup.backend.tdtd.room.dto.CreateRoomResponse
import mashup.backend.tdtd.room.entity.Room
import mashup.backend.tdtd.room.entity.RoomType
import mashup.backend.tdtd.room.repository.RoomRepository
import mashup.backend.tdtd.participation.entity.Participation
import mashup.backend.tdtd.participation.repository.ParticipationRepository
import mashup.backend.tdtd.room.dto.RoomResponse
import mashup.backend.tdtd.user.entity.User
import mashup.backend.tdtd.user.service.UserService
import org.springframework.stereotype.Service

@Service
class RoomService(
    private val roomRepository: RoomRepository,
    private val userService: UserService,
private val participationRepository: ParticipationRepository,
) {
    fun createRoom(
        deviceId: String,
        createRoomRequest: CreateRoomRequest,
    ): CreateRoomResponse {

        val savedRoom: Room = roomRepository.save(
            Room(
                hostId = userService.getUserByDeviceId(deviceId).id!!,
                title = createRoomRequest.title,
                type = RoomType.valueOf(createRoomRequest.type),
                roomCode = UuidManager.getBase64Uuid(),
            )
        )

        return CreateRoomResponse(
            roomCode = savedRoom.roomCode
        )
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
                room_code = roomMap[participation.roomId]?.roomCode!!,
                isBookmark = participation.bookmark,
                created_at = roomMap[participation.roomId]?.createdAt!!,
            )
        }
    }
}