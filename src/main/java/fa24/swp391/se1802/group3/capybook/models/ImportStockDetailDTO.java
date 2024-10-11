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
@Table(name = "importstockdetail")
public class ImportStockDetailDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "isdid")
    private Integer isdid;
    @Column(name = "isdquantity")
    private Integer iSDQuantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "importprice")
    private BigDecimal importPrice;
    @JoinColumn(name = "bookid", referencedColumnName = "bookid")
    @ManyToOne
    private BookDTO bookID;
    @JoinColumn(name = "isid", referencedColumnName = "isid")
    @ManyToOne
    private ImportStockDTO isid;


}