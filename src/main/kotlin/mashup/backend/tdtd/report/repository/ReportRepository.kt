package mashup.backend.tdtd.report.repository

import mashup.backend.tdtd.report.entity.Report
import org.springframework.data.jpa.repository.JpaRepository

interface ReportRepository : JpaRepository<Report, Long> {
    fun existsByUserIdAndReportedCommentId(userId: Long, reportedCommentId: Long): Boolean
    fun countByReportedCommentId(commentId: Long): Long
}