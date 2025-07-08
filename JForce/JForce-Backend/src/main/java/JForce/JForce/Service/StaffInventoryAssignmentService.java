package JForce.JForce.Service;

import JForce.JForce.Domain.StaffInventoryAssignment;
import JForce.JForce.Repository.StaffInventoryAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing StaffInventoryAssignment operations.
 * Provides methods for creating, retrieving, and deleting assignments.
 */
@Service
public class StaffInventoryAssignmentService {

    private final StaffInventoryAssignmentRepository assignmentRepository;

    @Autowired
    public StaffInventoryAssignmentService(StaffInventoryAssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    /**
     * Retrieves all staff-inventory assignments from the database.
     *
     * @return a list of all staff-inventory assignments
     */
    public List<StaffInventoryAssignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    /**
     * Retrieves a specific assignment by its ID.
     *
     * @param id the assignment ID
     * @return an optional containing the assignment if found
     */
    public Optional<StaffInventoryAssignment> getAssignmentById(Integer id) {
        return assignmentRepository.findById(id);
    }

    /**
     * Saves a new or updated assignment to the database.
     *
     * @param assignment the assignment to be saved
     * @return the saved assignment
     */
    public StaffInventoryAssignment saveAssignment(StaffInventoryAssignment assignment) {
        return assignmentRepository.save(assignment);
    }

    /**
     * Deletes a specific assignment by its ID.
     *
     * @param id the ID of the assignment to delete
     */
    public void deleteAssignmentById(Integer id) {
        assignmentRepository.deleteById(id);
    }
}
