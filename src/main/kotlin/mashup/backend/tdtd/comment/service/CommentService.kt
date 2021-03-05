package mashup.backend.tdtd.comment.service

import mashup.backend.tdtd.comment.dto.CommentResponse
import mashup.backend.tdtd.comment.entity.Comment
import mashup.backend.tdtd.comment.repository.CommentRepository
import mashup.backend.tdtd.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val userService: UserService) {

    fun getCommentListByRoomId(deviceId: String, roomId: Long): List<CommentResponse> {
        val userId: Long = userService.getUserByDeviceId(deviceId).id!!;
        return commentRepository.findAllByRoomId(roomId).map {
            CommentResponse(
                id = it.id!!,
                isMine = userId == it.userId,
                nickname = it.nickname!!,
                text = it.text,
                voiceFileUrl = it.voiceFileUrl,
                stickerPointX = it.stickerPointX!!,
                stickerPointY = it.stickerPointY!!,
                createdAt = it.createdAt
            )
        }
    }


    fun getCommentById(commentId: Long): Comment =
        commentRepository.findById(commentId).orElseThrow { NoSuchElementException() }

    fun saveComment(roomId: Long, userId: Long, nickname: String,
                    stickerPointX: Double, stickerPointY: Double
    ): Comment = commentRepository.save(
            Comment(
                roomId = roomId,
                userId = userId,
                nickname = nickname,
                stickerPointX = stickerPointX,
                stickerPointY = stickerPointY
            )
        )

    fun getCommentCountByRoomId(roomId: Long): Int =
        commentRepository.countByRoomId(roomId = roomId)

    @Transactional
    fun deleteCommentByCommentId(commentId: Long) =
        commentRepository.deleteById(commentId)
}

