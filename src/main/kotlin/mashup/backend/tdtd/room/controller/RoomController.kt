package mashup.backend.tdtd.room.controller

import mashup.backend.tdtd.room.dto.CreateRoomRequest
import mashup.backend.tdtd.room.dto.CreateRoomResponse
import mashup.backend.tdtd.room.service.RoomService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import mashup.backend.tdtd.room.dto.RoomResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/room")
@RestController
class RoomController(
    private val roomService: RoomService,
) {
    @RequestMapping("/api/v1/rooms")
    @RestController
    class RoomController(
        private val roomService: RoomService
    ) {

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
    }
}