package JForce.JForce.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "\"Inventory\"")
public class Inventory {
    @Id
    @ColumnDefault("nextval('inventory_id_seq')")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @Column(name = "entry_date",nullable = false)
    private LocalDate entryDate;

    @Size(max = 100)
    @Column(name = "brand", length = 100,nullable = false)
    private String brand;

    @Size(max = 100)
    @Column(name = "model", length = 100,nullable = false)
    private String model;

    @Size(max = 100)
    @Column(name = "serial_number", length = 100,nullable = false)
    private String serialNumber;

    @Size(max = 50)
    @Column(name = "status", length = 50,nullable = false)
    private String status;

}