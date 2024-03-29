package mashup.backend.tdtd.config.interceptor

import mashup.backend.tdtd.participation.service.ParticipationService
import mashup.backend.tdtd.user.service.UserService
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class UserRegistrationInterceptor(
    private val userService: UserService,
    private val participationService: ParticipationService
) : HandlerInterceptor {
    companion object {
        const val DEVICE_ID_KEY_IN_HEADER = "Device-Id"
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if ((request.method == HttpMethod.GET.name).not()) return true
        val deviceId: String = request.getHeader(DEVICE_ID_KEY_IN_HEADER)
        if (userService.isExistDeviceId(deviceId).not()) userService.createUser(deviceId)

        val requestPath = request.servletPath.replace("/api/v1/rooms", "")
        if (requestPath != "") {
            val roomCode = requestPath.substring(1)
            participationService.saveParticipation(deviceId, roomCode)
        }
        return true
    }
}