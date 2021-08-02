package mashup.backend.tdtd.host.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue


data class UpdateRoomNameRequest @JsonCreator constructor(
    @JsonValue val new_title: String
)