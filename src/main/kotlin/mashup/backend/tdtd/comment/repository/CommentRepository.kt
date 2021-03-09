package mashup.backend.tdtd.comment.repository

import mashup.backend.tdtd.comment.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface CommentRepository : JpaRepository<Comment, Long> {
    @Modifying(clearAutomatically = true)
    @Query("update Comment c set c.deletedAt=current_timestamp where c.id=:commentId")
    override fun deleteById(commentId: Long)
    fun findAllByRoomIdAndIsBlindedIsFalse(roomId: Long): List<Comment>
    @Modifying(clearAutomatically = true)
    @Query("update Comment c set c.deletedAt=current_timestamp where c.roomId=:roomId")
    fun deleteAllByRoomId(roomId: Long)
    fun countByRoomId(roomId: Long): Int
}