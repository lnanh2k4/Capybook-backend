package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "Supplier")
public class Supplier {
    //Define fields for supplier class
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OneToMany(mappedBy = "supID")
    private int supID;
    @Column(name = "supName")
    private String supName;
    @Column(name = "supEmail")
    private String supEmail;
    @Column(name = "supPhone")
    private String supPhone;
    @Column(name = "supAddress")
    private String supAddress;
    @Column(name = "supstatus")
    private int supstatus;


}



