
package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "staff")
public class StaffDTO {
    //Define fields for staff class
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "staffid")
    private Integer staffID;
    @OneToMany(mappedBy = "staffID")
    private Collection<OrderDTO> orderDTO1Collection;
    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne
    private AccountDTO username;
    @OneToMany(mappedBy = "managerID")
    private Collection<StaffDTO> staffDTOCollection;
    @JoinColumn(name = "managerid", referencedColumnName = "staffid")
    @ManyToOne
    private StaffDTO managerID;
    @OneToMany(mappedBy = "createdBy")
    private Collection<PromotionDTO> promotionDTOCollection;
    @OneToMany(mappedBy = "approvedBy")
    private Collection<PromotionDTO> promotionDTOCollection1;
    @OneToMany(mappedBy = "staffID")
    private Collection<ImportStockDTO> importStockDTOCollection;


}
