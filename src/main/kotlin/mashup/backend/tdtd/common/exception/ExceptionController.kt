package mashup.backend.tdtd.common.exception

import mashup.backend.tdtd.common.dto.BaseResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class ExceptionController {

    @RequestMapping("/api/error")
    fun <T> handleExceptionFromInterceptor(request: HttpServletRequest): ResponseEntity<BaseResponse<T>> {
        val exception: String = request.getAttribute("exception").toString()
        val exceptionType: ExceptionType = ExceptionType.valueOf(request.getAttribute("exceptionType").toString())

        when (exception) {
            "BadRequestException" -> throw BadRequestException(exceptionType)
            "UnauthorizedException" -> throw UnauthorizedException(exceptionType)
            "ForbiddenException" -> throw ForbiddenException(exceptionType)
            "NotFoundException" -> throw NotFoundException(exceptionType)
            else -> throw RuntimeException()
        }
    }
}