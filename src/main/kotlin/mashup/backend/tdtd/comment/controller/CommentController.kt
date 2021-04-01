package mashup.backend.tdtd.comment.controller

import mashup.backend.tdtd.comment.service.CommentService
import mashup.backend.tdtd.common.entity.ResponseWrapper
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/comments")
@RestController
class CommentController(
    private val commentService: CommentService
) {

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @RequestHeader("Device-Id") deviceId: String,
        @PathVariable("commentId") commentId: Long
    ): ResponseWrapper<Unit> {
        commentService.deleteCommentByCommentId(deviceId, commentId)
        return ResponseWrapper.wrappedSuccessResponse(Unit)
    }
}