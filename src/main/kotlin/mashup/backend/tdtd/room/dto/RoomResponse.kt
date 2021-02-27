package mashup.backend.tdtd.room.dto

import java.time.LocalDateTime

data class RoomResponse(
    val isHost: Boolean,
    val title: String?,
    val room_code: String,
    val isBookmark: Boolean,
    val created_at: LocalDateTime
)