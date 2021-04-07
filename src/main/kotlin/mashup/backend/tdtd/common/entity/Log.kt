package mashup.backend.tdtd.common.entity

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Log {
    companion object {
        private val logger : Logger get() = LoggerFactory.getLogger(this::class.java)

        fun printErrorLog(code: Int, message: String) {
            logger.error("code: $code - message: $message")
        }

        fun printErrorLog(message: String) {
            logger.error(message)
        }
    }
}