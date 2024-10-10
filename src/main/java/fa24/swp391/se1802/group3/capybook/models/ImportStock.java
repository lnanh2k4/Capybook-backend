package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "ImportStock")
public class ImportStock {

    //Define fields for account class
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ISID") // Khóa chính ISID
    private int ISID;

    @ManyToOne
    @JoinColumn(name = "supID", referencedColumnName = "supID")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "StaffID", referencedColumnName = "StaffID")
    private Staff staff;

    @Column(name = "importDate")
    private Date importDate;

    @Column(name = "ISStatus")
    private int ISStatus;

    @OneToMany(mappedBy = "importStock", cascade = CascadeType.ALL)
    private List<ImportStockDetail> importStockDetails;

    //Define constructor for class ImportStock
    public ImportStock(Supplier supplier, Staff staff, Date importDate, int ISStatus) {
        this.supplier = supplier;
        this.staff = staff;
        this.importDate = importDate;
        this.ISStatus = ISStatus;
    }

    public ImportStock() {
    }

    // Define Getter and Setter for ImportStock class
    public int getISID() {
        return ISID;
    }

    public void setISID(int ISID) {
        this.ISID = ISID;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public int getISStatus() {
        return ISStatus;
    }

    public void setISStatus(int ISStatus) {
        this.ISStatus = ISStatus;
    }

    public List<ImportStockDetail> getImportStockDetails() {
        return importStockDetails;
    }

    public void setImportStockDetails(List<ImportStockDetail> importStockDetails) {
        this.importStockDetails = importStockDetails;
    }
}
