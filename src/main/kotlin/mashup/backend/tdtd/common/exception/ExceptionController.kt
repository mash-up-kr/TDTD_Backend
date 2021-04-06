package mashup.backend.tdtd.common.exception

import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServletRequest

@RestController
class ExceptionController : ErrorController {
    override fun getErrorPath(): String {
        return "/error"
    }

    @RequestMapping("/error")
    fun errorHandler(request: HttpServletRequest) {
        val expectedException = request.getAttribute("ExpectedException")?.toString()
        if (expectedException != null) {
            val exceptionType = ExceptionType.valueOf(expectedException)
            when (exceptionType.code / 10) {
                400 -> throw BadRequestException(exceptionType)
                401 -> throw UnauthorizedException(exceptionType)
                403 -> throw ForbiddenException(exceptionType)
                404 -> throw NotFoundException(exceptionType)
                else -> throw RuntimeException()
            }
        }

        val errorStateCode = Integer.parseInt(
            request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)?.toString()
                ?: throw RuntimeException())
        val errorMessage =
            request.getAttribute(RequestDispatcher.ERROR_MESSAGE)?.toString()
                ?: throw RuntimeException()
        throw BaseException(errorStateCode, errorMessage, HttpStatus.valueOf(errorStateCode))
    }
}
