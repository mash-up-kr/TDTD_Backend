package mashup.backend.tdtd.common.exception

import org.springframework.http.HttpStatus

class BadRequestException(
    val exceptionType: ExceptionType
) : BaseException(exceptionType.code, exceptionType.message, HttpStatus.BAD_REQUEST)