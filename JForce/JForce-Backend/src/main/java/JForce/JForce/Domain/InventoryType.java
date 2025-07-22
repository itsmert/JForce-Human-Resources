    package JForce.JForce.Domain;

    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.Setter;

    @Entity
    @Table(name = "\"InventoryType\"")
    @Getter
    @Setter
    public class InventoryType {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name = "type_name", nullable = false, unique = true)
        private String typeName;
    }
