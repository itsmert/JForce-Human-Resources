package JForce.JForce.Domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "\"Staff_Inventory_Assignment\"")
public class StaffInventoryAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Staff_Inventory_Assignment_id_gen")
    @SequenceGenerator(name = "Staff_Inventory_Assignment_id_gen", sequenceName = "staff_inventory_assignment_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staffId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventoryId;

    @Column(name = "assign_date")
    private LocalDate assignDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by")
    private Staff assignedBy;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "received_by")
    private Staff receivedBy;

}