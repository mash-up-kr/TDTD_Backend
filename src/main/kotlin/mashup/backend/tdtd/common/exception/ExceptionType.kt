package mashup.backend.tdtd.common.exception

enum class ExceptionType(
    var code: Int,
    var message: String
) {
    BAD_REQUEST(4000, "Bad Request"),
    DEVICE_BAD_REQUEST(4001, "Device-Id is not included."),
    WRONG_PATH_BAD_REQUEST(4002, "The request was sent with the wrong path."),
    DELETE_COMMENT_BAD_REQUEST(4003, "Can't delete other people's comment."),
    REPORT_COMMENT_BAD_REQUEST(4004, "This comment has already been reported."),

    UNAUTHORIZED(4010, "Unauthorized"),
    USER_UNAUTHORIZED(4011, ""),

    FORBIDDEN(4030, "Forbidden"),
    NON_HOST_USER_FORBIDDEN(4031, "Only hosts can be requested."),
    REPORT_COMMENT_FORBIDDEN(4032, "Cannot report own comment."),

    NOT_FOUND(4040, "Not Found"),
    USER_NOT_FOUND(4041, "The user does not exist."),
    USER_DEVICE_NOT_FOUND(4042, "The device id does not exist."),
    ROOM_NOT_FOUND(4043, "The room does not exist."),
    ROOM_CODE_NOT_FOUND(4044, "The room code does not exist."),
    COMMENT_NOT_FOUND(4045, "The comment does not exist."),
    PARTICIPATION_NOT_FOUND(4046, "This user not participating in this room."),

    UNEXPECTED(5000, "This request cannot be processed.")
}