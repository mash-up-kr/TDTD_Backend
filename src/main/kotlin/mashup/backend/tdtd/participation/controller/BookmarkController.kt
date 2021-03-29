package mashup.backend.tdtd.participation.controller

import mashup.backend.tdtd.common.entity.ResponseWrapper
import mashup.backend.tdtd.participation.service.BookmarkService
import mashup.backend.tdtd.room.dto.RoomListResponse
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/bookmarks")
@RestController
class BookmarkController(
    private val bookmarkService: BookmarkService
) {
    companion object {
        const val BOOKMARKED = true
        const val UNMARKED = false
    }

    @GetMapping
    fun getBookmarkedRooms(
        @RequestHeader("Device-Id") deviceId: String
    ): ResponseWrapper<List<RoomListResponse>> {
        val listResponse: List<RoomListResponse> = bookmarkService.getBookmarkedRooms(deviceId)
        return ResponseWrapper.wrappedSuccessResponse(listResponse)
    }

    @PostMapping("/{roomCode}")
    fun addBookmarkToRoom(
        @RequestHeader("Device-Id") deviceId: String,
        @PathVariable roomCode: String
    ): ResponseWrapper<Unit> {
        bookmarkService.updateBookmarkStatus(deviceId, roomCode, BOOKMARKED)
        return ResponseWrapper.wrappedSuccessResponse(Unit)
    }

    @DeleteMapping("/{roomCode}")
    fun removeBookmarkInRoom(
        @RequestHeader("Device-Id") deviceId: String,
        @PathVariable roomCode: String
    ): ResponseWrapper<Unit> {
        bookmarkService.updateBookmarkStatus(deviceId, roomCode, UNMARKED)
        return ResponseWrapper.wrappedSuccessResponse(Unit)
    }
}