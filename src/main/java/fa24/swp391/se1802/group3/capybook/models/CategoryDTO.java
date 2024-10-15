package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "category")
public class CategoryDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "catid")
    private Integer catID;
    @Column(name = "catname")
    private String catName;
    @Column(name = "catstatus")
    private Integer catStatus;
    @OneToMany(mappedBy = "parentCatID")
    private Collection<CategoryDTO> categoryCollection;
    @JoinColumn(name = "parentcatid", referencedColumnName = "catid")
    @ManyToOne
    private CategoryDTO parentCatID;
    @OneToMany(mappedBy = "catID")
    private Collection<BookDTO> bookCollection;



}
