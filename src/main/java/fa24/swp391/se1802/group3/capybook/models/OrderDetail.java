package fa24.swp391.se1802.group3.capybook.models;
import jakarta.persistence.*;
import java.util.List;
@Entity
@Table(name = "OrderDetail")
public class OrderDetail {
    @Id
    @Column(name = "ODID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ODID;

    @ManyToOne
    @JoinColumn(name ="bookID" )
    private Book bookID;

    @ManyToOne
    @JoinColumn(name = "orderID")
    private Order orderID;

    @Column(name = "quantity")
    private int quantity;


}