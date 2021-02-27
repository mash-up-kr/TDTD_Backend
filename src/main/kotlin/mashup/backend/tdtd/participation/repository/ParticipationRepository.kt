package mashup.backend.tdtd.participation.repository

import mashup.backend.tdtd.participation.entity.Participation
import org.springframework.data.jpa.repository.JpaRepository

interface ParticipationRepository : JpaRepository<Participation, Long> {
    fun findByRoomIdAndUserId(roomId: Long, userId: Long): Participation?
    fun findByUserId(userId: Long): List<Participation>
    fun findByUserIdAndBookmark(userId: Long, bookmark: Boolean): List<Participation>
    fun deleteByRoomIdAndUserId(roomId: Long, userId: Long)
}