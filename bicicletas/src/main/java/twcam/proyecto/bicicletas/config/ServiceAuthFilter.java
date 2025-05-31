package twcam.proyecto.bicicletas.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class ServiceAuthFilter extends OncePerRequestFilter {

    @Value("${app.service.secret}")
    private String serviceSecret;

    private final List<AntPathRequestMatcher> protectedPaths = List.of(
            new AntPathRequestMatcher("/aparcamiento"),
            new AntPathRequestMatcher("/aparcamiento/*"));

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        if ("/aparcamiento/available".equals(path)) {
            return true; 
        }
        return protectedPaths.stream().noneMatch(matcher -> matcher.matches(request));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("X-Service-Auth");

        if (header == null || !header.equals(serviceSecret)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Access denied: Invalid or missing service authentication.");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
