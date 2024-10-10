package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderID")
    private int orderID;

    @ManyToOne
    @JoinColumn(name = "proID", referencedColumnName = "proID")
    private Promotion promotion;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "staffID", referencedColumnName = "staffID")
    private Staff staff;

    @Column(name = "orderDate")
    private Date orderDate;

    @Column(name = "orderStatus")
    private String orderStatus;

    // One-to-Many relationship with Order
    @OneToMany(mappedBy = "ODID", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetailList;

    public Order(int orderID, Promotion promotion, Account account, Staff staff, Date orderDate, String orderStatus) {
        this.orderID = orderID;
        this.promotion = promotion;
        this.account = account;
        this.staff = staff;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }

    public Order() {
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Promotion getProID() {
        return promotion;
    }

    public void setProID(Promotion proID) {
        this.promotion = proID;
    }

    public Account getUsername() {
        return account;
    }

    public void setUsername(Account username) {
        this.account = username;
    }

    public Staff getStaffID() {
        return staff;
    }

    public void setStaffID(Staff staffID) {
        this.staff = staffID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", proID=" + promotion +
                ", username=" + account +
                ", staffID=" + staff +
                ", orderDate=" + orderDate +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }
}