package mashup.backend.tdtd.common.dto

import mashup.backend.tdtd.common.exception.ExceptionType

class BaseResponse<T>(
    var code: Int,
    var message: String?,
    var result: T?
) {
    constructor(exceptionType: ExceptionType, result: T?) : this(exceptionType.code, exceptionType.message, result)
}