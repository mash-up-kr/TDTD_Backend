package mashup.backend.tdtd.comment.repository

import mashup.backend.tdtd.comment.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {
}