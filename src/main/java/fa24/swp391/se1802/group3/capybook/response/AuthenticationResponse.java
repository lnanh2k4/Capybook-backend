package fa24.swp391.se1802.group3.capybook.response;

import fa24.swp391.se1802.group3.capybook.models.AccountDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    String token;
    boolean authenticate;
    AccountDTO accountDTO;
}
