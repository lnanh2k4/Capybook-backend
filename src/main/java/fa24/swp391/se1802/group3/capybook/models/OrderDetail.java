
package fa24.swp391.se1802.group3.capybook.models;
import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "OrderDetail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ODID")
    private Integer odid;
    @Column(name = "quantity")
    private Integer quantity;
    @JoinColumn(name = "bookID", referencedColumnName = "bookID")
    @ManyToOne
    private Book bookID;
    @JoinColumn(name = "orderID", referencedColumnName = "orderID")
    @ManyToOne
    private Order orderID;

}
