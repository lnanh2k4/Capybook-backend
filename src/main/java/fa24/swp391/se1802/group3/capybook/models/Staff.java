package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Staff")
public class Staff {
    //Define fields for staff class
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staffID")
    private int staffID;
    @Column(name = "username")
    private Account username;
    @Column(name = "managerID")
    private int managerID;

}
