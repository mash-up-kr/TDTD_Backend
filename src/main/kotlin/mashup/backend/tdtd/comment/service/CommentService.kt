package mashup.backend.tdtd.comment.service

import mashup.backend.tdtd.comment.dto.CommentResponse
import mashup.backend.tdtd.comment.dto.CreateCommentRequest
import mashup.backend.tdtd.comment.dto.CreateCommentResponse
import mashup.backend.tdtd.comment.entity.Comment
import mashup.backend.tdtd.comment.entity.MessageType
import mashup.backend.tdtd.comment.entity.StickerColorType
import mashup.backend.tdtd.comment.repository.CommentRepository
import mashup.backend.tdtd.common.exception.BadRequestException
import mashup.backend.tdtd.common.exception.ExceptionType
import mashup.backend.tdtd.common.exception.NotFoundException
import mashup.backend.tdtd.common.service.AwsS3Service
import mashup.backend.tdtd.room.repository.RoomRepository
import mashup.backend.tdtd.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val roomRepository: RoomRepository,
    private val userService: UserService,
    private val awsS3Service: AwsS3Service,
) {

    fun saveComment(
        roomId: Long, userId: Long, nickname: String,
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
        commentRepository.findById(commentId)
            .orElseThrow { NotFoundException(ExceptionType.COMMENT_NOT_FOUND) }

    fun getCommentCountByRoomId(roomId: Long): Int =
        commentRepository.countByRoomId(roomId = roomId)

    @Transactional
    fun deleteCommentByCommentId(deviceId: String, commentId: Long) {
        val userId: Long = userService.getUserByDeviceId(deviceId).id!!
        val comment: Comment = getCommentById(commentId)
        if (userId != comment.userId) throw BadRequestException(ExceptionType.DELETE_COMMENT_BAD_REQUEST)

        commentRepository.deleteById(commentId)
    }

    @Transactional
    fun deleteCommentByHost(commentId: Long) {
        try {
            commentRepository.deleteById(commentId)
        } catch (e: IllegalArgumentException) {
            throw NotFoundException(ExceptionType.COMMENT_NOT_FOUND)
        }
    }

    fun createTextComment(
        requestParams: CreateCommentRequest,
        deviceId: String,
        roomCode: String
    ): CreateCommentResponse {
        val userId: Long = userService.getUserByDeviceId(deviceId).id!!
        val roomId: Long = roomRepository.findByRoomCode(roomCode)?.id!!
        val comment = Comment(
            roomId = roomId,
            userId = userId,
            nickname = requestParams.nickname,
            text = requestParams.text_message,
            stickerAngle = requestParams.sticker_angle,
            stickerColor = requestParams.sticker_color,
        )

        commentRepository.save(comment)

        return CreateCommentResponse(
            nickname = comment.nickname,
            messageType = MessageType.getMessageType(comment),
            textMessage = comment.text,
            voiceUrl = null,
            stickerColor = comment.stickerColor,
            stickerAngle = comment.stickerAngle,
        )
    }

    fun createVoiceComment(
        requestParams: CreateCommentRequest,
        deviceId: String,
        roomCode: String
    ): CreateCommentResponse {
        val userId: Long = userService.getUserByDeviceId(deviceId).id!!
        val roomId: Long = roomRepository.findByRoomCode(roomCode)?.id!!

        val voiceFileUrl: String = awsS3Service.uploadVoiceFile(requestParams.voice_file!!)

        val comment = Comment(
            roomId = roomId,
            userId = userId,
            nickname = requestParams.nickname,
            voiceFileUrl = voiceFileUrl,
            stickerAngle = requestParams.sticker_angle,
            stickerColor = requestParams.sticker_color,
        )

        commentRepository.save(comment)

        return CreateCommentResponse(
            nickname = comment.nickname,
            messageType = MessageType.getMessageType(comment),
            textMessage = null,
            voiceUrl = comment.voiceFileUrl,
            stickerColor = comment.stickerColor,
            stickerAngle = comment.stickerAngle,
        )
    }
}
