package mashup.backend.tdtd.common.exception

import org.springframework.http.HttpStatus

class ForbiddenException(exceptionType: ExceptionType) :
    BaseException(HttpStatus.FORBIDDEN, exceptionType)