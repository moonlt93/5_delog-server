import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenAPIConfig {
    @Bean
    fun openAPI(): OpenAPI =
        OpenAPI()
            .components(Components())
            .info(apiInfo())

    private fun apiInfo(): Info =
        Info()
            .title("API Test")
            .description("Let's practice Swagger UI")
            .version("1.0.0")
}
