    package JForce.JForce.Controller;

    import JForce.JForce.Domain.Staff;
    import JForce.JForce.Repository.StaffRepository;
    import JForce.JForce.Service.DTO.StaffFilterResponseDTO;
    import JForce.JForce.Service.DTO.StaffRequestDTO;
    import JForce.JForce.Service.StaffService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

    import java.util.List;
    import java.util.Optional;

    @CrossOrigin(origins = "http://localhost:3000")
    @RestController
    @RequestMapping("/api/staff")
    @RequiredArgsConstructor
    public class StaffController {

        private final StaffService staffService;
        private final StaffRepository staffRepository;

        /**
         * Returns all staff members.
         * Only accessible to Human_Resources role.
         */
        @GetMapping("/all")
        public ResponseEntity<List<Staff>> getAllStaff() {
            List<Staff> staffList = staffRepository.findAllWithDetails();
            return ResponseEntity.ok(staffList);
        }

        /**
         * Registers a new staff member and sends password setup email.
         */
        @PostMapping("/register")
        @PreAuthorize("hasAuthority('Human_Resources')")
        public ResponseEntity<Staff> registerNewStaff(@RequestBody StaffRequestDTO staffDTO) {
            Staff savedStaff = staffService.registerNewStaff(staffDTO);
            return ResponseEntity.ok(savedStaff);
        }

        /**
         * Returns staff by ID.
         * Example: /api/staff/5
         */
        @GetMapping("/{id}")
        @PreAuthorize("hasAuthority('Human_Resources')")
        public ResponseEntity<Optional<Staff>> getStaffById(@PathVariable Long id) {
            return ResponseEntity.ok(staffService.getStaffById(id));
        }

        /**
         * Filters staff by multiple optional fields.
         */
        @GetMapping("/filter")
        @PreAuthorize("hasAuthority('Human_Resources')")
        public ResponseEntity<List<StaffFilterResponseDTO>> filterStaff(
                @RequestParam(required = false) String name,
                @RequestParam(required = false) String surname,
                @RequestParam(required = false) String turkish_identity,
                @RequestParam(required = false) Integer unitId,
                @RequestParam(required = false) String email
        ) {
            return ResponseEntity.ok(staffService.filterStaff(name, surname, turkish_identity, unitId, email));
        }

        /**
         * Saves a new staff member.
         */
        @PostMapping("/save")
        public ResponseEntity<Staff> saveStaff(@RequestBody StaffRequestDTO staffDTO) {
            return ResponseEntity.ok(staffService.saveStaff(staffDTO));
        }

        /**
         * Updates an existing staff member.
         */
        @PutMapping("/{id}")
        @PreAuthorize("hasAuthority('Human_Resources')")
        public ResponseEntity<Staff> updateStaff(
                @PathVariable Long id,
                @RequestBody StaffRequestDTO staffDTO
        ) {
            System.out.println("GÜNCELLEME İSTEĞİ ALINDI → ID: " + id);
            staffDTO.setId(id);
            return ResponseEntity.ok(staffService.updateStaff(staffDTO));
        }

        /**
         * Deletes a staff member by ID.
         */
        @DeleteMapping("/{id}")
        @PreAuthorize("hasAuthority('Human_Resources')")
        public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
            staffService.deleteStaff(id);
            return ResponseEntity.ok().build();
        }
    }
