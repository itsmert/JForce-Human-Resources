package JForce.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/api/staff/save").permitAll()
                        .requestMatchers("/api/staff/all").permitAll()
                        .requestMatchers("/api/staff/update").permitAll()
                        .requestMatchers("/api/staff/register").permitAll()
                        .requestMatchers("/api/staff/export/csv").permitAll()
                        .requestMatchers("/api/staff-entry-exit/**").permitAll()
                        .requestMatchers("/api/staff-entry-exit/dto/all").permitAll()
                        .requestMatchers("/api/staff-entry-exit/export/csv").permitAll()
                        .requestMatchers("/api/inventory-assignment/assign").permitAll()
                        .requestMatchers("/api/inventory-assignment/return").permitAll()
                        .requestMatchers("/api/staff-inventory/assign-with-inventory").permitAll()
                        .requestMatchers("/api/inventory/staff-inventory/assign-with-inventory").permitAll()
                        .requestMatchers("/api/inventory/available-inventory").permitAll()
                        .requestMatchers("/api/staff-inventory/available-inventory").permitAll()
                        .requestMatchers("/api/staff-inventory/dto/all").permitAll()
                        .requestMatchers("/api/staff-inventory/dto/**").permitAll()
                        .requestMatchers("/api/inventory-types").permitAll()
                        .requestMatchers("/api/inventory-types/available").permitAll()
                        .requestMatchers("/api/inventory-types/all").permitAll()
                        .requestMatchers("/api/inventory-types/available-or-unassigned").permitAll()
                        .requestMatchers("/api/staff-inventory/return").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/staff/**").hasAuthority("Human_Resources")
                        .requestMatchers("/api/staff/**").hasAuthority("Human_Resources")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
