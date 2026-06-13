package com.guptaji.springboot_learning.config;

import com.guptaji.springboot_learning.constant.UserRoles;
import com.guptaji.springboot_learning.customFilter.JwtFilter;
import com.guptaji.springboot_learning.service.DbUserDetailsService;
import com.guptaji.springboot_learning.service.impl.DbUserDetailsServiceImpl;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

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

    @Value("${custom.security.user1.name}")
    private String userOne;

    @Value("${custom.security.user2.name}")
    private String userTwo;

    @Value("${custom.security.user1.pass}")
    private String userOnePassword;

    @Value("${custom.security.user2.pass}")
    private String userTwoPassword;

    @Bean("jwtFilter")
    public JwtFilter jwtFilter(UserDetailsService userDetailsService){
        return new JwtFilter(userDetailsService);
    }

    @Bean("userHelper")
    public UserHelper userHelper(){
        return new UserHelper(prefix, suffix);
    }

    @Bean
    public UsersUtility usersUtility(@Qualifier("userHelper") UserHelper userHelper){
        return new UsersUtility(userHelper);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtFilter jwtFilter){

        /**
         * 1. Session creation policy -- Here we are using httpBasic which by-default uses STATELESS session
         * policy internally i.e. with every request we need to pass creds to authenticate ourselves which will
         * call this method 'loadUserByUsername' everytime, but if we do not want to provide creds everytime
         * i.e. with first request we will provide creds then via sessions only browser will send some Jsessions
         * which will be used by the servers for authentication further then we can use following config -
         * .sessionManagement(httpSecuritySessionManagementConfigurer ->
         *          httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
         * with above config there is no need to send creds everytime. In our actual config we have provided
         * STATELESS policy externally to just make sure it should not store any session and ask for creds
         * everytime.
         *
         * 2. HttpBasic -> By default spring uses UserNamePasswordAuthentication filter but httpBasic uses
         * BasicAuthenticationFilter which also needs creds to perform authentication.
         *
         * 3. jwtFilter -> Here after adding jwtFilter first all the requests are checking jwt passed in header
         *  if that verifies then OK else they are going to BasicAuthenticationFilter to check creds because
         *  in the else part we have not provided any exceptions to throw if jwt is missing that we can provide
         *  if we want (in JwtFilter class). if we want then we can comment out httpBasic part then spring will
         *  only rely on jwt only
         *
         *  4. To enable swagger after JWT addition we need to allow two swagger url's w/o authentication
         */

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)              // disable the csrf token
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(SIGN_UP_PATTERN, LOGIN_PATTERN, SWAGGER_UI_URL,
                                        SWAGGER_API_UI_URL, REFRESH_TOKEN_PATTERN).permitAll()  // permit all sign-up, login requests
                        .anyRequest().authenticated())
//                .httpBasic(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .sessionManagement(httpSecuritySessionManagementConfigurer ->
//                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .addFilterBefore(jwtFilter, BasicAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // config for in-memory user
    @Bean
    public UserDetailsService userDetailsService(){

        // config for in-memory user
//        UserDetails userDetailsOne = User
//                .withUsername(userOne)
//                .password(passwordEncoder().encode(userOnePassword))
//                .roles(UserRoles.ROLE_ADMIN.getRole())
//                .build();
//
//        UserDetails userDetailsTwo = User
//                .withUsername(userTwo)
//                .password(passwordEncoder().encode(userTwoPassword))
//                .roles(UserRoles.ROLE_USER.getRole())
//                .build();
//
//        return new InMemoryUserDetailsManager(userDetailsOne, userDetailsTwo);

        // config for db user
        return new DbUserDetailsServiceImpl();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
                                                         PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration){
        return authenticationConfiguration.getAuthenticationManager();
    }

    // created to pass CSRF token in swagger-ui, by this config authorize button will be enabled
    // in swagger in which we can pass csrf token
    // commenting out below config as we are disabling csrf token for now
//    @Bean
//    public OpenAPI customOpenAPIForCsrf() {
//        return new OpenAPI()
//                .components(
//                        new Components()
//                        .addSecuritySchemes("csrf",
//                                new SecurityScheme().type(SecurityScheme.Type.APIKEY)
//                                        .in(SecurityScheme.In.HEADER)
//                                        .name(CSRF_TOKEN_HEADER)
//                        )
//                )
//                .addSecurityItem(new SecurityRequirement().addList("csrf"));
//    }

    // swagger config to pass jwt and enable authorize button
    @Bean
    public OpenAPI customOpenAPIForJwt() {

        return new OpenAPI()
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "bearerAuth",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .addSecurityItem(
                        new SecurityRequirement().addList("bearerAuth")
                );
    }

}
