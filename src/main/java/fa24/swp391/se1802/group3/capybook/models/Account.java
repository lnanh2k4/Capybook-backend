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
    @Column(name = "username")
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
    @Column(name = "accStatus")
    private int accStatus;

}
