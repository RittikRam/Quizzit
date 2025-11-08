package com.rittik.MyQuizzApp.security;

import com.rittik.MyQuizzApp.security.jwt.AuthEntryPointJwt;
import com.rittik.MyQuizzApp.security.jwt.AuthTokenFilter;
import com.rittik.MyQuizzApp.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private static final String[] PUBLIC_WHITELIST = {
            "/api/auth/**",      // Allows /api/auth/signup and /api/auth/login
            "/api/quizzes",      // Allows public quiz listing
            "/api/quizzes/{id}"  // Allows public quiz viewing
    };

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter(
            JwtProvider jwtProvider,
            UserDetailsServiceImpl userDetailsService
    ){
        return new AuthTokenFilter(jwtProvider, userDetailsService);
    }
    @Autowired
    private AuthEntryPointJwt authEntryPointJwt;
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(PUBLIC_WHITELIST);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,AuthTokenFilter authenticationJwtTokenFilter) throws Exception {

        http.cors(Customizer.withDefaults()) // 1. Enable CORS configuration defined earlier
                .csrf(csrf -> csrf.disable()) // 2. Disable CSRF for stateless REST API

                // 3. Set session management to stateless (Crucial for JWT)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 4. Handle Unauthorized Access/Errors
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(authEntryPointJwt)) // We must define this bean (for 401 errors)

                // 5. Define authorization rules for endpoints
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "/api/quizzes", "/api/quizzes/{id}").permitAll() // Public Endpoints
                        .requestMatchers("/api/questions/**", "/api/topics/**").hasRole("ADMIN") // Admin Endpoints (requires JWT with ADMIN role)
                        .anyRequest().authenticated() // All other requests must be authenticated
                );

        // 6. Add the JWT Filter BEFORE Spring's standard authentication filter
         http.addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/*")
                        .allowedOriginPatterns("*")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

}
