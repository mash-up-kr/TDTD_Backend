package mashup.backend.tdtd.user.entity

import mashup.backend.tdtd.config.BaseEntity
import javax.persistence.*

@Table(name = "participation")
@Entity
class Participation : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false)
    var roomId: Long = 0

    @Column(nullable = false)
    var userId: Long = 0

    @Column(nullable = false)
    var bookmark: Boolean = false
}