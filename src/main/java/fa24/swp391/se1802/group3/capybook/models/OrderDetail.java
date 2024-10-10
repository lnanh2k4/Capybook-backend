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


    private Book book;


    private Order order;
    @Column(name = "quantity")
    private int quantity;
    public OrderDetail(int ODID, Book book, Order order, int quantity) {
        this.ODID = ODID;
        this.book = book;
        this.order = order;
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
    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }
    public Order getOrderID() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
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
                ", book=" + book +
                ", order=" + order +
                ", quantity=" + quantity +
                '}';
    }
}