package JForce.JForce.Controller;

import JForce.JForce.Domain.StaffEntryExitStatus;
import JForce.JForce.Service.StaffEntryExitStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/staff-entry-exit")
@RequiredArgsConstructor
public class StaffEntryExitStatusController {

    private final StaffEntryExitStatusService service;

    @GetMapping("/{staffId}")
    @PreAuthorize("hasAuthority('Human_Resources')")
    public ResponseEntity<?> getStatus(@PathVariable Long staffId) {
        return service.getDTOByStaffId(staffId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('Human_Resources')")
    public ResponseEntity<?> getAllStatuses() {
        return ResponseEntity.ok(service.getAllAsDTO());
    }

    @PreAuthorize("hasAuthority('Human_Resources')")
    @PostMapping("/{staffId}")
    public ResponseEntity<?> saveOrUpdate(@PathVariable Long staffId, @RequestBody StaffEntryExitStatus status) {
        StaffEntryExitStatus saved = service.saveOrUpdate(staffId, status);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/dto/all")
    @PreAuthorize("hasAuthority('Human_Resources')")
    public ResponseEntity<?> getAllAsDTO() {
        return ResponseEntity.ok(service.getAllAsDTO());
    }


    @GetMapping("/export/csv")
    @PreAuthorize("hasAuthority('Human_Resources')")
    public ResponseEntity<byte[]> exportEntryExitCsv() {
        String csv = service.generateCsvOfAllEntryExit();
        byte[] csvBytes = csv.getBytes(StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=entry_exit_report.csv");
        headers.setContentType(MediaType.TEXT_PLAIN);

        return ResponseEntity.ok()
                .headers(headers)
                .body(csvBytes);
    }



}
