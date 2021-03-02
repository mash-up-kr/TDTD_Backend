package mashup.backend.tdtd.host.controller

import mashup.backend.tdtd.room.service.RoomService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/host")
@RestController
class HostController(
    private val roomService: RoomService) {

    @DeleteMapping("/rooms/{roomCode}")
    fun deleteRoom(
        @RequestHeader("Device-Id") deviceId: String,
        @PathVariable roomCode: String
    ): ResponseEntity<Void> {
        try {
            roomService.deleteRoom(deviceId, roomCode)
        } catch (e: Exception) {
            return ResponseEntity.badRequest().build()
        }
        return ResponseEntity.ok().build()
    }
}