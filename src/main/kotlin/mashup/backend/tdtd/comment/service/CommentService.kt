package mashup.backend.tdtd.comment.service

import mashup.backend.tdtd.comment.dto.CommentResponse
import mashup.backend.tdtd.comment.entity.Comment
import mashup.backend.tdtd.comment.entity.StickerColorType
import mashup.backend.tdtd.comment.repository.CommentRepository
import mashup.backend.tdtd.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val userService: UserService,
) {

    fun getCommentListByRoomId(userId: Long, roomId: Long): List<CommentResponse> {
        return commentRepository.findAllByRoomIdAndIsBlindedIsFalse(roomId).map {
            CommentResponse(
                id = it.id!!,
                isMine = userId == it.userId,
                nickname = it.nickname,
                text = it.text,
                voiceFileUrl = it.voiceFileUrl,
                stickerColor = it.stickerColor,
                stickerAngle = it.stickerAngle,
                createdAt = it.createdAt
            )
        }
    }

    fun getCommentById(commentId: Long): Comment =
        commentRepository.findById(commentId).orElseThrow { NoSuchElementException() }

    fun saveComment(roomId: Long, userId: Long, nickname: String,
                    stickerPointX: Double, stickerPointY: Double,
                    stickerAngle: Int, stickerColor: StickerColorType,
    ): Comment = commentRepository.save(
            Comment(
                roomId = roomId,
                userId = userId,
                nickname = nickname,
                stickerPointX = stickerPointX,
                stickerPointY = stickerPointY,
                stickerAngle = stickerAngle,
                stickerColor = stickerColor
            )
        )

    fun getCommentCountByRoomId(roomId: Long): Int =
        commentRepository.countByRoomId(roomId = roomId)

    @Transactional
    fun deleteCommentByCommentId(deviceId: String, commentId: Long) {
        val userId: Long = userService.getUserByDeviceId(deviceId).id!!
        val comment: Comment = getCommentById(commentId)
        if(userId != comment.userId)
            throw IllegalArgumentException("Can't delete other people's comments.")
        commentRepository.deleteById(commentId)
    }

    @Transactional
    fun deleteCommentByHost(commentId: Long) =
        commentRepository.deleteById(commentId)
}
