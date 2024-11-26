package fa24.swp391.se1802.group3.capybook.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "promotion_log")
public class PromotionLogDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pro_log_id")
    private Integer proLogId;
    @Column(name = "pro_action")
    private Integer proAction;
    @JoinColumn(name = "pro_id", referencedColumnName = "proID")
    @ManyToOne
    private PromotionDTO proId;
    @JoinColumn(name = "staff_id", referencedColumnName = "staffID")
    @ManyToOne
    private StaffDTO staffId;
}
