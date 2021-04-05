package mashup.backend.tdtd.common.exception

import org.springframework.http.HttpStatus

open class BaseException(
    val code: Int,
    override val message: String,
    val httpStatus: HttpStatus
) : RuntimeException()