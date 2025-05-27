package com.ssafy.cafe;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = "com.ssafy.cafe.model.dao")
public class MobileThroughProjectApplication {


	public static void main(String[] args) {
		SpringApplication.run(MobileThroughProjectApplication.class, args);
	}

    @Bean
    public OpenAPI postsApi() {
        Info info = new Info()
                .title("SSAFY Cafe Rest API")
                .description("<h3>SSAFY Cafe에서 제공되는 Rest api의 문서 제공</h3><br>"
                        + "<img src=\"/imgs/ssafy_logo.png\" width=\"200\">")
                .contact(new Contact().name("ssafy").email("ssafy@ssafy.com"))
                .license(new License().name("SSAFY License").url("https://www.ssafy.com/ksp/jsp/swp/etc/swpPrivacy.jsp"))
                .version("1.0");

        // ✅ OpenAPI 객체에 components()와 security 추가
        return new OpenAPI()
                .info(info)
                .components(new Components().addSecuritySchemes("bearer-key",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                ))
                .addSecurityItem(new SecurityRequirement().addList("bearer-key"));
    }


}
