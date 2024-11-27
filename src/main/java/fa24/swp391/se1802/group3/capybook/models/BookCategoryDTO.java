package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;
import lombok.*;

import java.awt.print.Book;
import java.io.Serializable;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "book_category")
public class BookCategoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "book_cate_id")
    private Integer bookCateId;
    @JoinColumn(name = "book_id", referencedColumnName = "bookID")
    @ManyToOne
    private Book bookId;
    @JoinColumn(name = "cat_id", referencedColumnName = "catID")
    @ManyToOne
    private CategoryDTO catId;
}
