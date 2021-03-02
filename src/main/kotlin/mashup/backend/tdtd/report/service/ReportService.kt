package mashup.backend.tdtd.report.service

import mashup.backend.tdtd.comment.entity.Comment
import mashup.backend.tdtd.comment.service.CommentService
import mashup.backend.tdtd.config.Log
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
        if(userId == reportedComment.userId) throw NoSuchElementException()
        reportRepository.save(Report(userId = userId, reportedCommentId = commentId))
        val cnt: Long = reportRepository.countByReportedCommentId(commentId)
        if(cnt >= BLINDED_CONDITION) reportedComment.changeBlindState(BLINDED_STATE)
    }
}