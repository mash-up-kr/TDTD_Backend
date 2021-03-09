package mashup.backend.tdtd.room.controller

import mashup.backend.tdtd.room.dto.CreateRoomRequest
import mashup.backend.tdtd.room.dto.CreateRoomResponse
import mashup.backend.tdtd.room.dto.RoomDetailResponse
import mashup.backend.tdtd.room.dto.RoomListResponse
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
    ): ResponseEntity<List<RoomListResponse>> {
        val listResponse: List<RoomListResponse> = roomService.getRooms(deviceId)
        return ResponseEntity.ok().body(listResponse)
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
}