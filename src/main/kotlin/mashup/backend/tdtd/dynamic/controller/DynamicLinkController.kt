package mashup.backend.tdtd.dynamic.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/dynamic")
@Controller
class DynamicLinkController {

    @GetMapping
    fun getDynamicLinkPage(): String {
        return "home"
    }
}