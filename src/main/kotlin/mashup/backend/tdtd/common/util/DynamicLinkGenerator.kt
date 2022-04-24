package mashup.backend.tdtd.common.util

import org.springframework.web.util.UriComponentsBuilder

class DynamicLinkGenerator {
    companion object {
        private const val SCHEMA = "https"
        private const val HOST = "sokdak.page.link"
        private const val APP_URL = "https://sokdak.shop"
        private const val IOS_APP_STORE_ID = "1557251130"
        private const val IOS_APP_BUNDLE_ID = "com.chan.TDTD"
        private const val AND_APP_PACKAGE_NAME = "com.tdtd.voicepaper"
        private const val SNS_META_TAG_TITLE = "속닥속닥"
        private const val SNS_META_TAG_DESCRIPTION = "주위 친구들, 연인, 부모님 등 소중한 사람들과 함께 당신의 속마음을 속삭여주세요."
        private const val SNS_META_TAG_IMAGE = "https://tdtd-bucket-2.s3.ap-northeast-2.amazonaws.com/prod/images/app_icon.png"


        fun getLongLink(roomCode: String): String {
            val link = UriComponentsBuilder.fromUriString(APP_URL).queryParam("room-code", roomCode).toUriString()
            return UriComponentsBuilder.fromUriString("$SCHEMA://$HOST/")
                .queryParam("link", link)
                .queryParam("apn", AND_APP_PACKAGE_NAME)
                .queryParam("isi", IOS_APP_STORE_ID)
                .queryParam("ibi", IOS_APP_BUNDLE_ID)
                .queryParam("ofl", APP_URL)
                .queryParam("st", SNS_META_TAG_TITLE)
                .queryParam("sd", SNS_META_TAG_DESCRIPTION)
                .queryParam("si", SNS_META_TAG_IMAGE)
                .toUriString()
        }
    }
}