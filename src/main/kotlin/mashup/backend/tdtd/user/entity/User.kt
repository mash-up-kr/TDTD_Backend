package mashup.backend.tdtd.user.entity

import mashup.backend.tdtd.common.entity.BaseEntity
import java.time.LocalDateTime
import javax.persistence.*

@Table(name = "users")
@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    var deviceId: String = "",

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    var pushAlarm: Boolean = true,

    @Column
    var email: String? = null,

    @Column
    var userName: String? = null,

    @Column
    var profile: String? = null,

    @Column(columnDefinition = "DATETIME")
    var lastUsedAt: LocalDateTime = LocalDateTime.now()
) : BaseEntity() {

    fun updateLastUsedAt() {
        this.lastUsedAt = LocalDateTime.now()
    }
}