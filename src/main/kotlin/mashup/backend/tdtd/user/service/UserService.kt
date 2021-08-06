package mashup.backend.tdtd.user.service

import mashup.backend.tdtd.common.exception.ExceptionType
import mashup.backend.tdtd.common.exception.NotFoundException
import mashup.backend.tdtd.user.entity.User
import mashup.backend.tdtd.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository) {

    fun getUserByDeviceId(deviceId: String): User =
        userRepository.findByDeviceId(deviceId) ?: throw NotFoundException(ExceptionType.USER_DEVICE_NOT_FOUND)

    fun getUserById(userId: Long): User =
        userRepository.findById(userId).orElseThrow { NotFoundException(ExceptionType.USER_NOT_FOUND) }

    fun isExistDeviceId(deviceId: String): Boolean =
        userRepository.findByDeviceId(deviceId) != null

    fun createUser(deviceId: String): User =
        userRepository.save(User(deviceId = deviceId))

    fun createUser(deviceId: String, userName: String): User =
        userRepository.save(User(deviceId = deviceId, userName = userName))

    @Transactional
    fun updateUserLastUsedAt(deviceId: String) {
        val user: User = getUserByDeviceId(deviceId)
        user.updateLastUsedAt()
    }
}