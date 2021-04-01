package mashup.backend.tdtd.room.controller

import mashup.backend.tdtd.common.entity.ResponseWrapper
import mashup.backend.tdtd.room.dto.CreateRoomRequest
import mashup.backend.tdtd.room.dto.CreateRoomResponse
import mashup.backend.tdtd.room.dto.RoomDetailResponse
import mashup.backend.tdtd.room.dto.RoomListResponse
import mashup.backend.tdtd.room.service.RoomService
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/rooms")
@RestController
class RoomController(
    private val roomService: RoomService
) {

    @GetMapping
    fun getRooms(
        @RequestHeader("Device-Id") deviceId: String
    ): ResponseWrapper<List<RoomListResponse>> {
        val listResponse: List<RoomListResponse> = roomService.getRooms(deviceId)
        return ResponseWrapper.wrappedSuccessResponse(listResponse)
    }

    @PostMapping
    fun createRoom(
        @RequestHeader("Device-Id") deviceId: String,
        @RequestBody createRoomRequest: CreateRoomRequest,
    ): ResponseWrapper<CreateRoomResponse> {
        val response: CreateRoomResponse = roomService.createRoom(deviceId, createRoomRequest)
        return ResponseWrapper.wrappedSuccessResponse(response)
    }

    @GetMapping("/{roomCode}")
    fun getRoomDetail(
        @RequestHeader("Device-Id") deviceId: String,
        @PathVariable roomCode: String
    ): ResponseWrapper<RoomDetailResponse> {
        val response: RoomDetailResponse = roomService.getRoomDetailByRoomCode(deviceId, roomCode)
        return ResponseWrapper.wrappedSuccessResponse(response)
    }
}