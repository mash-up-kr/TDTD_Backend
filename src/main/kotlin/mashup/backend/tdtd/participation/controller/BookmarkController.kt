package mashup.backend.tdtd.participation.controller

import mashup.backend.tdtd.participation.service.BookmarkService
import mashup.backend.tdtd.room.dto.RoomResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/bookmarks")
@RestController
class BookmarkController(
    private val bookmarkService: BookmarkService) {

    @GetMapping
    fun getBookmarkedRooms(
        @RequestHeader("Device-Id") deviceId: String
    ): ResponseEntity<List<RoomResponse>> {
        val response: List<RoomResponse> = bookmarkService.getBookmarkedRooms(deviceId)
        return ResponseEntity.ok().body(response)
    }

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