package mashup.backend.tdtd.host.controller

import mashup.backend.tdtd.comment.service.CommentService
import mashup.backend.tdtd.common.dto.ExceptionResponse
import mashup.backend.tdtd.room.service.RoomService
import org.springframework.http.ResponseEntity
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
    ): ResponseEntity<Void> {
        return try {
            roomService.deleteRoom(deviceId, roomCode)
            ResponseEntity.ok().build()
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @DeleteMapping("/comments/{commentId}")
    fun deleteComment(
        @RequestHeader("Device-Id") deviceId: String,
        @PathVariable commentId: Long
    ): ResponseEntity<Any> {
        return try{
            commentService.deleteCommentByHost(commentId)
            ResponseEntity.ok().build()
        }catch (e: Exception) {
            ResponseEntity.badRequest().body(ExceptionResponse(errMsg = e.message!!))
        }
    }
}