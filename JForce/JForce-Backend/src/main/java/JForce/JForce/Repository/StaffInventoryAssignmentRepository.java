package JForce.JForce.Repository;

import JForce.JForce.Domain.StaffInventoryAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffInventoryAssignmentRepository extends JpaRepository<StaffInventoryAssignment, Long> {

    List<StaffInventoryAssignment> findByStaffId_Id(Long staffId);

    @Query("SELECT sia FROM StaffInventoryAssignment sia JOIN FETCH sia.staffId")
    List<StaffInventoryAssignment> findAllWithStaff();
}
