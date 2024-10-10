package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;

import java.util.List;

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

    public Category() {
    }

    public Category(String catID, String catName, int parentCatID, int catStatus) {
        this.catID = catID;
        this.catName = catName;
        this.parentCatID = parentCatID;
        this.catStatus = catStatus;
    }

    public String getCatID() {
        return catID;
    }

    public void setCatID(String catID) {
        this.catID = catID;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public int getParentCatID() {
        return parentCatID;
    }

    public void setParentCatID(int parentCatID) {
        this.parentCatID = parentCatID;
    }

    public int getCatStatus() {
        return catStatus;
    }

    public void setCatStatus(int catStatus) {
        this.catStatus = catStatus;
    }


    @Override
    public String toString() {
        return "Category{" +
                "catID='" + catID + '\'' +
                ", catName='" + catName + '\'' +
                ", parentCatID=" + parentCatID +
                ", catStatus=" + catStatus +
                '}';
    }
}
