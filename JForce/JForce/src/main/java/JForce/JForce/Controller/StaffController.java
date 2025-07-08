package JForce.JForce.Controller;

import JForce.JForce.Domain.Staff;
import JForce.JForce.Service.DTO.StaffFilterResponseDTO;
import JForce.JForce.Service.DTO.StaffRequestDTO;
import JForce.JForce.Service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    /**
     * Lists all staff with optional filtering by name, surname, turkish_identity and unit.
     * Accessible only to Human_Resources role.
     */
    @PreAuthorize("hasAuthority('Human_Resources')")
    @GetMapping("/filter")
    public ResponseEntity<List<StaffFilterResponseDTO>> filterStaff(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) String turkish_identity,
            @RequestParam(required = false) Integer unitId
    ) {
        return ResponseEntity.ok(staffService.filterStaff(name, surname, turkish_identity, unitId));
    }

    /**
     * Returns staff information by ID.
     * Accessible only to Human_Resources role.
     */
    @PreAuthorize("hasAuthority('Human_Resources')")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Staff>> getStaffById(@PathVariable Long id) {
        return ResponseEntity.ok(staffService.getStaffById(id));
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('Human_Resources')")
    public ResponseEntity<Staff> saveStaff(@RequestBody StaffRequestDTO staffDTO) {
        return ResponseEntity.ok(staffService.saveStaff(staffDTO));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('Human_Resources')")
    public ResponseEntity<Staff> updateStaff(@RequestBody StaffRequestDTO staffDTO) {
        return ResponseEntity.ok(staffService.updateStaff(staffDTO));
    }

    /**
     * Deletes a staff by ID.
     * Accessible only to Human_Resources role.
     */
    @PreAuthorize("hasAuthority('Human_Resources')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return ResponseEntity.ok().build();
    }
}
