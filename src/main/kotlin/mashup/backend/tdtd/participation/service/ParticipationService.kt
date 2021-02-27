package mashup.backend.tdtd.participation.service

import mashup.backend.tdtd.participation.entity.Participation
import mashup.backend.tdtd.participation.repository.ParticipationRepository
import mashup.backend.tdtd.room.service.RoomService
import mashup.backend.tdtd.user.service.UserService
import org.springframework.stereotype.Service

@Service
class ParticipationService(
    private val participationRepository: ParticipationRepository,
    private val userService: UserService,
    private val roomService: RoomService) {

    fun saveParticipation(deviceId: String, roomCode: String): Long {
        val userId: Long = userService.getUserByDeviceId(deviceId).id!!
        val roomId: Long = roomService.getRoomByRoomCode(roomCode).id!!
        val participationId: Long = participationRepository.save(Participation(userId = userId, roomId = roomId)).id!!
        return participationId;
    }
}