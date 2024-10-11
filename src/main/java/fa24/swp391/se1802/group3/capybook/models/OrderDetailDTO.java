
package fa24.swp391.se1802.group3.capybook.models;
import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "orderdetail")
public class OrderDetailDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "odid")
    private Integer odid;
    @Column(name = "quantity")
    private Integer quantity;
    @JoinColumn(name = "bookid", referencedColumnName = "bookid")
    @ManyToOne
    private BookDTO bookID;
    @JoinColumn(name = "orderid", referencedColumnName = "orderid")
    @ManyToOne
    private OrderDTO orderID;

}
