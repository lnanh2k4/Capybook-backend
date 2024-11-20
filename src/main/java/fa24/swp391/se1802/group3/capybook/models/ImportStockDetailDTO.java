package fa24.swp391.se1802.group3.capybook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "importstockdetail")
public class ImportStockDetailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "isdid")
    private Integer isdid;

    @Column(name = "isdquantity")
    @JsonProperty("iSDQuantity")
    private Integer iSDQuantity;

    @Column(name = "importprice")
    private BigDecimal importPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookid", referencedColumnName = "bookid")
    private BookDTO bookID;


    @JoinColumn(name = "isid", referencedColumnName = "isid")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore // Bỏ qua isid trong serialization để tránh vòng lặp
    private ImportStockDTO isid;
}
