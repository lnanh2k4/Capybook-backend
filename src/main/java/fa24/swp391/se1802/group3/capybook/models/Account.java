package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "Account")
public class Account {
    //Define fields for account class
    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username")
    private String username;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "password")
    private String password;
    @Column(name = "dob")
    private Date dob;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "role")
    private int role;
    @Column(name = "address")
    private String address;
    @Column(name = "sex")
    private int sex;
    @Column(name = "status")
    private int status;

    // One-to-Many relationship with Order
    @OneToMany(mappedBy = "username", cascade = CascadeType.ALL)
    private List<Order> orders;

    //Define constructor for class

    public Account(String username, String firstName, String lastName, String password, Date dob, String email, String phone, int role, String address, int sex, int status) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.dob = dob;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.address = address;
        this.sex = sex;
        this.status = status;
    }

    public Account() {
    }

    //Define getters/setters method

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    //Define toString method

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                ", address='" + address + '\'' +
                ", sex=" + sex +
                ", status=" + status +
                '}';
    }
}
