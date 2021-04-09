package mashup.backend.tdtd.config

import mashup.backend.tdtd.config.interceptor.AuthenticationHostInterceptor
import mashup.backend.tdtd.config.interceptor.DeviceIdInterceptor
import mashup.backend.tdtd.config.interceptor.RedirectionHomeInterceptor
import mashup.backend.tdtd.config.interceptor.UserRegistrationInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val deviceIdInterceptor: DeviceIdInterceptor,
    private val userRegistrationInterceptor: UserRegistrationInterceptor,
    private val authenticationHostInterceptor: AuthenticationHostInterceptor,
    private val redirectionHomeInterceptor: RedirectionHomeInterceptor
) : WebMvcConfigurer {
    companion object {
        const val ALL_PATH = "/**"
        const val ERROR_PATH = "/error"
        const val IMAGES_PATH = "/images/**"
        const val CSS_PATH = "/css/**"
        const val FONTS_PATH = "/fonts/**"
        const val ALL_API_PATH = "/api/**"
        const val USER_REGISTRATION_URL_PATH = "/api/v1/rooms/**"
        const val HOST_ONLY_URL_PATH = "/api/v1/host/**"
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(redirectionHomeInterceptor)
            .addPathPatterns(ALL_PATH)
            .excludePathPatterns(ALL_API_PATH, ERROR_PATH, IMAGES_PATH, CSS_PATH, FONTS_PATH)
            .order(0)
        registry.addInterceptor(deviceIdInterceptor)
            .addPathPatterns(ALL_API_PATH)
            .order(0)
        registry.addInterceptor(userRegistrationInterceptor)
            .addPathPatterns(USER_REGISTRATION_URL_PATH).order(1)
        registry.addInterceptor(authenticationHostInterceptor)
            .addPathPatterns(HOST_ONLY_URL_PATH).order(1)
        super.addInterceptors(registry)
    }
}