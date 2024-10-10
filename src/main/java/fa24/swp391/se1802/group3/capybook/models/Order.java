
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
@Table(name = "Order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "orderID")
    private Integer orderID;
    @Column(name = "orderDate")
    @Temporal(TemporalType.DATE)
    private Date orderDate;
    @Column(name = "orderStatus")
    private Integer orderStatus;
    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne
    private Account username;
    @JoinColumn(name = "proID", referencedColumnName = "proID")
    @ManyToOne
    private Promotion proID;
    @JoinColumn(name = "staffID", referencedColumnName = "staffID")
    @ManyToOne
    private Staff staffID;
    @OneToMany(mappedBy = "orderID")
    private Collection<OrderDetail> orderDetailCollection;

}
