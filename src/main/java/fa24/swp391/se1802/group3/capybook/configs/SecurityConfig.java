package fa24.swp391.se1802.group3.capybook.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
 private final String[] PUBLIC_ENDPOINTS = {"/auth/token","/api/v1/accounts/", "/auth/introspect","/auth/logout","/auth/refresh"}; // url cho guest
    private final String[] SELLER_STAFF_ENDPOINTS = {"/auth/token","/api/v1/accounts/", "/auth/introspect"}; // url cho seller
    private final String[] WAREHOUSE_STAFF_ENDPOINTS = {"/auth/token","/api/v1/accounts/", "/auth/introspect"}; // url cho warehouse
    private final String[] ADMIN_ENDPOINTS = {"/auth/token","/api/v1/accounts/", "/auth/introspect"}; // url cho admin
    private final String[] CUSTOMER_ENDPOINTS = {"/auth/token","/api/v1/accounts/", "/auth/introspect"}; // url cho customer

    @Autowired
    private CustomerJwtDecoder customerJwtDecoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Tắt CSRF
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Áp dụng cấu hình CORS
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers(HttpMethod.POST,PUBLIC_ENDPOINTS).permitAll()
                                .requestMatchers(HttpMethod.GET,PUBLIC_ENDPOINTS).permitAll()

                                .requestMatchers(HttpMethod.GET,ADMIN_ENDPOINTS).hasAnyAuthority("SCOPE_ADMIN")
                                .requestMatchers(HttpMethod.POST,ADMIN_ENDPOINTS).hasAnyAuthority("SCOPE_ADMIN")
                                .requestMatchers(HttpMethod.PUT,ADMIN_ENDPOINTS).hasAnyAuthority("SCOPE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE,ADMIN_ENDPOINTS).hasAnyAuthority("SCOPE_ADMIN")

                                .requestMatchers(HttpMethod.POST,CUSTOMER_ENDPOINTS).hasAnyAuthority("SCOPE_CUSTOMER")
                                .requestMatchers(HttpMethod.GET,CUSTOMER_ENDPOINTS).hasAnyAuthority("SCOPE_CUSTOMER")
                                .requestMatchers(HttpMethod.PUT,CUSTOMER_ENDPOINTS).hasAnyAuthority("SCOPE_CUSTOMER")
                                .requestMatchers(HttpMethod.DELETE,CUSTOMER_ENDPOINTS).hasAnyAuthority("SCOPE_CUSTOMER")

                                .requestMatchers(HttpMethod.POST,SELLER_STAFF_ENDPOINTS).hasAnyAuthority("SCOPE_SELLER_STAFF")
                                .requestMatchers(HttpMethod.GET,SELLER_STAFF_ENDPOINTS).hasAnyAuthority("SCOPE_SELLER_STAFF")
                                .requestMatchers(HttpMethod.PUT,SELLER_STAFF_ENDPOINTS).hasAnyAuthority("SCOPE_SELLER_STAFF")
                                .requestMatchers(HttpMethod.DELETE,SELLER_STAFF_ENDPOINTS).hasAnyAuthority("SCOPE_SELLER_STAFF")

                                .requestMatchers(HttpMethod.POST,WAREHOUSE_STAFF_ENDPOINTS).hasAnyAuthority("SCOPE_WAREHOUSE_STAFF")
                                .requestMatchers(HttpMethod.GET,WAREHOUSE_STAFF_ENDPOINTS).hasAnyAuthority("SCOPE_WAREHOUSE_STAFF")
                                .requestMatchers(HttpMethod.PUT,WAREHOUSE_STAFF_ENDPOINTS).hasAnyAuthority("SCOPE_WAREHOUSE_STAFF")
                                .requestMatchers(HttpMethod.DELETE,WAREHOUSE_STAFF_ENDPOINTS).hasAnyAuthority("SCOPE_WAREHOUSE_STAFF")

                                .anyRequest().authenticated());

        http.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(customerJwtDecoder))
                );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // URL của frontend
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


}
