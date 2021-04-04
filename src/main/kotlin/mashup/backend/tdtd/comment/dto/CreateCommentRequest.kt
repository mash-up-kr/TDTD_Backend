package mashup.backend.tdtd.comment.dto

import mashup.backend.tdtd.comment.entity.MessageType
import mashup.backend.tdtd.comment.entity.StickerColorType
import org.springframework.web.multipart.MultipartFile

data class CreateCommentRequest(
    // todo change snake case to camel case
    val nickname: String,
    val message_type: MessageType,
    val text_message: String?,
    val voice_file: MultipartFile?,
    val sticker_color: StickerColorType,
    val sticker_angle: Int,
)
