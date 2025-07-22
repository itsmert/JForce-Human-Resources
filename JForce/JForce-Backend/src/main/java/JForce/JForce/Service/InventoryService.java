package JForce.JForce.Service;

import JForce.JForce.Domain.Inventory;
import JForce.JForce.Domain.InventoryStatus;
import JForce.JForce.Domain.InventoryType;
import JForce.JForce.Domain.Staff;
import JForce.JForce.Repository.InventoryRepository;
import JForce.JForce.Repository.InventoryTypeRepository;
import JForce.JForce.Repository.StaffRepository;
import JForce.JForce.Service.DTO.InventoryRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service

public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final StaffRepository staffRepository;
    private final InventoryTypeRepository inventoryTypeRepository; // ← EKLENDİ


    public InventoryService(InventoryRepository inventoryRepository, StaffRepository staffRepository, InventoryTypeRepository inventoryTypeRepository) {
        this.inventoryRepository = inventoryRepository;
        this.staffRepository = staffRepository;
        this.inventoryTypeRepository = inventoryTypeRepository;
    }

    public Inventory saveInventory(InventoryRequestDTO dto) {
        validateInventoryRequest(dto);
        Inventory inventory = mapDtoToEntity(dto, new Inventory());
        return inventoryRepository.save(inventory);
    }

    public Inventory updateInventory(InventoryRequestDTO dto) {
        Inventory inventory = inventoryRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        validateInventoryRequest(dto);
        return inventoryRepository.save(mapDtoToEntity(dto, inventory));
    }



    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    private Inventory mapDtoToEntity(InventoryRequestDTO dto, Inventory inventory) {
        InventoryType inventoryType = inventoryTypeRepository.findById(dto.getTypeId())
                .orElseThrow(() -> new IllegalArgumentException("InventoryType not found"));
        inventory.setEntryDate(dto.getEntryDate());
        inventory.setBrand(dto.getBrand());
        inventory.setModel(dto.getModel());
        inventory.setSerialNumber(dto.getSerialNumber());

        InventoryStatus status = dto.getStatus();
        inventory.setStatus(status);

        if (status == InventoryStatus.On_Staff) {
            Staff staff = staffRepository.findById(dto.getStaffId())
                    .orElseThrow(() -> new RuntimeException("Staff not found"));
            inventory.setAssignedStaff(staff);
        } else {
            inventory.setAssignedStaff(null);
        }

        return inventory;
    }

    private void validateInventoryRequest(InventoryRequestDTO dto) {
        if (dto.getEntryDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Entry date cannot be in the past.");
        }

        if (dto.getStatus() == null ||
                (!dto.getStatus().equals("AVAILABLE") &&
                        !dto.getStatus().equals("ON_STAFF") &&
                        !dto.getStatus().equals("WAREHOUSE"))) {
            throw new RuntimeException("Invalid status provided.");
        }

        if (dto.getStatus().equals("ON_STAFF") && dto.getStaffId() == null) {
            throw new RuntimeException("Staff must be assigned when status is ON_STAFF.");
        }
    }

    public List<String> getStatusOptions() {
        return Arrays.stream(InventoryStatus.values())
                .map(Enum::name)
                .toList();
    }
}
