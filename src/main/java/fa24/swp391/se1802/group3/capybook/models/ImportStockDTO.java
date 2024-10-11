package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "importstock")
public class ImportStockDTO {

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
    private Collection<ImportStockDetailDTO> importStockDetailCollection;
    @JoinColumn(name = "staffid", referencedColumnName = "staffid")
    @ManyToOne
    private StaffDTO staffID;
    @JoinColumn(name = "supid", referencedColumnName = "supid")
    @ManyToOne
    private SupplierDTO supID;

}