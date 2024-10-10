package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "Book")
public class Book {
    //Define fields for book class
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "bookID")
    private Integer bookID;
    @Column(name = "bookTitle")
    private String bookTitle;
    @Column(name = "author")
    private String author;
    @Column(name = "translator")
    private String translator;
    @Column(name = "publisher")
    private String publisher;
    @Column(name = "publicationYear")
    private Integer publicationYear;
    @Column(name = "isbn")
    private String isbn;
    @Lob
    @Column(name = "image")
    private String image;
    @Lob
    @Column(name = "bookDescription")
    private String bookDescription;
    @Column(name = "hardcover")
    private Integer hardcover;
    @Column(name = "dimension")
    private String dimension;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "weight")
    private Double weight;
    @Column(name = "bookPrice")
    private BigDecimal bookPrice;
    @Column(name = "bookQuantity")
    private Integer bookQuantity;
    @Column(name = "bookStatus")
    private Integer bookStatus;
    @OneToMany(mappedBy = "bookID")
    private Collection<OrderDetail> orderDetailCollection;
    @OneToMany(mappedBy = "bookID")
    private Collection<ImportStockDetail> importStockDetailCollection;
    @JoinColumn(name = "catID", referencedColumnName = "catID")
    @ManyToOne
    private Category catID;



}