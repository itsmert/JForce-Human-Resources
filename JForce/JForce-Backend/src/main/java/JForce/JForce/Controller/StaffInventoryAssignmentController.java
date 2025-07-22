package JForce.JForce.Controller;

import JForce.JForce.Domain.Inventory;
import JForce.JForce.Domain.InventoryStatus;
import JForce.JForce.Domain.InventoryType;
import JForce.JForce.Domain.StaffInventoryAssignment;
import JForce.JForce.Repository.InventoryRepository;
import JForce.JForce.Service.DTO.AssignWithInventoryRequest;
import JForce.JForce.Service.DTO.StaffInventoryAssignmentDTO;
import JForce.JForce.Service.StaffInventoryAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/staff-inventory")
@RequiredArgsConstructor
public class StaffInventoryAssignmentController {

    private final StaffInventoryAssignmentService assignmentService;
    private final InventoryRepository inventoryRepository;
    /**
     * Zimmet ataması yapar.
     */
    @PostMapping("/assign")
    @PreAuthorize("hasAuthority('Human_Resources')")
    public ResponseEntity<StaffInventoryAssignment> assignInventory(
            @RequestParam Long staffId,
            @RequestParam Integer inventoryId,
            @RequestParam(required = false) Long assignedById,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate assignDate) {

        StaffInventoryAssignment result = assignmentService.assignInventory(staffId, inventoryId, assignedById, assignDate);
        return ResponseEntity.ok(result);
    }

    /**
     * Zimmet iadesini kaydeder.
     */
    @PutMapping("/return")
    @PreAuthorize("hasAuthority('Human_Resources')")
    public ResponseEntity<Void> updateReturn(
            @RequestParam Long assignmentId,
            @RequestParam(required = false) Long receivedById,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate) {

        assignmentService.updateReturnInfo(assignmentId, receivedById, returnDate);
        return ResponseEntity.ok().build();
    }

    /**
     * Tüm zimmet bilgilerini DTO formatında getirir.
     */
    @GetMapping("/dto/all")
    @PreAuthorize("hasAuthority('Human_Resources')")
    public ResponseEntity<List<StaffInventoryAssignmentDTO>> getAllAssignmentsAsDTO() {
        return ResponseEntity.ok(assignmentService.getAllAsDTO());
    }
    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('Human_Resources')")
    public ResponseEntity<List<StaffInventoryAssignmentDTO>> filterAssignments(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) String registrationNumber) {

        return ResponseEntity.ok(assignmentService.filterAssignments(name, surname, registrationNumber));
    }

    @GetMapping("/staff/{staffId}")
    @PreAuthorize("hasAuthority('Human_Resources')")
    public ResponseEntity<List<StaffInventoryAssignmentDTO>> getByStaffId(@PathVariable Long staffId) {
        return ResponseEntity.ok(assignmentService.getAssignmentsByStaffId(staffId));
    }

    @GetMapping("/available-inventory")
    @PreAuthorize("hasAuthority('Human_Resources')")
    public ResponseEntity<List<Inventory>> getAvailableInventory() {
        return ResponseEntity.ok(assignmentService.getUnassignedInventory());
    }




    @GetMapping("/available-inventory-types")
    @PreAuthorize("hasAuthority('Human_Resources')")
    public ResponseEntity<List<InventoryType>> getAvailableInventoryTypes() {
        return ResponseEntity.ok(assignmentService.getAvailableInventoryTypes());
    }

    @PostMapping("/assign-with-inventory")
    @PreAuthorize("hasAuthority('Human_Resources')")
    public ResponseEntity<Void> assignWithNewInventory(@RequestBody AssignWithInventoryRequest request) {
        assignmentService.createInventoryAndAssign(request);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/return")
    public ResponseEntity<String> returnAssignment(
            @RequestParam Long assignmentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate) {
        assignmentService.returnAssignment(assignmentId, returnDate);
        return ResponseEntity.ok("Zimmet başarıyla iade edildi.");
    }
}
