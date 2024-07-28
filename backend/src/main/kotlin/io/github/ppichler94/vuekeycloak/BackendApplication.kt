package io.github.ppichler94.vuekeycloak

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain
import org.springframework.stereotype.Component
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@SpringBootApplication
class BackendApplication

fun main(args: Array<String>) {
    runApplication<BackendApplication>(*args)
}

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
internal class SecurityConfig {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }
            oauth2ResourceServer {
                jwt {  }
            }
        }

        return http.build()
    }
}

@Component
class MyCustomErrorAttributes() : DefaultErrorAttributes() {
    override fun getErrorAttributes(webRequest: WebRequest, options: ErrorAttributeOptions?): Map<String, Any> {
        val errorAttributes: MutableMap<String, Any> = super.getErrorAttributes(webRequest, options)
        errorAttributes.remove("trace")
        return errorAttributes
    }
}

@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:4040")
    }
}