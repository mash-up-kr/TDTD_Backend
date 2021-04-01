package mashup.backend.tdtd.room.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import mashup.backend.tdtd.comment.dto.CommentResponse
import mashup.backend.tdtd.room.entity.RoomType

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class RoomDetailResponse(
    val title: String,
    val type: Enum<RoomType>,
    @get:JsonProperty("is_host")
    val isHost: Boolean,
    val shareUrl: String,
    var comments: List<CommentResponse>
)