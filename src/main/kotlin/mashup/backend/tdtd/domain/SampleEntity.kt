package mashup.backend.tdtd.domain

import javax.persistence.*

@Table(name = "samples")
@Entity
class SampleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var name: String = "sample"
}