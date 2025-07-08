package JForce.JForce.Controller;

import JForce.JForce.Domain.Unit;
import JForce.JForce.Repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unit")
@RequiredArgsConstructor
public class UnitController {

    private final UnitRepository unitRepository;

    /**
     * Returns all available units.
     * Accessible only to Human_Resources role.
     */
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('Human_Resources')")
    public ResponseEntity<List<Unit>> getAllUnits() {
        return ResponseEntity.ok(unitRepository.findAll());
    }
}
