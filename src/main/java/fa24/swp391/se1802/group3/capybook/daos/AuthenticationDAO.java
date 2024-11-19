package fa24.swp391.se1802.group3.capybook.daos;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import fa24.swp391.se1802.group3.capybook.models.AccountDTO;
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
import java.util.StringJoiner;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationDAO {
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;
    AccountDAO accountDAO;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            var account = accountDAO.findByUsername(request.getUsername());
            if (account == null) {
                throw new Exception("Account is not eixst");
            }
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            boolean authenticated = passwordEncoder.matches(request.getPassword(), account.getPassword());
            var token = generateToken(account);

            if (!authenticated) {
                throw new Exception("Unauthenticated!");
            }
            return AuthenticationResponse.builder().token(token).authenticate(true).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private String generateToken(AccountDTO accountDTO) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(accountDTO.getUsername())
                .issuer("capybook")
                .claim("scope", buildScope(accountDTO))
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return jwsObject.serialize();
    }

    public IntrospectResponse introspect(IntrospectRequest request) {
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

    private String buildScope(AccountDTO accountDTO){
        if(accountDTO.getRole()==0) return "ADMIN WAREHOUSE_STAFF SELLER_STAFF CUSTOMER";
        else if (accountDTO.getRole()==1) return "CUSTOMER";
        else if (accountDTO.getRole()==2) return "SELLER_STAFF CUSTOMER";
        else if (accountDTO.getRole()==3) return "WAREHOUSE_STAFF CUSTOMER";
        else  return "UNKNOWN";
    }
}

