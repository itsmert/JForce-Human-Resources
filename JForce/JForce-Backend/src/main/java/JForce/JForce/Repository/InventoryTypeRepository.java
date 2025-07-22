package JForce.JForce.Repository;

import JForce.JForce.Domain.InventoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InventoryTypeRepository extends JpaRepository<InventoryType, Integer> {

    Optional<InventoryType> findByTypeName(String typeName);
    boolean existsByTypeName(String type_name); // tekrar kontrolü için (opsiyonel)
    @Query("""
    SELECT it FROM InventoryType it
    WHERE 
        it.id IN (
            SELECT i.type.id FROM Inventory i WHERE i.status = 'Available'
        )
        OR NOT EXISTS (
            SELECT 1 FROM Inventory i WHERE i.type.id = it.id
        )
""")
    List<InventoryType> findAvailableOrUnassignedInventoryTypes();
}
