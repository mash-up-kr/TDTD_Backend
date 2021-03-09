package mashup.backend.tdtd.common.util

import mashup.backend.tdtd.comment.entity.StickerColorType
import mashup.backend.tdtd.comment.service.CommentService
import mashup.backend.tdtd.participation.entity.Participation
import mashup.backend.tdtd.participation.service.ParticipationService
import mashup.backend.tdtd.report.service.ReportService
import mashup.backend.tdtd.room.dto.CreateRoomRequest
import mashup.backend.tdtd.room.entity.RoomType
import mashup.backend.tdtd.room.service.RoomService
import mashup.backend.tdtd.user.entity.User
import mashup.backend.tdtd.user.service.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class SeedDataInitializer(
    private val userService: UserService,
    private val roomService: RoomService,
    private val commentService: CommentService,
    private val participationService: ParticipationService,
    private val reportService: ReportService
) : CommandLineRunner{
    companion object {
        val userList: Array<String> = arrayOf(
            "김남수", "이호찬", "김주희",
            "윤소정", "이영민",
            "김연정", "김원경", "김재현", "김종윤",
            "박지영", "정세희", "고승윤"
        )
        val userRange = (1..12)
        val roomRange = (1..100)
        val roomTypeRange = (0..1)
        val participationRange = (1..10)
        val reportRange = (1..100)
        val commentRange = (1..1000)
        val stickerColorTypeRange = (0..6)
        val stickerAngleRange = (-10..10)

        const val PARTICIPATION_RATE = 6
        const val STICKER_AREA_HEIGHT = 146
        const val STICKER_AREA_WIDTH = 343
        const val CHARACTER_TOP_MARGIN = 18
        const val CHARACTER_LEFT_MARGIN = 20
        const val CHARACTER_LIMIT_IN_ROW = 3
        const val CHARACTER_HEIGHT = 110
        const val CHARACTER_WIDTH = 88
        const val REPORT_RATE = 10
    }

    override fun run(vararg args: String?) {
        insertUser()
        createRoom()
        replyComment()
        reportComment()
    }

    fun insertUser() {
        for((index, userName) in userList.withIndex()) {
            userService.createUser("device-${index+1}", userName = userName)
        }
    }

    fun createRoom() {
        for(roomId in roomRange) {
            val randomDeviceId = userRange.random()
            val randomRoomType = if(roomTypeRange.random() == 0) RoomType.VOICE.name else RoomType.TEXT.name
            val insertedRoom = roomService.createRoom(
                "device-${randomDeviceId}",
                CreateRoomRequest(title = "room-${roomId}", type = randomRoomType)
            )
            participationService.saveParticipation("device-${randomDeviceId}", insertedRoom.roomCode)
            joinRandomUser(randomDeviceId, insertedRoom.roomCode)
        }
    }

    fun joinRandomUser(randomDeviceId: Int, roomCode: String) {
        for(participationDevice in userRange) {
            if(randomDeviceId == participationDevice
                || participationRange.random() > PARTICIPATION_RATE) continue
            participationService.saveParticipation("device-${participationDevice}", roomCode)
        }
    }

    fun replyComment() {
        for(commentId in commentRange) {
            val randomParticipationInfo: Participation = participationService.getParticipationRandomLimitOne()
            val userInfo: User = userService.getUserById(randomParticipationInfo.userId)
            val commentCnt = commentService.getCommentCountByRoomId(randomParticipationInfo.roomId)
            val (pointX, pointY) = calcStickerPointFromCommentCnt(commentCnt, false)
            commentService.saveComment(
                userId = userInfo.id!!,
                roomId = randomParticipationInfo.roomId,
                nickname = userInfo.userName!!,
                stickerPointX = pointX,
                stickerPointY = pointY,
                stickerAngle = stickerAngleRange.random(),
                stickerColor = StickerColorType.values()[stickerColorTypeRange.random()]
            )
        }
    }

    fun calcStickerPointFromCommentCnt(commentCnt: Int, isCenter: Boolean): Pair<Double, Double> {
        val col = commentCnt % CHARACTER_LIMIT_IN_ROW
        val row = commentCnt / CHARACTER_LIMIT_IN_ROW
        var left = ((col + 1) * CHARACTER_LEFT_MARGIN) + (col * CHARACTER_WIDTH)
        var top = row * STICKER_AREA_HEIGHT + CHARACTER_TOP_MARGIN
        if(isCenter) {
            left += (CHARACTER_WIDTH / 2)
            top += (CHARACTER_HEIGHT / 2)
        }
        return Pair(left.toDouble(), top.toDouble())
    }

    fun reportComment() {
        for(commentId in commentRange) {
            val commentInfo = commentService.getCommentById(commentId.toLong())
            val participationList = participationService.getParticipationListByRoomId(commentInfo.roomId)
            for(participation in participationList) {
                if(commentInfo.userId == participation.userId
                    || reportRange.random() > REPORT_RATE) continue
                val userInfo = userService.getUserById(participation.userId)
                reportService.reportCommentByCommentId(commentId.toLong(), userInfo.deviceId)
            }
        }
    }
}