package mashup.backend.tdtd.common.exception

import org.springframework.http.HttpStatus

class BadRequestException(exceptionType: ExceptionType) :
    BaseException(HttpStatus.BAD_REQUEST, exceptionType)