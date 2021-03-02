package mashup.backend.tdtd.comment.entity

import mashup.backend.tdtd.config.BaseEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@SQLDelete(sql = "update comments set deleted_at=now() where id=?")
@Where(clause = "deleted_at is null")
@Table(name = "comments")
@Entity
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var roomId: Long = 0,

    @Column(nullable = false)
    var userId: Long = 0,

    @Column(nullable = false)
    var nickname: String? = null,

    @Column
    var text: String? = null,

    @Column
    var voiceFileUrl: String? = null,

    @Column(name = "sticker_point_x", nullable = false)
    var stickerPointX: Double? = null,

    @Column(name = "sticker_point_y", nullable = false)
    var stickerPointY: Double? = null,

    @Column(nullable = false)
    var isBlinded: Boolean = false
) : BaseEntity() {

    fun changeBlindState(state: Boolean) {
        this.isBlinded = state
    }
}