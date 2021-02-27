package mashup.backend.tdtd.config

import mashup.backend.tdtd.config.interceptor.DeviceIdInterceptor
import mashup.backend.tdtd.config.interceptor.UserRegistrationInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig (
    private val deviceIdInterceptor: DeviceIdInterceptor,
    private val userRegistrationInterceptor: UserRegistrationInterceptor
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(deviceIdInterceptor).order(0)
        registry.addInterceptor(userRegistrationInterceptor).addPathPatterns("/auth/user").order(1)
        super.addInterceptors(registry)
    }
}