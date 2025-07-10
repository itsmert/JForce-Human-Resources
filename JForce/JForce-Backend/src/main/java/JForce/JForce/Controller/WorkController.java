package JForce.JForce.Controller;

import JForce.JForce.Domain.Work;
import JForce.JForce.Repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work")
public class WorkController {

    private final WorkRepository workRepository;

    public WorkController(WorkRepository workRepository) {
        this.workRepository = workRepository;
    }

    @PostMapping
    public ResponseEntity<Work> createWork(@RequestBody Work work) {
        return ResponseEntity.ok(workRepository.save(work));
    }

    @GetMapping
    public ResponseEntity<List<Work>> getAllWorks() {
        return ResponseEntity.ok(workRepository.findAll());
    }
}
