package mashup.backend.tdtd.report.controller

import mashup.backend.tdtd.report.service.ReportService
import org.springframework.http.ResponseEntity
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
    ): ResponseEntity<Void> {
        return try {
            reportService.reportCommentByCommentId(commentId, deviceId)
            ResponseEntity.ok().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.badRequest().build()
        }
    }
}