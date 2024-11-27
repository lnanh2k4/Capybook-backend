package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;
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
    String ITID;
    @Column(name = "expirytime")
    Date expiryTime;
    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne
    private AccountDTO username;
}
