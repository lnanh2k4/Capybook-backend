package fa24.swp391.se1802.group3.capybook.models;


import jakarta.persistence.*;

@Entity
@Table(name = "ImportStockDetail")
public class ImportStockDetail {

    //Define fields for account class
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ISDID")
    private int ISDID;

    @ManyToOne
    @JoinColumn(name = "bookID", referencedColumnName = "bookID")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "ISID", referencedColumnName = "ISID")
    private ImportStock importStock;

    @Column(name = "ISDQuantity")
    private int ISDQuantity;

    @Column(name = "importPrice")
    private double importPrice;

    //Define constructor for class
    public ImportStockDetail(Book book, ImportStock importStock, int ISDQuantity, double importPrice) {
        this.book = book;
        this.importStock = importStock;
        this.ISDQuantity = ISDQuantity;
        this.importPrice = importPrice;
    }

    public ImportStockDetail() {
    }

    //Define getters/setters method
    public int getISDID() {
        return ISDID;
    }

    public void setISDID(int ISDID) {
        this.ISDID = ISDID;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public ImportStock getImportStock() {
        return importStock;
    }

    public void setImportStock(ImportStock importStock) {
        this.importStock = importStock;
    }

    public int getISDQuantity() {
        return ISDQuantity;
    }

    public void setISDQuantity(int ISDQuantity) {
        this.ISDQuantity = ISDQuantity;
    }

    public double getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(double importPrice) {
        this.importPrice = importPrice;
    }
}
