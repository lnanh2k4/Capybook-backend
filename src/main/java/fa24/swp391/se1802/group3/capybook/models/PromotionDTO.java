
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
@Table(name = "promotion")
public class PromotionDTO {
    @Id
    @Basic(optional = false)
    @Column(name = "proid")
    private Integer proID;
    @Column(name = "proname")
    private String proName;
    @Column(name = "procode")
    private String proCode;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "discount")
    private Double discount;
    @Column(name = "startdate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "enddate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "prostatus")
    private Integer proStatus;
    @OneToMany(mappedBy = "proID")
    private Collection<OrderDTO> orderDTO1Collection;
    @JoinColumn(name = "createdby", referencedColumnName = "staffid")
    @ManyToOne
    private StaffDTO createdBy;
    @JoinColumn(name = "approvedby", referencedColumnName = "staffid")
    @ManyToOne
    private StaffDTO approvedBy;
}
