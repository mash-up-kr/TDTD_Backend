package mashup.backend.tdtd.common.exception

import mashup.backend.tdtd.common.entity.Log
import mashup.backend.tdtd.common.entity.ResponseWrapper
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionAdvice {
    companion object : Log

    @ExceptionHandler(BaseException::class)
    fun handleBaseException(exception: BaseException): ResponseWrapper<Unit> {
        logger.error("code: ${exception.exceptionType.code} - message: ${exception.exceptionType.message}")
        return ResponseWrapper.wrappedExceptionResponse(exception.httpStatus, exception.exceptionType)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleUnexpectedException(exception: RuntimeException): ResponseWrapper<Unit> {
        logger.error(exception.stackTraceToString())
        return ResponseWrapper.wrappedExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionType.UNEXPECTED)
    }
}