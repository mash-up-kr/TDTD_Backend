package mashup.backend.tdtd.participation.repository

import mashup.backend.tdtd.participation.entity.Participation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface ParticipationRepository : JpaRepository<Participation, Long> {
    fun findByRoomIdAndUserId(roomId: Long, userId: Long): Participation?
    fun findByUserId(userId: Long): List<Participation>
    fun findByUserIdAndBookmark(userId: Long, bookmark: Boolean): List<Participation>
    @Query(value = "select * from Participation group by rand() LIMIT 1", nativeQuery = true)
    fun findByRandomLimitOne(): Participation
    fun findAllByRoomId(roomId: Long): List<Participation>
    fun deleteByRoomIdAndUserId(roomId: Long, userId: Long)
    @Modifying(clearAutomatically = true)
    @Query("update Participation p set p.deletedAt=current_timestamp where p.roomId=:roomId")
    fun deleteAllByRoomId(roomId: Long)
}