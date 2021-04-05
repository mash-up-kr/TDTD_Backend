package mashup.backend.tdtd.common.exception

import org.springframework.http.HttpStatus

class UnauthorizedException(
    val exceptionType: ExceptionType
) : BaseException(exceptionType.code, exceptionType.message, HttpStatus.UNAUTHORIZED)