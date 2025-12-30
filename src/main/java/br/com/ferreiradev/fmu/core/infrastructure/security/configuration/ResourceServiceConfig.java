package br.com.ferreiradev.fmu.core.infrastructure.security.configuration;

import br.com.ferreiradev.fmu.core.infrastructure.security.JWTCustomAuthFilter;
import br.com.ferreiradev.fmu.core.infrastructure.security.LoginSocialSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class ResourceServiceConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            LoginSocialSuccessHandler successHandler,
            JWTCustomAuthFilter jwtAuthFilter
    ) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/login/**").permitAll();
                    auth.requestMatchers("/api/auth/login").permitAll();
                    auth.anyRequest().authenticated();
                })
                .oauth2Login(oauth -> oauth
                        .successHandler(successHandler)
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                )
                .addFilterAfter(jwtAuthFilter, BearerTokenAuthenticationFilter.class)
                .build();
    }

}
