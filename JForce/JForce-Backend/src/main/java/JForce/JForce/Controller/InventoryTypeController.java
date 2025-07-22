package JForce.JForce.Controller;

import JForce.JForce.Domain.Inventory;
import JForce.JForce.Domain.InventoryStatus;
import JForce.JForce.Repository.InventoryRepository;
import JForce.JForce.Repository.InventoryTypeRepository;
import JForce.JForce.Service.DTO.InventoryInfoDTO;
import JForce.JForce.Service.StaffInventoryAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import JForce.JForce.Domain.InventoryType;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventory-types")
@RequiredArgsConstructor
public class InventoryTypeController {

    private final InventoryTypeRepository inventoryTypeRepository;
    private final InventoryRepository inventoryRepository;
    private final StaffInventoryAssignmentService staffInventoryAssignmentService;
    @GetMapping
    public ResponseEntity<List<String>> getAllInventoryTypes() {
        List<String> types = inventoryTypeRepository.findAll()
                .stream()
                .map(InventoryType::getTypeName)
                .toList();
        return ResponseEntity.ok(types);
    }


    @PostMapping
    public ResponseEntity<InventoryType> addInventoryType(@RequestBody InventoryType newType) {
        if (newType.getTypeName() == null || newType.getTypeName().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        InventoryType saved = inventoryTypeRepository.save(newType);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> getAllTypeNames() {
        List<String> typeNames = inventoryTypeRepository.findAll()
                .stream()
                .map(InventoryType::getTypeName)
                .toList();
        return ResponseEntity.ok(typeNames);
    }


    @GetMapping("/available")
    public ResponseEntity<List<String>> getTypesWithAtLeastOneFreeInventory() {
        // 1. Müsait (boşta) inventoryleri bul
        List<Inventory> freeInventories = inventoryRepository.findByStatusIn(List.of(
                InventoryStatus.Available,
                InventoryStatus.Warehouse
        ));

        // 2. Bu envanterlerin type_id'lerini al
        Set<Integer> availableTypeIds = freeInventories.stream()
                .map(inv -> inv.getType().getId())
                .collect(Collectors.toSet());

        // 3. Bu ID’lerle type_name’leri al
        List<String> availableTypeNames = inventoryTypeRepository.findAllById(availableTypeIds)
                .stream()
                .map(InventoryType::getTypeName)
                .toList();

        return ResponseEntity.ok(availableTypeNames);
    }

    @GetMapping("/all")
    public ResponseEntity<List<InventoryInfoDTO>> getAllInventoriesWithStaff() {
        List<Inventory> list = inventoryRepository.findAll();

        List<InventoryInfoDTO> result = list.stream().map(inv -> {
            InventoryInfoDTO dto = new InventoryInfoDTO();
            dto.setTypeName(inv.getType().getTypeName());
            dto.setBrand(inv.getBrand());
            dto.setModel(inv.getModel());
            dto.setSerialNumber(inv.getSerialNumber());
            dto.setStatus(inv.getStatus().name());
            if (inv.getAssignedStaff() != null) {
                dto.setAssignedTo(inv.getAssignedStaff().getName() + " " + inv.getAssignedStaff().getSurname());
            }
            return dto;
        }).toList();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/available-or-unassigned")
    public ResponseEntity<List<InventoryType>> getAvailableOrUnassigned() {
        return ResponseEntity.ok(staffInventoryAssignmentService.getAvailableOrUnassignedInventoryTypes());
    }
}

