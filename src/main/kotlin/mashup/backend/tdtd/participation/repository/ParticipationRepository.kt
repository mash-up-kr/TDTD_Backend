package mashup.backend.tdtd.participation.repository

import mashup.backend.tdtd.participation.entity.Participation
import org.springframework.data.jpa.repository.JpaRepository

interface ParticipationRepository : JpaRepository<Participation, Long> {
}