package mashup.backend.tdtd.user.repository

import mashup.backend.tdtd.user.entity.Participation
import org.springframework.data.jpa.repository.JpaRepository

interface ParticipationRepository : JpaRepository<Participation, Long> {
}