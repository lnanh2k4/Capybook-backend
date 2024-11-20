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
 private final String[] PUBLIC_ENDPOINTS = {"/api/auth/token",
         "/api/auth/introspect",
         "/api/auth/logout",
         "/api/auth/refresh",
         "/api/v1/accounts/register" ,"/api/v1/accounts/","/api/v1/accounts/{username}", "/api/v1/accounts/search", "/api/v1/accounts/change"
         ,"/api/v1/books/", "/api/v1/books/{bookId}",
    "/api/v1/cart","/api/v1/cart/add","/api/v1/cart/update", "/api/v1/cart/delete",
         "/api/v1/categories","/api/v1/categories/{catID}", "/api/v1/categories/{catID}/soft-delete", "/api/v1/categories/search",
         "api/v1/importStock",
         "/api/v1/importStock/{id}/details",
         "/api/v1/notifications","/api/v1/notifications/detail/{notID}", "/api/v1/notifications/{notTitle}", "/api/v1/notifications/{notID}",
         "/api/v1/orders","/api/v1/orders/{orderID}", "/api/v1/orders/search",
         "/api/v1/payment", "/api/v1/payment/create", "/api/v1/payment/return",
         "/api/v1/promotions", "/api/v1/promotions/{proID}", "/api/v1/promotions/search",
         "/api/v1/staff", "/api/v1/staff/{id}",
         "api/v1/suppliers", "api/v1/suppliers/{id}", "api/v1/suppliers/{supID}", "api/v1/suppliers/search"
 }; // url cho guest
    private final String[] SELLER_STAFF_ENDPOINTS = {"/api/auth/token",
            "/api/auth/introspect",
            "/api/auth/logout",
            "/api/auth/refresh",
            "/api/v1/accounts/register" ,"/api/v1/accounts/","/api/v1/accounts/{username}"
            ,"/api/v1/books/"}; // url cho seller
    private final String[] WAREHOUSE_STAFF_ENDPOINTS = {"/api/auth/token",
            "/api/auth/introspect",
            "/api/auth/logout",
            "/api/auth/refresh",
            "/api/v1/accounts/register" ,"/api/v1/accounts/","/api/v1/accounts/{username}"
            ,"/api/v1/books/"}; // url cho warehouse
    private final String[] ADMIN_ENDPOINTS = {"/api/auth/token",
            "/api/auth/introspect",
            "/api/auth/logout",
            "/api/auth/refresh",
            "/api/v1/accounts/register" ,"/api/v1/accounts/","/api/v1/accounts/{username}"
            ,"/api/v1/books/"}; // url cho admin
    private final String[] CUSTOMER_ENDPOINTS = {"/api/auth/token",
            "/api/auth/introspect",
            "/api/auth/logout",
            "/api/auth/refresh",
            "/api/v1/accounts/register" ,"/api/v1/accounts/","/api/v1/accounts/{username}"
            ,"/api/v1/books/"}; // url cho customer

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
                                .requestMatchers(HttpMethod.PUT,PUBLIC_ENDPOINTS).permitAll()

                                .requestMatchers(HttpMethod.GET,PUBLIC_ENDPOINTS).hasAnyAuthority("SCOPE_ADMIN")
                                .requestMatchers(HttpMethod.POST,PUBLIC_ENDPOINTS).hasAnyAuthority("SCOPE_ADMIN")
                                .requestMatchers(HttpMethod.PUT,PUBLIC_ENDPOINTS).hasAnyAuthority("SCOPE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE,PUBLIC_ENDPOINTS).hasAnyAuthority("SCOPE_ADMIN")

                                .requestMatchers(HttpMethod.POST,PUBLIC_ENDPOINTS).hasAnyAuthority("SCOPE_CUSTOMER")
                                .requestMatchers(HttpMethod.GET,PUBLIC_ENDPOINTS).hasAnyAuthority("SCOPE_CUSTOMER")
                                .requestMatchers(HttpMethod.PUT,PUBLIC_ENDPOINTS).hasAnyAuthority("SCOPE_CUSTOMER")
                                .requestMatchers(HttpMethod.DELETE,PUBLIC_ENDPOINTS).hasAnyAuthority("SCOPE_CUSTOMER")

                                .requestMatchers(HttpMethod.POST,PUBLIC_ENDPOINTS).hasAnyAuthority("SCOPE_SELLER_STAFF")
                                .requestMatchers(HttpMethod.GET,PUBLIC_ENDPOINTS).hasAnyAuthority("SCOPE_SELLER_STAFF")
                                .requestMatchers(HttpMethod.PUT,PUBLIC_ENDPOINTS).hasAnyAuthority("SCOPE_SELLER_STAFF")
                                .requestMatchers(HttpMethod.DELETE,PUBLIC_ENDPOINTS).hasAnyAuthority("SCOPE_SELLER_STAFF")

                                .requestMatchers(HttpMethod.POST,PUBLIC_ENDPOINTS).hasAnyAuthority("SCOPE_WAREHOUSE_STAFF")
                                .requestMatchers(HttpMethod.GET,PUBLIC_ENDPOINTS).hasAnyAuthority("SCOPE_WAREHOUSE_STAFF")
                                .requestMatchers(HttpMethod.PUT,PUBLIC_ENDPOINTS).hasAnyAuthority("SCOPE_WAREHOUSE_STAFF")
                                .requestMatchers(HttpMethod.DELETE,PUBLIC_ENDPOINTS).hasAnyAuthority("SCOPE_WAREHOUSE_STAFF")

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
