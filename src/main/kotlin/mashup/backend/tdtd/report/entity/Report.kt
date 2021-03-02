package mashup.backend.tdtd.report.entity

import mashup.backend.tdtd.config.BaseEntity
import javax.persistence.*

@Table(name = "reports")
@Entity
class Report(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var userId: Long = 0,

    @Column(nullable = false)
    var reportedCommentId: Long = 0
) : BaseEntity()