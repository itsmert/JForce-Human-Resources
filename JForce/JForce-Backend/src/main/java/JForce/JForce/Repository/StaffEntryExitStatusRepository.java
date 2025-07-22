package JForce.JForce.Repository;

import JForce.JForce.Domain.StaffEntryExitStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StaffEntryExitStatusRepository extends JpaRepository<StaffEntryExitStatus, Integer> {

    Optional<StaffEntryExitStatus> findByStaffId_Id(Long staffId);
    List<StaffEntryExitStatus> findAllByStaffId_Id(Long staffId);
    @EntityGraph(attributePaths = {"staffId"})
    List<StaffEntryExitStatus> findAll();
}
