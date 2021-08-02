package mashup.backend.tdtd.room.entity

import mashup.backend.tdtd.common.entity.BaseEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@SQLDelete(sql = "update rooms set deleted_at=now() where id=?")
@Where(clause = "deleted_at is null")
@Table(name = "rooms")
@Entity
class Room(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var hostId: Long = 0,

    @Column(nullable = false)
    var title: String = "",

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var type: RoomType = RoomType.TEXT,

    @Column(nullable = false)
    val roomCode: String = "",

    @Column(nullable = false, length = 2048)
    val shareUrl: String = ""
) : BaseEntity() {

    fun updateTitle(title: String) {
        this.title = title
    }
}