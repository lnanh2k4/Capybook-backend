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
    private Account username;
    @Column(name = "managerID")
    private int managerID;

    //Define constructor for staff


    public Staff(int staffID, Account username, int managerID) {
        this.staffID = staffID;
        this.username = username;
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

    public Account getUsername() {
        return username;
    }

    public void setUsername(Account username) {
        this.username = username;
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    //Define toString method

    @Override
    public String toString() {
        return "Staff{" +
                "staffID=" + staffID +
                ", username='" + username + '\'' +
                ", managerID=" + managerID +
                '}';
    }
}
