package mashup.backend.tdtd.common.entity

import mashup.backend.tdtd.common.dto.BaseResponse
import mashup.backend.tdtd.common.exception.ExceptionType
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity


class ResponseWrapper<T> : ResponseEntity<BaseResponse<T>> {
    private constructor(body: T, status: HttpStatus) : super(
        BaseResponse(2000, "success", body), null, status
    )

    private constructor(type: ExceptionType, status: HttpStatus) : super(
        BaseResponse(type, null), null, status
    )

    companion object {
        fun <T> wrappedSuccessResponse(body: T): ResponseWrapper<T> {
            return ResponseWrapper(body, HttpStatus.OK)
        }

        fun <T> wrappedExceptionResponse(
            status: HttpStatus,
            type: ExceptionType
        ): ResponseWrapper<T> {
            return ResponseWrapper(type, status)
        }
    }
}