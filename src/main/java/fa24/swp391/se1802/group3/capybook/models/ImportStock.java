package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ImportStock")
public class ImportStock {

    //Define fields for account class
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ISID")
    private int ISID;

    @Column(name = "supID")
    private Supplier supplier;

    @Column(name = "StaffID")
    private Staff staff;

    @Column(name = "importDate")
    private Date importDate;

    @Column(name = "ISStatus")
    private int ISStatus;


    //Define constructor for class ImportStock

}
