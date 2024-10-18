
package fa24.swp391.se1802.group3.capybook.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "supplier")
public class SupplierDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
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

    @OneToMany(mappedBy = "supID", fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    private Collection<ImportStockDTO> importStockDTOCollection;


}
