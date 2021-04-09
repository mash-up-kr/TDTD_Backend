package mashup.backend.tdtd.home.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/")
@Controller
class HomeController {

    @GetMapping
    fun home(): String {
        return "home"
    }
}