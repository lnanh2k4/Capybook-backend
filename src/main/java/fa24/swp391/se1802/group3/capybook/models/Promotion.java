package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;
        import org.hibernate.engine.internal.Cascade;

import javax.naming.Name;
import java.util.Date;

@Entity
@Table(name = "Promotion")
public class Promotion {
    @Id
    @OneToMany
    @Column(name = "proID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int proID;

    @ManyToOne
    @JoinColumn(name = "createBy", referencedColumnName = "staffID")
    private Staff createBy;

    @ManyToOne
    @JoinColumn(name = "approvedBy", referencedColumnName = "staffID")
    private Staff approvedBy;

    @Column(name = "proName")
    private String proName;

    @Column(name = "creatproCodeeBy")
    private String proCode;

    @Column(name = "discount")
    private float discount;

    @Column(name = "startDate")
    private Date startDate;

    @Column(name = "endDate")
    private Date endDate;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "proStatus")
    private int proStatus;


    public Promotion(int proID, Staff createBy, Staff approvedBy, String proName, String proCode, float discount, Date startDate, Date endDate, int quantity, int proStatus) {
        this.proID = proID;
        this.createBy = createBy;
        this.approvedBy = approvedBy;
        this.proName = proName;
        this.proCode = proCode;
        this.discount = discount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.quantity = quantity;
        this.proStatus = proStatus;
    }

    public Promotion() {
    }

    public int getProID() {
        return proID;
    }

    public void setProID(int proID) {
        this.proID = proID;
    }

    public Staff getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Staff createBy) {
        this.createBy = createBy;
    }

    public Staff getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Staff approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProStatus() {
        return proStatus;
    }

    public void setProStatus(int proStatus) {
        this.proStatus = proStatus;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "proID=" + proID +
                ", createBy=" + createBy +
                ", approvedBy=" + approvedBy +
                ", proName='" + proName + '\'' +
                ", proCode='" + proCode + '\'' +
                ", discount=" + discount +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", quantity=" + quantity +
                ", proStatus=" + proStatus +
                '}';
    }
}