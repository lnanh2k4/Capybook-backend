
package fa24.swp391.se1802.group3.capybook.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "`order`")
public class OrderDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "orderid")
    private Integer orderID;

    @Column(name = "orderdate")
    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @Column(name = "orderstatus")
    private Integer orderStatus;

    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private AccountDTO username;

    @JoinColumn(name = "proid", referencedColumnName = "proid")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private PromotionDTO proID;


    @JoinColumn(name = "staffid", referencedColumnName = "staffid")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JsonIgnore
    private StaffDTO staffID;

    @OneToMany(mappedBy = "orderID", fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    private Collection<OrderDetailDTO> orderDetailCollection;

}
