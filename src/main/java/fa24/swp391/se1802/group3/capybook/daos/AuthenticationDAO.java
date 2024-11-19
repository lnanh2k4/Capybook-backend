package fa24.swp391.se1802.group3.capybook.daos;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import fa24.swp391.se1802.group3.capybook.request.AuthenticationRequest;
import fa24.swp391.se1802.group3.capybook.request.IntrospectRequest;
import fa24.swp391.se1802.group3.capybook.response.AuthenticationResponse;
import fa24.swp391.se1802.group3.capybook.response.IntrospectResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.time.Instant;

import java.time.temporal.ChronoUnit;
import java.util.Date;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationDAO {
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;
    AccountDAO accountDAO;
  public AuthenticationResponse authenticate(AuthenticationRequest request)  {
        try {
            var account = accountDAO.findByUsername(request.getUsername());
            if(account==null){
                throw new Exception("Account is not eixst");
            }
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            boolean authenticated = passwordEncoder.matches(request.getPassword(), account.getPassword());

          if(!authenticated){
              throw new Exception("Unauthenticated!");
          }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
      var token = generateToken(request.getUsername());
      return AuthenticationResponse.builder().token(token).authenticate(true).build();
    }

    private String generateToken(String username) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("capybook")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS ).toEpochMilli()))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

      JWSObject jwsObject = new JWSObject(header,payload);

     try {
         jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));

     } catch (Exception e){
         System.out.println(e.getMessage());
     }
        return jwsObject.serialize();
    }

    public IntrospectResponse introspect(IntrospectRequest request){
      var token = request.getToken();
        try {
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

            SignedJWT signedJWT = SignedJWT.parse(token);

            Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            var verified = signedJWT.verify(verifier);
            return IntrospectResponse.builder()
                    .isValid(verified && expityTime.after(new Date()))
                    .build();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}

