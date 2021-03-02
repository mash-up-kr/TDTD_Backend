package mashup.backend.tdtd.room.controller

import mashup.backend.tdtd.room.dto.CreateRoomRequest
import mashup.backend.tdtd.room.dto.CreateRoomResponse
import mashup.backend.tdtd.room.dto.RoomDetailResponse
import mashup.backend.tdtd.room.dto.RoomResponse
import mashup.backend.tdtd.room.service.RoomService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/rooms")
@RestController
class RoomController(
    private val roomService: RoomService) {

    @GetMapping
    fun getRooms(
        @RequestHeader("Device-Id") deviceId: String
    ): ResponseEntity<List<RoomResponse>> {
        val response: List<RoomResponse> = roomService.getRooms(deviceId)
        return ResponseEntity.ok().body(response)
    }

    @PostMapping
    fun createRoom(
        @RequestHeader("Device-Id") deviceId: String,
        @RequestBody createRoomRequest: CreateRoomRequest,
    ): ResponseEntity<CreateRoomResponse> {
        return ResponseEntity.ok()
            .body(roomService.createRoom(deviceId, createRoomRequest))
    }

    @GetMapping("/{roomCode}")
    fun getRoomDetail(
        @RequestHeader("Device-Id") deviceId: String,
        @PathVariable roomCode: String
    ): ResponseEntity<RoomDetailResponse> {
        val response: RoomDetailResponse = roomService.getRoomDetailByRoomCode(deviceId, roomCode)
        return ResponseEntity.ok().body(response)
    }

    @DeleteMapping("/{roomCode}")
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