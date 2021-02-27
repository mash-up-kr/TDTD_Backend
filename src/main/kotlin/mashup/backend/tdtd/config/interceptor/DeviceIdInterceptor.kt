package mashup.backend.tdtd.config.interceptor

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class DeviceIdInterceptor : HandlerInterceptor{
    companion object {
        const val DEVICE_ID_KEY_IN_HEADER = "Device-Id"
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        // Request header에서 deviceId 가져오기(null일 경우 컨트롤러로 진입 X)
        val deviceId : String? = request.getHeader(DEVICE_ID_KEY_IN_HEADER)
        if(deviceId.isNullOrBlank()) {
            response.sendError(HttpStatus.UNAUTHORIZED.value())
            return false;
        }
        return true
    }
}