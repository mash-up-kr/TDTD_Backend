package mashup.backend.tdtd.common.exception

import org.springframework.http.HttpStatus

class NotFoundException(exceptionType: ExceptionType) :
    BaseException(HttpStatus.NOT_FOUND, exceptionType)