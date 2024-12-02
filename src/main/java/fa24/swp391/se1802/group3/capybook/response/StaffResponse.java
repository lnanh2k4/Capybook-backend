package fa24.swp391.se1802.group3.capybook.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffResponse {
     String username;
     String firstName;
     String lastName;
     String password;
     Date dob;
     String email;
     String phone;
     Integer role;
     String address;
     Integer sex;
     Integer accStatus;
     Integer staffID;
}
