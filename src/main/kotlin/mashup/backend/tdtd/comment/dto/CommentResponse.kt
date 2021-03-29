package mashup.backend.tdtd.comment.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import mashup.backend.tdtd.comment.entity.StickerColorType
import java.time.LocalDateTime

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class CommentResponse(
    val id: Long,
    @get:JsonProperty("is_mine")
    val isMine: Boolean,
    val nickname: String,
    val text: String?,
    val voiceFileUrl: String?,
    val stickerColor: StickerColorType,
    val stickerAngle: Int,
    val createdAt: LocalDateTime
)