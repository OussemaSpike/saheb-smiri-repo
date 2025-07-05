//package com.wassefchargui.employee_service.config;
//
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Contact;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.info.License;
//import io.swagger.v3.oas.models.servers.Server;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//@Configuration
//public class OpenApiConfig {
//
//    @Bean
//    public OpenAPI employeeServiceOpenAPI() {
//        Server devServer = new Server();
//        devServer.setUrl("http://localhost:8081");
//        devServer.setDescription("Server URL in Development environment");
//
//        Server prodServer = new Server();
//        prodServer.setUrl("https://api.employee-service.com");
//        prodServer.setDescription("Server URL in Production environment");
//
//        Contact contact = new Contact();
//        contact.setEmail("wassefchargui@example.com");
//        contact.setName("Wassef Chargui");
//        contact.setUrl("https://www.wassefchargui.com");
//
//        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");
//
//        Info info = new Info()
//                .title("Employee Service API")
//                .version("1.0")
//                .contact(contact)
//                .description("This API exposes endpoints to manage employees in the company. It integrates with the Department Service to validate department references and enrich employee data.")
//                .termsOfService("https://www.wassefchargui.com/terms")
//                .license(mitLicense);
//
//        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
//    }
//}
