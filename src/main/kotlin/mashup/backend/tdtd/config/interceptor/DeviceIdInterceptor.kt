package mashup.backend.tdtd.config.interceptor

import mashup.backend.tdtd.common.exception.ExceptionType
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class DeviceIdInterceptor : HandlerInterceptor {
    companion object {
        const val DEVICE_ID_KEY_IN_HEADER = "Device-Id"
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val deviceId: String? = request.getHeader(DEVICE_ID_KEY_IN_HEADER)
        if (deviceId.isNullOrBlank()) {
            request.setAttribute("exception", "BadRequestException")
            request.setAttribute("exceptionType", ExceptionType.DEVICE_BAD_REQUEST)
            request.getRequestDispatcher("/api/error").forward(request, response)
            return false
        }
        return true
    }
}