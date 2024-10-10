package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "category")
public class Category {
    @Id
    @Basic(optional = false)
    @Column(name = "catID")
    private Integer catID;
    @Column(name = "catName")
    private String catName;
    @Column(name = "catStatus")
    private Integer catStatus;
    @OneToMany(mappedBy = "parentCatID")
    private Collection<Category> categoryCollection;
    @JoinColumn(name = "parentCatID", referencedColumnName = "catID")
    @ManyToOne
    private Category parentCatID;
    @OneToMany(mappedBy = "catID")
    private Collection<Book> bookCollection;



}
