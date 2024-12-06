package fa24.swp391.se1802.group3.capybook.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
 private final String[] PUBLIC_ENDPOINTS = {
         // Category endpoint
         "/api/v1/categories/"
         // Book endpoint
         ,"/api/v1/books/", "/api/v1/books/{bookId}",
         // Token endpoint
         "/api/auth/token", "/api/auth/introspect", "/api/auth/logout", "/api/auth/refresh",
         // Account endpoint
         "/api/v1/accounts/register", "/api/v1/accounts/email/send/", "/api/v1/accounts/change", "/api/v1/accounts/password/reset/", "/api/v1/accounts/email/verify/", "/api/v1/accounts/account/verify/"
 }; // url cho guest

    private final String[] SELLER_STAFF_ENDPOINTS = {
            //         Promotion endpoint
            "/api/v1/promotions", "/api/v1/promotions/{proID}", "/api/v1/promotions/search","/api/v1/promotions/logs",
            //         Category endpoint
            "/api/v1/categories/","/api/v1/categories/{catID}",
            //         Order endpoint
            "/api/v1/orders","/api/v1/orders/{orderID}", "/api/v1/orders/search",
            //Import stock endpoint
            "api/v1/importStock", "/api/v1/importStock/{id}/details",
//            Staff endpoint
            "api/v1/staffs/{id}","api/v1/staffs/username/{username}"
    };

    private final String[] WAREHOUSE_STAFF_ENDPOINTS = {
            //Import stock endpoint
            "api/v1/importStock", "/api/v1/importStock/{id}/details",
            //Supplier endpoint
            "api/v1/suppliers", "api/v1/suppliers/{id}", "api/v1/suppliers/{supID}", "api/v1/suppliers/search"
    }; // url cho warehouse staff
    private final String[] ADMIN_ENDPOINTS = {
//            Account endpoint
            "api/v1/accounts","api/v1/accounts/${username}",
            //            Staff endpoint
            "api/v1/staffs","api/v1/staffs/{id}","api/v1/staffs/username/{username}","api/v1/staffs/search/","api/v1/staffs/{staffID}"
    }; // url cho admin
    private final String[] CUSTOMER_ENDPOINTS = {
            //         Payment endpoint
            "/api/v1/payment", "/api/v1/payment/create", "/api/v1/payment/return",
            //         Order endpoint
            "/api/v1/orders/search" , "/api/v1/orders",
            //         Cart endpoint
            "/api/v1/cart/{username}","/api/v1/cart/add","/api/v1/cart/update", "/api/v1/cart/delete",
            //         Account endpoint
            "/api/v1/accounts/email/send/","/api/v1/accounts/change", "/api/v1/accounts/password/reset/", "/api/v1/accounts/email/verify/",

    }; // url cho customer


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

                                .requestMatchers(HttpMethod.GET,ADMIN_ENDPOINTS).hasAnyAuthority("SCOPE_ADMIN")
                                .requestMatchers(HttpMethod.POST,ADMIN_ENDPOINTS).hasAnyAuthority("SCOPE_ADMIN")
                                .requestMatchers(HttpMethod.PUT,ADMIN_ENDPOINTS).hasAnyAuthority("SCOPE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE,ADMIN_ENDPOINTS).hasAnyAuthority("SCOPE_ADMIN")

                                .requestMatchers(HttpMethod.GET, "/uploads/**").permitAll()
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
        config.setExposedHeaders(Arrays.asList("Authorization")); // Đảm bảo client lấy được token hoặc header khác
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }


}
