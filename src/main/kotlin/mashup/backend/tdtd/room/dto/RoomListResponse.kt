package mashup.backend.tdtd.room.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import mashup.backend.tdtd.room.entity.RoomType
import java.time.LocalDateTime

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class RoomListResponse(
    @get:JsonProperty("is_host")
    val isHost: Boolean,
    val title: String,
    val roomCode: String,
    val shareUrl: String,
    @get:JsonProperty("is_bookmark")
    val isBookmark: Boolean,
    val type: RoomType,
    val createdAt: LocalDateTime
)