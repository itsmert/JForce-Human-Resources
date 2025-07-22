package JForce.JForce.Domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "\"Staff_Entry_Exit_Status\"")
public class StaffEntryExitStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Staff_Entry_Exit_Status_id_gen")
    @SequenceGenerator(name = "Staff_Entry_Exit_Status_id_gen", sequenceName = "staff_entry_exit_status_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "staff_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

    private Staff staffId;

    @Column(name = "entry_date")
    private LocalDate entryDate;

    @Size(max = 100)
    @Column(name = "position", length = 100)
    private String position;

    @Size(max = 100)
    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "exit_date")
    private LocalDate exitDate;

    @Column(name = "exit_reason", length = Integer.MAX_VALUE)
    private String exitReason;

}