
package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Staff")
public class Staff {
    //Define fields for staff class
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "staffID")
    private Integer staffID;
    @OneToMany(mappedBy = "staffID")
    private Collection<Order> order1Collection;
    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne
    private Account username;
    @OneToMany(mappedBy = "managerID")
    private Collection<Staff> staffCollection;
    @JoinColumn(name = "managerID", referencedColumnName = "staffID")
    @ManyToOne
    private Staff managerID;
    @OneToMany(mappedBy = "createdBy")
    private Collection<Promotion> promotionCollection;
    @OneToMany(mappedBy = "approvedBy")
    private Collection<Promotion> promotionCollection1;
    @OneToMany(mappedBy = "staffID")
    private Collection<ImportStock> importStockCollection;


}
