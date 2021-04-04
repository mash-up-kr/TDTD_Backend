package mashup.backend.tdtd.comment.entity

import com.amazonaws.util.StringUtils

enum class MessageType {
    TEXT, VOICE;

    companion object {
        fun getMessageType(comment: Comment): MessageType {
            return if (StringUtils.isNullOrEmpty(comment.text)) VOICE else TEXT
        }
    }
}