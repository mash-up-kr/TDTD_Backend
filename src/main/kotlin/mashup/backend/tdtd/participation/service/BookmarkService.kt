package mashup.backend.tdtd.participation.service

import mashup.backend.tdtd.participation.entity.Participation
import mashup.backend.tdtd.participation.repository.ParticipationRepository
import mashup.backend.tdtd.room.service.RoomService
import mashup.backend.tdtd.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookmarkService(
    private val participationRepository: ParticipationRepository,
    private val userService: UserService,
    private val roomService: RoomService) {

    @Transactional
    fun updateBookmarkStatus(deviceId: String, roomCode: String, bookmark: Boolean) {
        val userId: Long = userService.getUserByDeviceId(deviceId).id!!
        val roomId: Long = roomService.getRoomByRoomCode(roomCode).id!!
        val participation: Participation = participationRepository.findByRoomIdAndUserId(roomId, userId)!!
        participation.updateBookmark(bookmark)
    }
}