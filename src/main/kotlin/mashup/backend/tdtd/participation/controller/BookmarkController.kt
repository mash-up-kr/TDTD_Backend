package mashup.backend.tdtd.participation.controller

import mashup.backend.tdtd.participation.service.BookmarkService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/bookmarks")
@RestController
class BookmarkController(
    private val bookmarkService: BookmarkService) {

    @PostMapping("/{roomCode}")
    fun addBookmarkToRoom(
        @RequestHeader("Device-Id") deviceId: String,
        @PathVariable roomCode: String
    ): ResponseEntity<Void> {
        try {
            bookmarkService.updateBookmarkStatus(deviceId, roomCode, true)
        } catch (e: NullPointerException) {
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{roomCode}")
    fun removeBookmarkInRoom(
        @RequestHeader("Device-Id") deviceId: String,
        @PathVariable roomCode: String
    ): ResponseEntity<Void> {
        try {
            bookmarkService.updateBookmarkStatus(deviceId, roomCode, false)
        } catch (e: NullPointerException) {
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.ok().build()
    }
}