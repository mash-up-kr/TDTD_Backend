package mashup.backend.tdtd.config.interceptor

import mashup.backend.tdtd.user.service.UserService
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class RecentAccessInterceptor(
    private val userService: UserService
) : HandlerInterceptor {
    companion object {
        const val DEVICE_ID_KEY_IN_HEADER = "Device-Id"
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val deviceId: String = request.getHeader(DEVICE_ID_KEY_IN_HEADER)
        userService.updateUserLastUsedAt(deviceId)
        return true
    }
}