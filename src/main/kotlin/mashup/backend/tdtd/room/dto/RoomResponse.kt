package mashup.backend.tdtd.room.dto

import java.time.LocalDateTime

data class RoomResponse(
    val isHost: Boolean,
    val title: String?,
    val roomCode: String,
    val isBookmark: Boolean,
    val createdAt: LocalDateTime
)