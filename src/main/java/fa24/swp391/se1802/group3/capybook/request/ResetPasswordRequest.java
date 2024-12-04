package fa24.swp391.se1802.group3.capybook.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPasswordRequest {
    String username;
    String password;
}
