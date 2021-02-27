package mashup.backend.tdtd.comment.entity

import mashup.backend.tdtd.config.BaseEntity
import javax.persistence.*

@Table(name = "comments")
@Entity
class Comment : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false)
    var roomId: Long = 0

    @Column(nullable = false)
    var userId: Long = 0

    @Column(nullable = false)
    var nickname: String? = null

    @Column
    var text: String? = null

    @Column
    var voiceFileUrl: String? = null

    @Column(nullable = false)
    var stickerPointX: Double? = null

    @Column(nullable = false)
    var stickerPointY: Double? = null

    @Column(nullable = false)
    var isBlinded: Boolean = false
}