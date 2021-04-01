package mashup.backend.tdtd.user.entity

import mashup.backend.tdtd.common.entity.BaseEntity
import javax.persistence.*

@Table(name = "users")
@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var deviceId: String = "",

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    var pushAlarm: Boolean = true,

    @Column
    var email: String? = null,

    @Column
    var userName: String? = null,

    @Column
    var profile: String? = null
) : BaseEntity()