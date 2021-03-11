package mashup.backend.tdtd.common.util

import org.springframework.web.util.UriComponentsBuilder

class DeepLinkGenerator {
    companion object {
        private const val SCHEMA = "https"
        private const val HOST = "sokdak.page.link"

        fun getDeepLink(roomCode: String): String {
            return UriComponentsBuilder.fromUriString("${SCHEMA}://${HOST}/")
                .queryParam("room-code", roomCode)
                .toUriString()
        }
    }
}