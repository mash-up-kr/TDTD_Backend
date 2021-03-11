package mashup.backend.tdtd.user.service

import mashup.backend.tdtd.user.entity.User
import mashup.backend.tdtd.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun getUserByDeviceId(deviceId: String): User =
        userRepository.findByDeviceId(deviceId) ?: throw NoSuchElementException("The device id does not exist.")

    fun getUserById(userId: Long): User =
        userRepository.findByIdOrNull(userId) ?: throw NoSuchElementException("This id does not exist.")

    fun isExistDeviceId(deviceId: String): Boolean =
        userRepository.findByDeviceId(deviceId) != null

    fun createUser(deviceId: String): User =
        userRepository.save(User(deviceId = deviceId))

    fun createUser(deviceId: String, userName: String): User =
        userRepository.save(User(deviceId = deviceId, userName = userName))
}