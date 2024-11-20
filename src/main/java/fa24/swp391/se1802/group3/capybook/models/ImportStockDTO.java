package fa24.swp391.se1802.group3.capybook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "importstock")
public class ImportStockDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "isid")
    private Integer isid;

    @Column(name = "importdate")
    @Temporal(TemporalType.DATE)
    private Date importDate;

    @Column(name = "isstatus")
    private Integer iSStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "staffid", referencedColumnName = "staffid")
    private StaffDTO staffID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supid", referencedColumnName = "supid")
    private SupplierDTO supID;

    @OneToMany(mappedBy = "isid", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Collection<ImportStockDetailDTO> importStockDetailCollection = new ArrayList<>();


    public ImportStockDTO(int isid) {
        this.isid = isid;
    }
}

