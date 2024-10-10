package fa24.swp391.se1802.group3.capybook.models;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "ImportStockDetail")
public class ImportStockDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ISDID")
    private Integer isdid;
    @Column(name = "ISDQuantity")
    private Integer iSDQuantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "importPrice")
    private BigDecimal importPrice;
    @JoinColumn(name = "bookID", referencedColumnName = "bookID")
    @ManyToOne
    private Book bookID;
    @JoinColumn(name = "ISID", referencedColumnName = "ISID")
    @ManyToOne
    private ImportStock isid;


}