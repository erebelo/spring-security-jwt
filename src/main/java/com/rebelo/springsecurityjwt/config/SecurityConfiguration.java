package com.rebelo.springsecurityjwt.config;

import static com.rebelo.springsecurityjwt.constant.BusinessConstant.ANY_PATH_SUFFIX;
import static com.rebelo.springsecurityjwt.constant.BusinessConstant.AUTHORIZATION_PATH;
import static com.rebelo.springsecurityjwt.constant.BusinessConstant.HEALTH_CHECK_PATH;
import static com.rebelo.springsecurityjwt.constant.BusinessConstant.ROLE_PREFIX;
import static com.rebelo.springsecurityjwt.constant.BusinessConstant.USERS_PATH;
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import com.rebelo.springsecurityjwt.domain.enumeration.RoleEnum;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;

    private static final String[] PUBLIC_WHITELIST = {"/v3/api-docs", "/v3/api-docs/swagger-config", "/swagger-ui/**",
            HEALTH_CHECK_PATH, AUTHORIZATION_PATH + ANY_PATH_SUFFIX};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtAuthenticationFilter jwtAuthFilter)
            throws Exception {
        return httpSecurity.cors(Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers(toH2Console()).permitAll().requestMatchers(PUBLIC_WHITELIST)
                                .permitAll().requestMatchers(HttpMethod.DELETE).hasRole(RoleEnum.ADMIN.getCode())
                                // hasAuthority is more flexible for fine-grained permissions
                                .requestMatchers(HttpMethod.GET, USERS_PATH)
                                .hasAuthority(ROLE_PREFIX + RoleEnum.ADMIN.getCode()).anyRequest().authenticated())
                .headers(headers -> headers.frameOptions(FrameOptionsConfig::disable))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(Collections.singletonList("http://localhost:8005"));
        cors.setAllowedMethods(Collections.singletonList("*"));
        cors.setAllowedHeaders(Collections.singletonList("*"));
        cors.setExposedHeaders(Collections.singletonList("Authorization"));
        cors.setAllowCredentials(true);
        cors.setMaxAge(3600L);

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    private AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
