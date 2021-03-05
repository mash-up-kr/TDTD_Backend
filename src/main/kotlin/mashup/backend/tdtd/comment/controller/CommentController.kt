package mashup.backend.tdtd.comment.controller

import mashup.backend.tdtd.comment.service.CommentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/comments")
@RestController
class CommentController(
    private val commentService: CommentService
) {
    @DeleteMapping("/{commentId}")
    fun deleteComment (
        @RequestHeader("Device-Id") deviceId: String,
        @PathVariable("commentId") commentId: Long,
    ) : ResponseEntity<Void> {
        commentService.deleteCommentByCommentId(commentId)
        return ResponseEntity.ok().build()
    }
}