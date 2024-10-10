package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Staff")
public class Staff {
    //Define fields for staff class
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staffID")
    private int staffID;
    @Column(name = "username")
    @OneToOne(mappedBy = "username", cascade = CascadeType.ALL)
    private Account account;
    @Column(name = "managerID")
    private int managerID;

    //Define constructor for staff


    public Staff(int staffID, Account account, int managerID) {
        this.staffID = staffID;
        this.account = account;
        this.managerID = managerID;
    }

    public Staff() {
    }
//Define getters and setters method

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }


    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    //Define toString method


    @Override
    public String toString() {
        return "Staff{" +
                "staffID=" + staffID +
                ", account=" + account +
                ", managerID=" + managerID +
                '}';
    }
}
