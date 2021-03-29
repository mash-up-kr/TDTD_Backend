package mashup.backend.tdtd.config

import mashup.backend.tdtd.config.interceptor.AuthenticationHostInterceptor
import mashup.backend.tdtd.config.interceptor.DeviceIdInterceptor
import mashup.backend.tdtd.config.interceptor.UserRegistrationInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val deviceIdInterceptor: DeviceIdInterceptor,
    private val userRegistrationInterceptor: UserRegistrationInterceptor,
    private val authenticationHostInterceptor: AuthenticationHostInterceptor
) : WebMvcConfigurer {
    companion object {
        const val USER_REGISTRATION_URL_PATH = "/api/v1/rooms/**"
        const val HOST_ONLY_URL_PATH = "/api/v1/host/**"
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(deviceIdInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/error", "/api/error")
            .order(0)
        registry.addInterceptor(userRegistrationInterceptor)
            .addPathPatterns(USER_REGISTRATION_URL_PATH).order(1)
        registry.addInterceptor(authenticationHostInterceptor)
            .addPathPatterns(HOST_ONLY_URL_PATH).order(1)
        super.addInterceptors(registry)
    }
}