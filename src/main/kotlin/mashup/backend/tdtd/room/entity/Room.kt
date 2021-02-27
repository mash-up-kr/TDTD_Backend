package mashup.backend.tdtd.room.entity

import mashup.backend.tdtd.config.BaseEntity
import javax.persistence.*

@Table(name = "rooms")
@Entity
class Room (
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
) : BaseEntity()
