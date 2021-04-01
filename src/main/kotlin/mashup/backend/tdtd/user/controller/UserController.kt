package mashup.backend.tdtd.user.controller

import mashup.backend.tdtd.common.entity.ResponseWrapper
import mashup.backend.tdtd.participation.service.ParticipationService
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/users")
@RestController
class UserController(
    private val participationService: ParticipationService
) {

    @PostMapping("/{roomCode}")
    fun joinUserInRoom(
        @RequestHeader("Device-Id") deviceId: String,
        @PathVariable roomCode: String
    ): ResponseWrapper<Unit> {
        participationService.saveParticipation(deviceId, roomCode)
        return ResponseWrapper.wrappedSuccessResponse(Unit)
    }

    @DeleteMapping("/{roomCode}")
    fun leaveUserInRoom(
        @RequestHeader("Device-Id") deviceId: String,
        @PathVariable roomCode: String
    ): ResponseWrapper<Unit> {
        participationService.deleteParticipation(deviceId, roomCode)
        return ResponseWrapper.wrappedSuccessResponse(Unit)
    }
}