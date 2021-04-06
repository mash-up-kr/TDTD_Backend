package mashup.backend.tdtd.common.service

import mashup.backend.tdtd.common.dto.ShortDynamicLinkRequest
import mashup.backend.tdtd.common.dto.ShortDynamicLinkResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class DynamicLinkService(
    private val restTemplate: RestTemplate,
    private val environment: Environment,
    @Value("\${firebase.api-key}")
    private val apiKey: String
) {
    companion object {
        const val FIREBASE_SHORT_DYNAMIC_LINK_URL = "https://firebasedynamiclinks.googleapis.com/v1/shortLinks"
    }

    fun getShortDynamicLink(dynamicLink: String): String? {
        if(environment.activeProfiles.contains("local")) return null
        val url: String = UriComponentsBuilder
            .fromUriString(FIREBASE_SHORT_DYNAMIC_LINK_URL)
            .queryParam("key", apiKey)
            .toUriString()
        val body = ShortDynamicLinkRequest(dynamicLink)
        val httpEntity = HttpEntity(body, HttpHeaders.EMPTY)

        val response: ShortDynamicLinkResponse =
            restTemplate.exchange(url,
                HttpMethod.POST,
                httpEntity,
                ShortDynamicLinkResponse::class.java).body?: throw RuntimeException()

        return response.shortLink
    }
}