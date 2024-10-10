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
    @JoinColumn(name = "bookID")
    private Book bookID;
    
    @Column(name = "ISID")
    private ImportStock ISID;

    @Column(name = "ISDQuantity")
    private int ISDQuantity;

    @Column(name = "importPrice")
    private double importPrice;

    //Define constructor for class

}
