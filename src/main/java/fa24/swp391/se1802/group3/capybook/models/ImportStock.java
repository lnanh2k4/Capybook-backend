package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "ImportStock")
public class ImportStock {

    //Define fields for account class
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ISID")
    private int ISID;

    @ManyToOne
    @JoinColumn(name = "supID")
    private Supplier supID;

    @ManyToOne
    @JoinColumn(name = "staffID")
    private Staff staffID;

    @Column(name = "importDate")
    private Date importDate;

    @Column(name = "ISStatus")
    private int ISStatus;


    //Define constructor for class ImportStock

}
