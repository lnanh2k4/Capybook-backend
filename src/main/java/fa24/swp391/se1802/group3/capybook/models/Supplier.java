
package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
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
    @Basic(optional = false)
    @Column(name = "supID")
    private Integer supID;
    @Column(name = "supName")
    private String supName;
    @Column(name = "supEmail")
    private String supEmail;
    @Column(name = "supPhone")
    private String supPhone;
    @Column(name = "supAddress")
    private String supAddress;
    @Column(name = "supStatus")
    private Integer supStatus;
    @OneToMany(mappedBy = "supID")
    private Collection<ImportStock> importStockCollection;


}
