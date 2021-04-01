package mashup.backend.tdtd.report.controller

import mashup.backend.tdtd.common.entity.ResponseWrapper
import mashup.backend.tdtd.report.service.ReportService
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/reports")
@RestController
class ReportController(
    private val reportService: ReportService
) {

    @PostMapping("/{commentId}")
    fun reportComment(
        @RequestHeader("Device-Id") deviceId: String,
        @PathVariable commentId: Long
    ): ResponseWrapper<Unit> {
        reportService.reportCommentByCommentId(commentId, deviceId)
        return ResponseWrapper.wrappedSuccessResponse(Unit)
    }
}