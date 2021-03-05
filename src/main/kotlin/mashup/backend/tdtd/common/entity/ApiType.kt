package mashup.backend.tdtd.common.entity

enum class ApiType(val path: String) {
    USER("users"),
    ROOM("rooms"),
    COMMENT("comments"),
    REPORT("reports"),
    BOOKMARK("bookmarks")
}