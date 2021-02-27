package mashup.backend.tdtd.room.controller

import mashup.backend.tdtd.room.dto.CreateRoomRequest
import mashup.backend.tdtd.room.dto.CreateRoomResponse
import mashup.backend.tdtd.room.service.RoomService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/room")
@RestController
class RoomController(
    private val roomService: RoomService,
) {
    @PostMapping
    fun createRoom(
        @RequestHeader("Device-Id") deviceId: String,
        @RequestBody createRoomRequest: CreateRoomRequest,
    ): ResponseEntity<CreateRoomResponse> {
        return ResponseEntity.ok()
            .body(roomService.createRoom(deviceId, createRoomRequest))
    }
}