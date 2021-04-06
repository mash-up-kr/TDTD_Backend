package mashup.backend.tdtd.common.exception

import org.springframework.http.HttpStatus

class ForbiddenException(
    val exceptionType: ExceptionType
) : BaseException(exceptionType.code, exceptionType.message, HttpStatus.FORBIDDEN)