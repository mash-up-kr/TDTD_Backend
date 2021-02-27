package mashup.backend.tdtd.user.controller

import mashup.backend.tdtd.participation.service.ParticipationService
import mashup.backend.tdtd.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/users")
@RestController
class UserController(
    private val userService: UserService,
    private val participationService: ParticipationService) {

    @PostMapping("/{roomCode}")
    fun joinUserInRoom(
        @RequestHeader("Device-Id") deviceId: String,
        @PathVariable roomCode: String
    ): ResponseEntity<Void> {
        try {
            participationService.saveParticipation(deviceId, roomCode)
        } catch (e: NullPointerException) {
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{roomCode}")
    fun leaveUserInRoom(
        @RequestHeader("Device-Id") deviceId: String,
        @PathVariable roomCode: String
    ): ResponseEntity<Void> {
        try {
            participationService.deleteParticipation(deviceId, roomCode)
        } catch (e: NullPointerException) {
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.ok().build()
    }
}