package fa24.swp391.se1802.group3.capybook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "staff")
public class StaffDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "staffid")
    private Integer staffID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private AccountDTO username;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "managerid", referencedColumnName = "staffid")
    private StaffDTO managerID;

    // Ignoring other collections that don’t need serialization
    @OneToMany(mappedBy = "staffID", fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<OrderDTO> orderDTOCollection;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<PromotionDTO> createByCollection;

    @OneToMany(mappedBy = "approvedBy", fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<PromotionDTO> approveByCollection;

    @OneToMany(mappedBy = "staffID", fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<ImportStockDTO> importStockDTOCollection;

    @OneToMany(mappedBy = "staffID", fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<NotificationDTO> notificationCollection;
}
