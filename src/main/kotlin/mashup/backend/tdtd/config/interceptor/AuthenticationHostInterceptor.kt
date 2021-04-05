package mashup.backend.tdtd.config.interceptor

import mashup.backend.tdtd.comment.entity.Comment
import mashup.backend.tdtd.comment.service.CommentService
import mashup.backend.tdtd.common.entity.ApiType
import mashup.backend.tdtd.common.exception.ExceptionType
import mashup.backend.tdtd.room.service.RoomService
import mashup.backend.tdtd.user.entity.User
import mashup.backend.tdtd.user.service.UserService
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationHostInterceptor(
    private val userService: UserService,
    private val commentService: CommentService,
    private val roomService: RoomService
) : HandlerInterceptor {
    companion object {
        const val DEVICE_ID_KEY_IN_HEADER = "Device-Id"
        const val REMAIN_URL_MIN_LENGTH = 2
        const val REMAIN_URL_MAX_LENGTH = 2
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val deviceId: String = request.getHeader(DEVICE_ID_KEY_IN_HEADER)
        if (isWrongPath(request.servletPath)) {
            request.setAttribute("InterceptorException", ExceptionType.WRONG_PATH_BAD_REQUEST)
            response.sendError(ExceptionType.WRONG_PATH_BAD_REQUEST.code)
            return false
        }
        if (isHost(deviceId, request.servletPath).not()) {
            request.setAttribute("InterceptorException", ExceptionType.NON_HOST_USER_FORBIDDEN)
            response.sendError(ExceptionType.NON_HOST_USER_FORBIDDEN.code)
            return false
        }
        return true
    }

    fun isHost(deviceId: String, path: String): Boolean {
        val user: User = userService.getUserByDeviceId(deviceId)
        val (apiType, detailInfo) = path.replace("/api/v1/host/", "").split("/")
        val room = when (apiType) {
            ApiType.COMMENT.path -> {
                val commentId = detailInfo.toLong()
                val commentInfo: Comment = commentService.getCommentById(commentId)
                roomService.getRoomById(commentInfo.roomId)
            }
            ApiType.ROOM.path -> roomService.getRoomByRoomCode(detailInfo)
            else -> return false
        }
        if (room.hostId != user.id) return false
        return true
    }

    fun isWrongPath(path: String): Boolean {
        val remainingPathList = path.replace("/api/v1/host/", "").split("/")
        val accessPathList = listOf(ApiType.COMMENT.path, ApiType.ROOM.path)
        if (remainingPathList.size > REMAIN_URL_MAX_LENGTH
            || remainingPathList.size < REMAIN_URL_MIN_LENGTH
        ) return true
        if (accessPathList.contains(remainingPathList.first()).not()) return true
        return false
    }
}