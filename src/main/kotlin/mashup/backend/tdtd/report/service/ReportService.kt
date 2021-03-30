package mashup.backend.tdtd.report.service

import mashup.backend.tdtd.comment.entity.Comment
import mashup.backend.tdtd.comment.service.CommentService
import mashup.backend.tdtd.common.exception.BadRequestException
import mashup.backend.tdtd.common.exception.ExceptionType
import mashup.backend.tdtd.common.exception.ForbiddenException
import mashup.backend.tdtd.report.entity.Report
import mashup.backend.tdtd.report.repository.ReportRepository
import mashup.backend.tdtd.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReportService(
    private val reportRepository: ReportRepository,
    private val userService: UserService,
    private val commentService: CommentService
) {
    companion object {
        const val BLINDED_CONDITION = 3;
        const val BLINDED_STATE = true;
    }

    @Transactional
    fun reportCommentByCommentId(commentId: Long, deviceId: String) {
        val userId: Long = userService.getUserByDeviceId(deviceId).id!!
        val reportedComment: Comment = commentService.getCommentById(commentId)
        if (userId == reportedComment.userId) throw ForbiddenException(ExceptionType.REPORT_COMMENT_FORBIDDEN)
        if (existsReportByUserIdAndCommentId(userId, reportedComment.id!!))
            throw BadRequestException(ExceptionType.REPORT_COMMENT_BAD_REQUEST)
        reportRepository.save(Report(userId = userId, reportedCommentId = commentId))
        if (reportedComment.isBlinded) return
        val cnt: Long = reportRepository.countByReportedCommentId(commentId)
        if (cnt >= BLINDED_CONDITION) reportedComment.changeBlindState(BLINDED_STATE)
    }

    fun existsReportByUserIdAndCommentId(userId: Long, commentId: Long): Boolean {
        if (reportRepository.existsByUserIdAndReportedCommentId(userId, commentId)) return true
        return false
    }
}