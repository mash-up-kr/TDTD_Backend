package mashup.backend.tdtd.room.repository

import mashup.backend.tdtd.room.entity.Room
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface RoomRepository : JpaRepository<Room, Long> {
    fun findByRoomCode(roomCode: String): Room?
    fun findByIdIn(ids: List<Long>): List<Room>

    // roomcode 랑 roomid 중 뭘로 업뎃해야하는지를 몰겠음
    // 이거 말고 더 필요한 어노테이션이 있는게 아닐까?
    // 여기가 에러 나는 부분임. 혹시 newTitle 이 null 이면 이게 안돼서? 그런 의존성? 그럼 에러 처리나 디폴트값 설정 같은거 하면 되나?
    @Query("update Room r set r.title =: newTitle where r.roomCode=:roomCode")
    fun updateByRoomCode(newTitle: String, roomCode: String)
}