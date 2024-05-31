package uk.mushow.client.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${spring.username}")
    String username;
    @Value("${spring.password}")
    String password;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/login").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin(form -> form.loginProcessingUrl("/login")
                        .successHandler(customAuthenticationSuccessHandler())
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    public static class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                            Authentication authentication) throws IOException {
            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

            String targetUrl = determineTargetUrl(roles);
            if (response.isCommitted()) {
                return;
            }

            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }

        protected String determineTargetUrl(Set<String> roles) {
            String baseUrl = "http://localhost:10005";
            if (roles.contains("ROLE_DOCTOR")) {
                return baseUrl + "/doctor";
            } else if (roles.contains("ROLE_ORGANIZER")) {
                return baseUrl + "/organizer";
            } else {
                return baseUrl + "/";
            }
        }
    }

    @Bean
    public UserDetailsService users() {
        UserDetails doctor = User.builder()
                .username("doctor")
                .password(passwordEncoder().encode(password))
                .roles("DOCTOR").build();
        UserDetails organizer = User.builder()
                .username("organizer")
                .password(passwordEncoder().encode(password))
                .roles("ORGANIZER").build();

        return new InMemoryUserDetailsManager(doctor, organizer);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
