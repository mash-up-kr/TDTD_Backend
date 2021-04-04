package mashup.backend.tdtd.comment.controller

import mashup.backend.tdtd.comment.dto.CreateCommentRequest
import mashup.backend.tdtd.comment.dto.CreateCommentResponse
import mashup.backend.tdtd.comment.entity.MessageType
import mashup.backend.tdtd.comment.service.CommentService
import mashup.backend.tdtd.common.entity.ResponseWrapper
import org.springframework.http.ResponseEntity
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

    @PostMapping("/{roomCode}")
    fun createComment(
        @RequestHeader("Device-Id") deviceId: String,
        @PathVariable("roomCode") roomCode: String,
        requestParams: CreateCommentRequest,
    ): ResponseEntity<CreateCommentResponse> {
        // todo: remove code duplication in createTextComment, createVoiceComment
        val result: CreateCommentResponse = when (requestParams.message_type) {
            MessageType.TEXT -> commentService.createTextComment(
                requestParams,
                deviceId,
                roomCode
            )
            MessageType.VOICE -> commentService.createVoiceComment(
                requestParams,
                deviceId,
                roomCode
            )
        }
        return ResponseEntity.ok(result)
    }
}