package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Book")
public class Book {
    //Define fields for book class
    @Id
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
    @ManyToOne
    @JoinColumn(name = "catID", referencedColumnName = "catID")
    private Category category;

    //Define constructor for class
    public Book(String bookTitle, String author, String translator, String publisher, int publicationYear, String isbn, String image, String bookDescription, int hardcover, String dimension, float weight, int bookPrice, int bookQuantity, int bookStatus) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.translator = translator;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.image = image;
        this.bookDescription = bookDescription;
        this.hardcover = hardcover;
        this.dimension = dimension;
        this.weight = weight;
        this.bookPrice = bookPrice;
        this.bookQuantity = bookQuantity;
        this.bookStatus = bookStatus;
    }

    //Define getters/setters method
    public Book() {
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public int getHardcover() {
        return hardcover;
    }

    public void setHardcover(int hardcover) {
        this.hardcover = hardcover;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(int bookPrice) {
        this.bookPrice = bookPrice;
    }

    public int getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(int bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

    public int getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(int bookStatus) {
        this.bookStatus = bookStatus;
    }
    //Define toString method
    @Override
    public String toString() {
        return "Book{" +
                "bookID=" + bookID +
                ", bookTitle='" + bookTitle + '\'' +
                ", author='" + author + '\'' +
                ", translator='" + translator + '\'' +
                ", publisher='" + publisher + '\'' +
                ", publicationYear=" + publicationYear +
                ", isbn='" + isbn + '\'' +
                ", image='" + image + '\'' +
                ", bookDescription='" + bookDescription + '\'' +
                ", hardcover=" + hardcover +
                ", dimension='" + dimension + '\'' +
                ", weight=" + weight +
                ", bookPrice=" + bookPrice +
                ", bookQuantity=" + bookQuantity +
                ", bookStatus=" + bookStatus +
                '}';
    }
}
