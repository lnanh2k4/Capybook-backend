
package fa24.swp391.se1802.group3.capybook.models;

import com.fasterxml.jackson.annotation.*;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "staffID")
public class StaffDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "staffid")
    private Integer staffID;

    // Relation with OrderDTO - Ignored in serialization
    @OneToMany(mappedBy = "staffID", fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<OrderDTO> orderDTOCollection;

    // Relation with AccountDTO - Eager fetch but no reference management
    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne(fetch = FetchType.EAGER)
    private AccountDTO username;

    // Relation with other Staff members as subordinates - Ignored in serialization
    @OneToMany(mappedBy = "managerID", fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<StaffDTO> staffDTOCollection;

    // Relation with manager
    @JoinColumn(name = "managerid", referencedColumnName = "staffid")
    @ManyToOne(fetch = FetchType.EAGER)
    private StaffDTO managerID;

    // Relation with PromotionDTO (createdBy) - Ignored in serialization
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<PromotionDTO> createByCollection;

    // Relation with PromotionDTO (approvedBy) - Ignored in serialization
    @OneToMany(mappedBy = "approvedBy", fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<PromotionDTO> approveByCollection;

    // Relation with ImportStockDTO - Ignored in serialization
    @OneToMany(mappedBy = "staffID", fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<ImportStockDTO> importStockDTOCollection;

    // Relation with NotificationDTO - Ignored in serialization
    @OneToMany(mappedBy = "staffID", fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<NotificationDTO> notificationCollection;
}
