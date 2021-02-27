package mashup.backend.tdtd.room.service

import mashup.backend.tdtd.common.util.UuidManager
import mashup.backend.tdtd.room.dto.CreateRoomRequest
import mashup.backend.tdtd.room.dto.CreateRoomResponse
import mashup.backend.tdtd.room.entity.Room
import mashup.backend.tdtd.room.entity.RoomType
import mashup.backend.tdtd.room.repository.RoomRepository
import mashup.backend.tdtd.user.service.UserService
import org.springframework.stereotype.Service

@Service
class RoomService(
    private val roomRepository: RoomRepository,
    private val userService: UserService
) {
    fun createRoom(
        deviceId: String,
        createRoomRequest: CreateRoomRequest,
    ): CreateRoomResponse {

        val savedRoom: Room = roomRepository.save(
            Room(
                hostId = userService.getUserByDeviceId(deviceId).id!!,
                title = createRoomRequest.title,
                type = RoomType.valueOf(createRoomRequest.type),
                roomCode = UuidManager.getBase64Uuid(),
            )
        )

        return CreateRoomResponse(
            roomCode = savedRoom.roomCode
        )
    }

    fun getRoomByRoomCode(roomCode: String): Room {
        return roomRepository.findByRoomCode(roomCode)!!
    }
}