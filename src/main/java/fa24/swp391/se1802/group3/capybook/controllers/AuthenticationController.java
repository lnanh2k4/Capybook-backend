package fa24.swp391.se1802.group3.capybook.controllers;

import fa24.swp391.se1802.group3.capybook.daos.AuthenticationDAO;
import fa24.swp391.se1802.group3.capybook.request.AuthenticationRequest;
import fa24.swp391.se1802.group3.capybook.request.IntrospectRequest;
import fa24.swp391.se1802.group3.capybook.response.AuthenticationResponse;
import fa24.swp391.se1802.group3.capybook.response.IntrospectResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationDAO authenticationDAO;
    @PostMapping("/token")
    ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
       AuthenticationResponse response = authenticationDAO.authenticate(request);
       return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping("/introspect")
    ResponseEntity<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request){
        IntrospectResponse response = authenticationDAO.introspect(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
