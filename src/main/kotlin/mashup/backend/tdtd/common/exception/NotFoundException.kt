package mashup.backend.tdtd.common.exception

import org.springframework.http.HttpStatus

class NotFoundException(
    val exceptionType: ExceptionType
) : BaseException(exceptionType.code, exceptionType.message, HttpStatus.NOT_FOUND)