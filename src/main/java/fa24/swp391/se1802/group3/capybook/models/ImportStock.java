package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "ImportStock")
public class ImportStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ISID")
    private Integer isid;
    @Column(name = "importDate")
    @Temporal(TemporalType.DATE)
    private Date importDate;
    @Column(name = "ISStatus")
    private Integer iSStatus;
    @OneToMany(mappedBy = "isid")
    private Collection<ImportStockDetail> importStockDetailCollection;
    @JoinColumn(name = "staffID", referencedColumnName = "staffID")
    @ManyToOne
    private Staff staffID;
    @JoinColumn(name = "supID", referencedColumnName = "supID")
    @ManyToOne
    private Supplier supID;

}