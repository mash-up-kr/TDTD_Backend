package mashup.backend.tdtd.host.controller

import mashup.backend.tdtd.comment.service.CommentService
import mashup.backend.tdtd.common.entity.ResponseWrapper
import mashup.backend.tdtd.room.service.RoomService
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/host")
@RestController
class HostController(
    private val roomService: RoomService,
    private val commentService: CommentService
) {

    @DeleteMapping("/rooms/{roomCode}")
    fun deleteRoom(
        @RequestHeader("Device-Id") deviceId: String,
        @PathVariable roomCode: String
    ): ResponseWrapper<Unit> {
        roomService.deleteRoom(roomCode)
        return ResponseWrapper.wrappedSuccessResponse(Unit)
    }

    @DeleteMapping("/comments/{commentId}")
    fun deleteComment(
        @RequestHeader("Device-Id") deviceId: String,
        @PathVariable commentId: Long
    ): ResponseWrapper<Unit> {
        commentService.deleteCommentByHost(commentId)
        return ResponseWrapper.wrappedSuccessResponse(Unit)
    }
}