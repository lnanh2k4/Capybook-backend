package fa24.swp391.se1802.group3.capybook.models;
import jakarta.persistence.*;
import java.util.List;
@Entity
@Table(name = "OrderDetail")
public class OrderDetail {
    @Id
    @Column(name = "ODID")
    @OneToMany
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ODID;
    @ManyToOne
    @JoinColumn(name = "bookID", referencedColumnName = "bookID")
    private Book bookID;
    @ManyToOne
    @JoinColumn(name = "orderID", referencedColumnName = "orderID")
    private Order orderID;
    @Column(name = "quantity")
    private int quantity;
    public OrderDetail(int ODID, Book bookID, Order orderID, int quantity) {
        this.ODID = ODID;
        this.bookID = bookID;
        this.orderID = orderID;
        this.quantity = quantity;
    }
    public OrderDetail() {
    }
    public int getODID() {
        return ODID;
    }
    public void setODID(int ODID) {
        this.ODID = ODID;
    }
    public Book getBookID() {
        return bookID;
    }
    public void setBookID(Book bookID) {
        this.bookID = bookID;
    }
    public Order getOrderID() {
        return orderID;
    }
    public void setOrderID(Order orderID) {
        this.orderID = orderID;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    @Override
    public String toString() {
        return "OrderDetail{" +
                "ODID=" + ODID +
                ", bookID=" + bookID +
                ", orderID=" + orderID +
                ", quantity=" + quantity +
                '}';
    }
}