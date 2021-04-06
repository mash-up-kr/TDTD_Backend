package mashup.backend.tdtd.common.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class ShortDynamicLinkResponse(
    val shortLink: String
) {
    constructor() : this("")
}