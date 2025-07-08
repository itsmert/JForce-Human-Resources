package JForce.JForce.Repository;

import JForce.JForce.Domain.StaffInventoryAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffInventoryAssignmentRepository extends JpaRepository<StaffInventoryAssignment, Integer> {

    List<StaffInventoryAssignment> findByStaffId_Id(Long staffId);
}
