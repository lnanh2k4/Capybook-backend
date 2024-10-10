package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "Account")
public class Account {
    //Define fields for account class
    @Id
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "password")
    private String password;
    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "role")
    private Integer role;
    @Column(name = "address")
    private String address;
    @Column(name = "sex")
    private Integer sex;
    @Column(name = "accStatus")
    private Integer accStatus;
    @OneToMany(mappedBy = "username")
    private Collection<Order> orderCollection;
    @OneToMany(mappedBy = "username")
    private Collection<Staff> staffCollection;


}