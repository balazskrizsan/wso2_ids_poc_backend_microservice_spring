package com.kbalazsworks.wso2_ids_poc_backend_microservice_spring;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@Slf4j
public class SpringWebSecurityConfig
{
    @Value("${spring.security.oauth2.resourceserver.opaque.introspection-uri}")
    String introspectionUri;

    @Value("${spring.security.oauth2.resourceserver.opaque.introspection-client-id}")
    String clientId;

    @Value("${spring.security.oauth2.resourceserver.opaque.introspection-client-secret}")
    String clientSecret;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        return http
            .authorizeHttpRequests(authz -> authz.anyRequest().authenticated())
            .exceptionHandling(e -> e
                .accessDeniedHandler(loggingAccessDenied403Handler())
                .authenticationEntryPoint(loggingAuthenticationEntry401Point())
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .opaqueToken(token -> token
                    .introspectionUri(introspectionUri)
                    .introspectionClientCredentials(clientId, clientSecret)
                )
            )
            .build();
    }

    private AuthenticationEntryPoint loggingAuthenticationEntry401Point()
    {
        return (HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException ex) -> {
            log.warn(
                "401 Unauthorized – endpoint [{} {}], reason: {}",
                request.getMethod(),
                request.getRequestURI(),
                ex.getMessage()
            );
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        };
    }

    private AccessDeniedHandler loggingAccessDenied403Handler()
    {
        return (HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) -> {
            log.warn(
                "Access denied for request [{} {}], reason: {}, user: {}",
                request.getMethod(),
                request.getRequestURI(),
                accessDeniedException.getMessage(),
                request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "anonymous"
            );

            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        };
    }
}
