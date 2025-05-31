package twcam.proyecto.ayuntamiento.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/aparcamientoCercano**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/aggregatedData").permitAll()

                        .requestMatchers("/aparcamiento").hasAuthority("SCOPE_aud_ayuntamiento_admin")
                        .requestMatchers("/aparcamiento/*").hasAuthority("SCOPE_aud_ayuntamiento_admin")
                        .requestMatchers("/estacion").hasAuthority("SCOPE_aud_ayuntamiento_admin")
                        .requestMatchers("/estacion/*").hasAuthority("SCOPE_aud_ayuntamiento_admin")

                        .requestMatchers(HttpMethod.POST,"/aggregateData").hasAuthority("SCOPE_aud_ayuntamiento_servicio")
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt());

        return http.build();
    }
}