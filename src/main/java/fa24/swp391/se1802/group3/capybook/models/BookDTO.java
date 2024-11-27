package fa24.swp391.se1802.group3.capybook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "book")
public class BookDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "bookid")
    private Integer bookID;

    @Column(name = "booktitle")
    private String bookTitle;

    @Column(name = "author")
    private String author;

    @Column(name = "translator")
    private String translator;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "publicationyear")
    private Integer publicationYear;

    @Column(name = "isbn")
    private String isbn;

    @Lob
    @Column(name = "image")
    private String image;

    @Lob
    @Column(name = "bookdescription")
    private String bookDescription;

    @Column(name = "hardcover")
    private Integer hardcover;

    @Column(name = "dimension")
    private String dimension;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "bookprice")
    private BigDecimal bookPrice;

    @Column(name = "bookquantity")
    private Integer bookQuantity;

    @Column(name = "bookstatus")
    private Integer bookStatus;

    @OneToMany(mappedBy = "bookID", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<OrderDetailDTO> orderDetailList;

    @OneToMany(mappedBy = "bookID", fetch = FetchType.LAZY)
    @JsonIgnore // Bỏ qua importStockDetailCollection trong serialization để tránh vòng lặp
    private List<ImportStockDetailDTO> importStockDetailList;

    @OneToMany(mappedBy = "bookId")
    private List<BookCategoryDTO> bookCategoryList;

    @OneToMany(mappedBy = "bookID")
    @JsonIgnore
    private List<CartDTO> cartList;
}
