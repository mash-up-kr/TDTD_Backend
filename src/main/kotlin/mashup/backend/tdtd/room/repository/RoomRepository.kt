package mashup.backend.tdtd.room.repository

import mashup.backend.tdtd.room.entity.Room
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RoomRepository : JpaRepository<Room, Long> {
    fun findByRoomCode(roomCode: String): Room?
    fun findByIdIn(ids: List<Long>): List<Room>
}