
package fa24.swp391.se1802.group3.capybook.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "orderdetail")
public class OrderDetailDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "odid")
    private Integer odid;

    @Column(name = "quantity")
    private Integer quantity;

    @JoinColumn(name = "bookid", referencedColumnName = "bookid")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JsonIgnore
    private BookDTO bookID;

    @JoinColumn(name = "orderid", referencedColumnName = "orderid")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JsonIgnore
    private OrderDTO orderID;

}
