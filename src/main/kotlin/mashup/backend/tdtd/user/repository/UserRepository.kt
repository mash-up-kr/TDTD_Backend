package mashup.backend.tdtd.user.repository

import mashup.backend.tdtd.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByDeviceId(deviceId: String): User?
}