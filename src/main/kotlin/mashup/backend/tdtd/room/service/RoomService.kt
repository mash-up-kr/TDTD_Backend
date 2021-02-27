package mashup.backend.tdtd.room.service

import mashup.backend.tdtd.room.entity.Room
import mashup.backend.tdtd.room.repository.RoomRepository
import org.springframework.stereotype.Service

@Service
class RoomService(
    private val roomRepository: RoomRepository) {

    fun getRoomByRoomCode(roomCode: String): Room {
        return roomRepository.findByRoomCode(roomCode)!!
    }
}