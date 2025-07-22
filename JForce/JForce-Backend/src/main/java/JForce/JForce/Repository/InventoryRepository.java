package JForce.JForce.Repository;

import JForce.JForce.Domain.Inventory;
import JForce.JForce.Domain.InventoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

   // List<Inventory> findByTypeContainingIgnoreCase(String type);

    List<Inventory> findByStatusIn(List<InventoryStatus> statuses);

}
