package mashup.backend.tdtd.common.util

import java.nio.ByteBuffer
import java.util.*

class UuidManager {
    companion object {
        fun getBase64Uuid(): String {
            val uuid: UUID = UUID.randomUUID()
            val byteBuffer: ByteBuffer = ByteBuffer.wrap(ByteArray(16))
            byteBuffer.putLong(uuid.mostSignificantBits)
            byteBuffer.putLong(uuid.leastSignificantBits)
            val uuidBytes: ByteArray = byteBuffer.array()
            return Base64.getUrlEncoder().withoutPadding().encodeToString(uuidBytes)
        }
    }
}