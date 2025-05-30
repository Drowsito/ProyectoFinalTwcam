package twcam.proyecto.polucion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                        .requestMatchers("/estaciones/**").permitAll()
                        .requestMatchers("/estacion/*/status**").permitAll()

                        .requestMatchers("/estacion").hasRole("admin")
                        .requestMatchers("/estacion/{id}").hasRole("admin")

                        .requestMatchers("/estacion/{id}").hasRole("SCOPE_aud_polucion_estacion")
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt());

        return http.build();
    }
}