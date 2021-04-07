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
        private const val SNS_META_TAG_TITLE = "속닥속닥"
        private const val SNS_META_TAG_DESCRIPTION = "당신의 마음을 속삭여주세요."
        private const val SNS_META_TAG_IMAGE = "https://tdtd-bucket.s3.ap-northeast-2.amazonaws.com/prod/images/app_icon.png"


        fun getLongLink(roomCode: String): String {
            return UriComponentsBuilder.fromUriString("$SCHEMA://$HOST/")
                .queryParam("link", LINK)
                .queryParam("apn", AND_APP_PACKAGE_NAME)
                .queryParam("isi", IOS_APP_STORE_ID)
                .queryParam("ibi", IOS_APP_BUNDLE_ID)
                .queryParam("st", SNS_META_TAG_TITLE)
                .queryParam("sd", SNS_META_TAG_DESCRIPTION)
                .queryParam("si", SNS_META_TAG_IMAGE)
                .queryParam("room-code", roomCode)
                .toUriString()
        }
    }
}