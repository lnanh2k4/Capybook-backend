package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;

        import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Order")
public class Order {

    @Id
    @OneToMany(mappedBy = "orderID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderID;

    @ManyToOne
    @JoinColumn(name = "proID")
    private Promotion proID;

    @ManyToOne
    @JoinColumn(name = "username")
    private Account username;

    @ManyToOne
    @JoinColumn(name = "staffID")
    private Staff staffID;

    @Column(name = "orderDate")
    private Date orderDate;

    @Column(name = "orderStatus")
    private String orderStatus;

}