package mashup.backend.tdtd.participation.service

import mashup.backend.tdtd.participation.entity.Participation
import mashup.backend.tdtd.participation.repository.ParticipationRepository
import mashup.backend.tdtd.room.dto.RoomResponse
import mashup.backend.tdtd.room.entity.Room
import mashup.backend.tdtd.room.repository.RoomRepository
import mashup.backend.tdtd.room.service.RoomService
import mashup.backend.tdtd.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookmarkService(
    private val participationRepository: ParticipationRepository,
    private val roomRepository: RoomRepository,
    private val userService: UserService,
    private val roomService: RoomService) {

    fun getBookmarkedRooms(deviceId: String): List<RoomResponse> {
        val userId: Long = userService.getUserByDeviceId(deviceId).id!!
        val participationList: List<Participation> = participationRepository.findByUserIdAndBookmark(userId!!, true)
        val roomIdList: List<Long> = participationList.map { it.roomId }.toList()
        val roomList: List<Room> = roomRepository.findByIdIn(roomIdList)
        val roomMap: Map<Long, Room> = roomList.map { it.id!! to it }.toMap()
        return participationList.map { participation ->
            RoomResponse(
                isHost = roomMap[participation.roomId]?.hostId == userId,
                title = roomMap[participation.roomId]?.title,
                roomCode = roomMap[participation.roomId]?.roomCode!!,
                isBookmark = participation.bookmark,
                createdAt = roomMap[participation.roomId]?.createdAt!!,
            )
        }
    }

    @Transactional
    fun updateBookmarkStatus(deviceId: String, roomCode: String, bookmark: Boolean) {
        val userId: Long = userService.getUserByDeviceId(deviceId).id!!
        val roomId: Long = roomService.getRoomByRoomCode(roomCode).id!!
        val participation: Participation = participationRepository.findByRoomIdAndUserId(roomId, userId)!!
        participation.updateBookmark(bookmark)
    }
}