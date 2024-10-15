
package fa24.swp391.se1802.group3.capybook.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "staff")
public class StaffDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    //Define fields for staff class
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "staffid")
    private Integer staffID;
    @OneToMany(mappedBy = "staffID", fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    private Collection<OrderDTO> orderDTO1Collection;
    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne
    @JsonBackReference
    @JsonIgnore
    private AccountDTO username;
    @OneToMany(mappedBy = "managerID", fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    private Collection<StaffDTO> staffDTOCollection;
    @JoinColumn(name = "managerid", referencedColumnName = "staffid")
    @ManyToOne
    @JsonBackReference
    @JsonIgnore
    private StaffDTO managerID;
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    private Collection<PromotionDTO> promotionDTOCollection;
    @OneToMany(mappedBy = "approvedBy", fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    private Collection<PromotionDTO> promotionDTOCollection1;
    @OneToMany(mappedBy = "staffID", fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    private Collection<ImportStockDTO> importStockDTOCollection;


}
