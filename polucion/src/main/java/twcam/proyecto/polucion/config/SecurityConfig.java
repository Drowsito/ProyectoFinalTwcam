package twcam.proyecto.polucion.config;

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
                        .requestMatchers("/estaciones").permitAll()
                        .requestMatchers("/estacion/*/status**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/estacion/*").hasAuthority("SCOPE_aud_polucion_estacion")

                        .requestMatchers("/estacion").hasAuthority("SCOPE_aud_ayuntamiento_admin")
                        .requestMatchers("/estacion/*").hasAuthority("SCOPE_aud_ayuntamiento_admin")

                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt());

        return http.build();
    }
}