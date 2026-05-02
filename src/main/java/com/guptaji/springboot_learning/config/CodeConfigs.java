package com.guptaji.springboot_learning.config;

import com.guptaji.springboot_learning.service.impl.UserHelper;
import com.guptaji.springboot_learning.service.impl.UsersUtility;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.resilience.annotation.EnableResilientMethods;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static com.guptaji.springboot_learning.constant.Constants.*;

@Configuration
@EnableCaching
@EnableResilientMethods
@EnableWebSecurity
public class CodeConfigs {

    @Value("${name.prefix}")
    private String prefix;

    @Value("${name.suffix}")
    private String suffix;

    @Bean("userHelper")
    public UserHelper userHelper(){
        return new UserHelper(prefix, suffix);
    }

    @Bean
    public UsersUtility usersUtility(@Qualifier("userHelper") UserHelper userHelper){
        return new UsersUtility(userHelper);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        return httpSecurity
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    // created to pass CSRF token in swagger-ui, by this config authorize button will be enable
    // in swagger in which we can pass csrf token
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(
                        new Components()
                        .addSecuritySchemes("csrf",
                                new SecurityScheme().type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .name(CSRF_TOKEN_HEADER)
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("csrf"));
    }
}
