package mashup.backend.tdtd

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TdtdApplication

fun main(args: Array<String>) {
    runApplication<TdtdApplication>(*args)
    print("Hello TDTD!")
}
