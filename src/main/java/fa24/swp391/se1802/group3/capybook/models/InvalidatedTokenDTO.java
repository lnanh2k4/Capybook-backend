package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "invalidatedtoken")
public class InvalidatedTokenDTO {
    @Id
    String id;
    @Column(name = "expirytime")
    Date expiryTime;
}
