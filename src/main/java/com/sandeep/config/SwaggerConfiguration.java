package com.sandeep.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Excel import Rest API",
                description = "Sandeep Management API",
                version = "0.0.1-SNAPSHOT",
                contact = @Contact(
                        name = "Sandeep Prajapati",
                        url = "https://github.com/sandeep8750?tab=repositories",
                        email = "Sandeeprajapati8750@gmail.com"
                ),
                license = @io.swagger.v3.oas.annotations.info.License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"
                )
        )
)
public class SwaggerConfiguration {
}
