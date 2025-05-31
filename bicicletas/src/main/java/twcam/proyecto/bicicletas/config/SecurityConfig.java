package twcam.proyecto.bicicletas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final ServiceAuthFilter serviceAuthFilter;

    public SecurityConfig(ServiceAuthFilter serviceAuthFilter) {
        this.serviceAuthFilter = serviceAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/aparcamientos/**").permitAll()
                        .requestMatchers("/aparcamiento/*/status*").permitAll()
                        .requestMatchers("/aparcamiento/available").permitAll()

                        .requestMatchers("/aparcamiento").hasAuthority("SCOPE_aud_ayuntamiento_admin")
                        .requestMatchers("/aparcamiento/*").hasAuthority("SCOPE_aud_ayuntamiento_admin")

                        .requestMatchers("/evento/**").hasAuthority("SCOPE_aud_bicicletas_aparcamiento")
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt());

        return http.build();
    }
}
