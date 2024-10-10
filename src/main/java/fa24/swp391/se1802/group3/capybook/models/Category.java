package fa24.swp391.se1802.group3.capybook.models;


import jakarta.persistence.*;

@Entity
@Table(name = "Category")
public class Category {
    @Id
    @OneToMany(mappedBy = "catID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int catID;
    @Column(name = "catName")
    private String catName;
    @Column(name = "parentCatID")
    private Category parentCatID;
    @Column(name = "catStatus")
    private int catStatus;


}
