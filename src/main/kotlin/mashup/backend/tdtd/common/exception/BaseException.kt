package mashup.backend.tdtd.common.exception

import org.springframework.http.HttpStatus

open class BaseException(
    var httpStatus: HttpStatus,
    var exceptionType: ExceptionType
) : RuntimeException()