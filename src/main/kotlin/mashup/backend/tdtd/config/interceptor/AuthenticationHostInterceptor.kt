package mashup.backend.tdtd.config.interceptor

import mashup.backend.tdtd.comment.entity.Comment
import mashup.backend.tdtd.comment.service.CommentService
import mashup.backend.tdtd.common.entity.ApiType
import mashup.backend.tdtd.room.service.RoomService
import mashup.backend.tdtd.user.entity.User
import mashup.backend.tdtd.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationHostInterceptor(
    private val userService: UserService,
    private val commentService: CommentService,
    private val roomService: RoomService
): HandlerInterceptor{
    companion object {
        const val DEVICE_ID_KEY_IN_HEADER = "Device-Id"
        const val HOST_ONLY_API_PATH_PREFIX = "/api/v1/host/"
        const val REMAIN_URL_MIN_LENGTH = 2
        const val REMAIN_URL_MAX_LENGTH = 2
        const val REQUEST_WRONG_PATH_ERROR_MSG = "The Request was sent with the wrong path."
        const val REQUEST_NOT_HOST_ERROR_MSG = "This API can only be requested by the host."
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val deviceId: String = request.getHeader(DEVICE_ID_KEY_IN_HEADER)
        if(isWrongPath(request.servletPath)) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), REQUEST_WRONG_PATH_ERROR_MSG)
            return false
        }
        if(isHost(deviceId, request.servletPath).not()) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), REQUEST_NOT_HOST_ERROR_MSG)
            return false
        }
        return true
    }

    fun isHost(deviceId: String, path: String): Boolean {
        val user: User = userService.getUserByDeviceId(deviceId)
        val (apiType, detailInfo) = splitRemovedPrefixPath(path)
        val room = when (apiType) {
            ApiType.COMMENT.path -> {
                val commentId = detailInfo.toLong()
                val commentInfo: Comment = commentService.getCommentById(commentId)
                roomService.getRoomById(commentInfo.roomId)
            }
            ApiType.ROOM.path -> {
                roomService.getRoomByRoomCode(detailInfo)
            }
            else -> return false
        }
        if(room.hostId != user.id) return false
        return true
    }

    fun isWrongPath(path: String): Boolean {
        if(path.contains(HOST_ONLY_API_PATH_PREFIX).not()) return true
        val remainingPathList = splitRemovedPrefixPath(path)
        val accessPathList = listOf("comments", "rooms")
        if(remainingPathList.size > REMAIN_URL_MAX_LENGTH
            || remainingPathList.size < REMAIN_URL_MIN_LENGTH) return true
        if(accessPathList.contains(remainingPathList.first()).not()) return true
        return false
    }

    fun splitRemovedPrefixPath(path: String): List<String> =
        path.replace(HOST_ONLY_API_PATH_PREFIX, "").split("/")
}