package mashup.backend.tdtd.common.exception

import org.springframework.http.HttpStatus

class UnauthorizedException(exceptionType: ExceptionType) :
    BaseException(HttpStatus.UNAUTHORIZED, exceptionType)