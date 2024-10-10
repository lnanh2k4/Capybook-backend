package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Book")
public class Book {
    //Define fields for book class
    @Id
    @OneToMany(cascade = CascadeType.ALL)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookID")
    private int bookID;

    @Column(name = "bookTitle", nullable = false)
    private String bookTitle;

    @Column(name = "author")
    private String author;

    @Column(name = "translator")
    private String translator;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "publicationYear")
    private int publicationYear;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "image")
    private String image;

    @Column(name = "bookDescription", columnDefinition = "TEXT")
    private String bookDescription;

    @Column(name = "hardcover")
    private int hardcover;

    @Column(name = "dimension")
    private String dimension;

    @Column(name = "weight")
    private float weight;

    @Column(name = "bookPrice", precision = 10, scale = 2)
    private int bookPrice;

    @Column(name = "bookQuantity")
    private int bookQuantity;

    @Column(name = "bookStatus")
    private int bookStatus;

    // Many-to-One relationship with Category
    @Column(name = "catID")
    private Category catID;


    //Define constructor for class


    //Define getters/setters method



}
