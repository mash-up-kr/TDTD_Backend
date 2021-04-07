package mashup.backend.tdtd.common.util

import org.springframework.web.util.UriComponentsBuilder

class DynamicLinkGenerator {
    companion object {
        private const val SCHEMA = "https"
        private const val HOST = "sokdak.page.link"
        private const val LINK = "http://52.79.54.20/dynamic"
        private const val IOS_APP_STORE_ID = "1557251130"
        private const val IOS_APP_BUNDLE_ID = "com.chan.TDTD"
        private const val AND_APP_PACKAGE_NAME = "com.tdtd.voicepaper"

        fun getLongLink(roomCode: String): String {
            return UriComponentsBuilder.fromUriString("$SCHEMA://$HOST/")
                .queryParam("link", LINK)
                .queryParam("apn", AND_APP_PACKAGE_NAME)
                .queryParam("isi", IOS_APP_STORE_ID)
                .queryParam("ibi", IOS_APP_BUNDLE_ID)
                .queryParam("room-code", roomCode)
                .toUriString()
        }
    }
}