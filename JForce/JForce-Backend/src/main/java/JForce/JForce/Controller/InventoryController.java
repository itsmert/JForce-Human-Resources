package JForce.JForce.Controller;

import JForce.JForce.Domain.Inventory;
import JForce.JForce.Service.DTO.InventoryRequestDTO;
import JForce.JForce.Service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/filter")
    public ResponseEntity<List<Inventory>> filterInventory(@RequestParam(required = false) String type) {
        return ResponseEntity.ok(inventoryService.filterByType(type));
    }

    @PostMapping("/save")
    public ResponseEntity<Inventory> saveInventory(@RequestBody @Valid InventoryRequestDTO dto) {
        return ResponseEntity.ok(inventoryService.saveInventory(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Inventory> updateInventory(@RequestBody @Valid InventoryRequestDTO dto) {
        return ResponseEntity.ok(inventoryService.updateInventory(dto));
    }

    @GetMapping("/status-options")
    public ResponseEntity<List<String>> getStatusOptions() {
        return ResponseEntity.ok(inventoryService.getStatusOptions());
    }
}
