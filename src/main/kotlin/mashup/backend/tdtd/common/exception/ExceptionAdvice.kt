package mashup.backend.tdtd.common.exception

import mashup.backend.tdtd.common.entity.Log
import mashup.backend.tdtd.common.entity.ResponseWrapper
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionAdvice {

    @ExceptionHandler(BaseException::class)
    fun handleBaseException(ex: BaseException): ResponseWrapper<Unit> {
        Log.printErrorLog(ex.code, ex.message)
        return ResponseWrapper.wrappedExceptionResponse(ex.code, ex.message, ex.httpStatus)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleUnexpectedException(ex: RuntimeException): ResponseWrapper<Unit> {
        Log.printErrorLog(ex.stackTraceToString())
        return ResponseWrapper.wrappedExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionType.UNEXPECTED)
    }
}