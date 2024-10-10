
package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Staff")
public class Staff {
    //Define fields for staff class
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OneToMany(mappedBy = "staffID")
    private int staffID;
    @OneToOne(mappedBy = "username")
    private Account username;
    @OneToOne(mappedBy = "staffID")
    private Staff managerID;

}
