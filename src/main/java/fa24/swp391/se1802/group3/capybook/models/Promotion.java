package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;
        import org.hibernate.engine.internal.Cascade;

import javax.naming.Name;
import java.util.Date;

@Entity
@Table(name = "Promotion")
public class Promotion {
    @Id
    @Column(name = "proID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int proID;

    @Column(name = "createBy")
    private Staff createBy;

    @Column(name = "approvedBy")
    private Staff approvedBy;

    @Column(name = "proName")
    private String proName;

    @Column(name = "proCode")
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
}