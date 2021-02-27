package mashup.backend.tdtd.user.service

import mashup.backend.tdtd.user.entity.User
import mashup.backend.tdtd.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository) {

    fun getUserByDeviceId(deviceId: String): User {
        return userRepository.findByDeviceId(deviceId)!!
    }
}