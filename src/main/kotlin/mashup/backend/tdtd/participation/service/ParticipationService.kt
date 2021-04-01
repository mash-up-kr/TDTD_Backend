package mashup.backend.tdtd.participation.service

import mashup.backend.tdtd.participation.entity.Participation
import mashup.backend.tdtd.participation.repository.ParticipationRepository
import mashup.backend.tdtd.room.service.RoomService
import mashup.backend.tdtd.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ParticipationService(
    private val participationRepository: ParticipationRepository,
    private val userService: UserService,
    private val roomService: RoomService
) {

    @Transactional
    fun saveParticipation(deviceId: String, roomCode: String) {
        val userId: Long = userService.getUserByDeviceId(deviceId).id!!
        val roomId: Long = roomService.getRoomByRoomCode(roomCode).id!!
        participationRepository.findByRoomIdAndUserId(roomId, userId)
            ?: participationRepository.save(Participation(roomId = roomId, userId = userId))
    }

    @Transactional
    fun deleteParticipation(deviceId: String, roomCode: String) {
        val userId: Long = userService.getUserByDeviceId(deviceId).id!!
        val roomId: Long = roomService.getRoomByRoomCode(roomCode).id!!
        participationRepository.deleteByRoomIdAndUserId(roomId, userId)
    }

    fun getParticipationRandomLimitOne() =
        participationRepository.findByRandomLimitOne()

    fun getParticipationListByRoomId(roomId: Long) =
        participationRepository.findAllByRoomId(roomId)
}