package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;
import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "catID")
    private String catID;
    @Column(name = "catName")
    private String catName;
    @Column(name = "parentCatID")
    private int parentCatID;
    @Column(name = "catStatus")
    private int catStatus;

}