package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Supplier")
public class Supplier {
    //Define fields for supplier class
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supID")
    private int supID;
    @Column(name = "supName")
    private String supName;
    @Column(name = "supEmail")
    private String supEmail;
    @Column(name = "supPhone")
    private String supPhone;
    @Column(name = "supAddress")
    private String supAddress;
    @Column(name = "status")
    private int status;
    //Define constructor for supplier class
    public Supplier(int supID, String supName, String supEmail, String supPhone, String supAddress, int status) {
        this.supID = supID;
        this.supName = supName;
        this.supEmail = supEmail;
        this.supPhone = supPhone;
        this.supAddress = supAddress;
        this.status = status;
    }

    public Supplier() {
    }
    //Define getter/setter for supplier class

    public int getSupID() {
        return supID;
    }

    public void setSupID(int supID) {
        this.supID = supID;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }

    public String getSupEmail() {
        return supEmail;
    }

    public void setSupEmail(String supEmail) {
        this.supEmail = supEmail;
    }

    public String getSupPhone() {
        return supPhone;
    }

    public void setSupPhone(String supPhone) {
        this.supPhone = supPhone;
    }

    public String getSupAddress() {
        return supAddress;
    }

    public void setSupAddress(String supAddress) {
        this.supAddress = supAddress;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    //Define toString for supplier class
    @Override
    public String toString() {
        return "Supplier{" +
                "supID=" + supID +
                ", supName='" + supName + '\'' +
                ", supEmail='" + supEmail + '\'' +
                ", supPhone='" + supPhone + '\'' +
                ", supAddress='" + supAddress + '\'' +
                ", status=" + status +
                '}';


    }


}



