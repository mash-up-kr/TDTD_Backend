package mashup.backend.tdtd.user.service

import mashup.backend.tdtd.user.entity.User
import mashup.backend.tdtd.user.repository.UserRepository
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class UserService(private val userRepository : UserRepository) {

    fun getUserByDeviceId(deviceId: String): User = userRepository.findByDeviceId(deviceId)!!

    fun isExistDeviceId(deviceId : String) : Boolean {
        return userRepository.findByDeviceId(deviceId) != null
    }

    fun createUser(deviceId: String) : User {
        return userRepository.save(User(deviceId = deviceId))
    }

    fun validateHostUser(userId: Long, hostId: Long) {
        // TODO: 권한 없음 Exception 추가
        if(userId != hostId) throw RuntimeException()
    }
}