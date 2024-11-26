package fa24.swp391.se1802.group3.capybook.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fa24.swp391.se1802.group3.capybook.daos.AuthenticationDAO;
import fa24.swp391.se1802.group3.capybook.request.AuthenticationRequest;
import fa24.swp391.se1802.group3.capybook.request.IntrospectRequest;
import fa24.swp391.se1802.group3.capybook.request.LogoutRequest;
import fa24.swp391.se1802.group3.capybook.request.RefreshToken;
import fa24.swp391.se1802.group3.capybook.response.AuthenticationResponse;
import fa24.swp391.se1802.group3.capybook.response.IntrospectResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationDAO authenticationDAO;
    @PostMapping(value = "/token")
    ResponseEntity<AuthenticationResponse> authenticate(@RequestPart("login") String login)  {
        try {
            System.out.println("Request received");
            ObjectMapper objectMapper = new ObjectMapper();
            AuthenticationRequest request = objectMapper.readValue(login,AuthenticationRequest.class);
            AuthenticationResponse response = authenticationDAO.authenticate(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (JsonMappingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/introspect")
    ResponseEntity<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request){
        IntrospectResponse response = authenticationDAO.introspect(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping("/logout")
    void logout(@RequestBody LogoutRequest request) throws Exception {
        System.out.println("logout successfully!");
             authenticationDAO.logout(request);
    }

    @PostMapping("/refresh")
    ResponseEntity<AuthenticationResponse> authenticate(@RequestBody RefreshToken request) throws Exception {
        System.out.println("Đã vào token");
        var response = authenticationDAO.refreshToken(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
