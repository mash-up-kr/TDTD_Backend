package mashup.backend.tdtd.room.repository

import mashup.backend.tdtd.room.entity.Room
import org.springframework.data.jpa.repository.JpaRepository

interface RoomRepository : JpaRepository<Room, Long> {
    fun findByRoomCode(roomCode: String): Room?
}