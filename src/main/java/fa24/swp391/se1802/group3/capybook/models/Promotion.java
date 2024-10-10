
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
@Table(name = "Promotion")
public class Promotion {
    @Id
    @Basic(optional = false)
    @Column(name = "proID")
    private Integer proID;
    @Column(name = "proName")
    private String proName;
    @Column(name = "proCode")
    private String proCode;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "discount")
    private Double discount;
    @Column(name = "startDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "endDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "proStatus")
    private Integer proStatus;
    @OneToMany(mappedBy = "proID")
    private Collection<Order> order1Collection;
    @JoinColumn(name = "createdBy", referencedColumnName = "staffID")
    @ManyToOne
    private Staff createdBy;
    @JoinColumn(name = "approvedBy", referencedColumnName = "staffID")
    @ManyToOne
    private Staff approvedBy;
}
