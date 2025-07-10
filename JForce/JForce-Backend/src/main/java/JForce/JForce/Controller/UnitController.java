package JForce.JForce.Controller;

import JForce.JForce.Domain.Unit;
import JForce.JForce.Repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unit")

public class UnitController {

    private final UnitRepository unitRepository;


    public UnitController(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }
    @PostMapping
    public ResponseEntity<Unit> createUnit(@RequestBody Unit unit) {
        return ResponseEntity.ok(unitRepository.save(unit));
    }

    @GetMapping
    public ResponseEntity<List<Unit>> getAllUnits() {
        return ResponseEntity.ok(unitRepository.findAll());
    }
}
