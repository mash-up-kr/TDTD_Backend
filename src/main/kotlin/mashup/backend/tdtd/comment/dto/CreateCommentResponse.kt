package mashup.backend.tdtd.comment.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import mashup.backend.tdtd.comment.entity.MessageType
import mashup.backend.tdtd.comment.entity.StickerColorType

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class CreateCommentResponse(
    val nickname: String,
    val messageType: MessageType,
    val textMessage: String?,
    val voiceUrl: String?,
    val stickerColor: StickerColorType,
    val stickerAngle: Int,
)
