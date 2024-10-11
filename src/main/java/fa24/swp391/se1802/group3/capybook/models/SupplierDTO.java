
package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "supplier")
public class SupplierDTO {
    //Define fields for supplier class
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "supid")
    private Integer supID;
    @Column(name = "supname")
    private String supName;
    @Column(name = "supemail")
    private String supEmail;
    @Column(name = "supphone")
    private String supPhone;
    @Column(name = "supaddress")
    private String supAddress;
    @Column(name = "supstatus")
    private Integer supStatus;
    @OneToMany(mappedBy = "supID")
    private Collection<ImportStockDTO> importStockDTOCollection;


}
