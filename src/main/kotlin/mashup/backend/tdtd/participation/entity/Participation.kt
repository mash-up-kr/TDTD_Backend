package mashup.backend.tdtd.participation.entity

import mashup.backend.tdtd.common.entity.BaseEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@SQLDelete(sql = "update participation set deleted_at=now() where id=?")
@Where(clause = "deleted_at is null")
@Table(name = "participation")
@Entity
class Participation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var roomId: Long = 0,

    @Column(nullable = false)
    var userId: Long = 0,

    @Column(nullable = false)
    var bookmark: Boolean = false
) : BaseEntity() {

    fun updateBookmark(bookmark: Boolean) {
        this.bookmark = bookmark
    }
}