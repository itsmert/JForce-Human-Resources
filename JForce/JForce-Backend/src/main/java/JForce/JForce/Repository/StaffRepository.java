package JForce.JForce.Repository;

import JForce.JForce.Domain.Staff;
import JForce.JForce.Service.DTO.StaffLoginResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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


    @Query("SELECT s FROM Staff s " +
            "WHERE (:name IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:surname IS NULL OR LOWER(s.surname) LIKE LOWER(CONCAT('%', :surname, '%'))) " +
            "AND (:turkishIdentity IS NULL OR s.turkishIdentity = :turkishIdentity) " +
            "AND (:unitId IS NULL OR s.unit.id = :unitId)")
    List<Staff> filterStaff(
            @Param("name") String name,
            @Param("surname") String surname,
            @Param("turkishIdentity") String turkishIdentity,
            @Param("unitId") Integer unitId
    );
}