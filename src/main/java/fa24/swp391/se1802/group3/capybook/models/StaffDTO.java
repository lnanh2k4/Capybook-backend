
package fa24.swp391.se1802.group3.capybook.models;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    private Collection<OrderDTO> orderDTOCollection;

    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne
    @JsonBackReference("account-staff")
    private AccountDTO username;

    @OneToMany(mappedBy = "managerID", fetch = FetchType.EAGER)
    @JsonManagedReference("staff-staff")
    @JsonIgnore
    private Collection<StaffDTO> staffDTOCollection;

    @JoinColumn(name = "managerid", referencedColumnName = "staffid")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference("staff-manager")
    private StaffDTO managerID;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    private Collection<PromotionDTO> createByCollection;

    @OneToMany(mappedBy = "approvedBy", fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    private Collection<PromotionDTO> approveByCollection;

    @OneToMany(mappedBy = "staffID", fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    private Collection<ImportStockDTO> importStockDTOCollection;


}
