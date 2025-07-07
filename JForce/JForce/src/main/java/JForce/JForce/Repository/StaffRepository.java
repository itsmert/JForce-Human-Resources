package JForce.JForce.Repository;

import JForce.JForce.Domain.Staff;
import JForce.JForce.Service.DTO.StaffLoginResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Staff entity, providing access to database operations.
 */
@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    /**
     * Finds a Staff entity by username.
     *
     * @param username the username of the staff member
     * @return Staff object if found; otherwise null
     */
    Staff findByUsername(String username);

    /**
     * Returns login DTO for a staff.
     */
    @Query("SELECT new JForce.JForce.Service.DTO.StaffLoginResponseDTO(s.username, s.name, s.surname, s.role) FROM Staff s WHERE s.username = :username")
    StaffLoginResponseDTO findLoginDTOByUserName(@Param("username") String username);
}