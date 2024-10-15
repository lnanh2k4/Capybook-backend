package fa24.swp391.se1802.group3.capybook.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "importstock")
public class ImportStockDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "isid")
    private Integer isid;
    @Column(name = "importdate")
    @Temporal(TemporalType.DATE)
    private Date importDate;
    @Column(name = "isstatus")
    private Integer iSStatus;
    @OneToMany(mappedBy = "isid")
    @JsonManagedReference
    @JsonIgnore
    private Collection<ImportStockDetailDTO> importStockDetailCollection;
    @JoinColumn(name = "staffid", referencedColumnName = "staffid")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JsonIgnore
    private StaffDTO staffID;
    @JoinColumn(name = "supid", referencedColumnName = "supid")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JsonIgnore
    private SupplierDTO supID;

}