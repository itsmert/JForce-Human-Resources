package JForce.JForce.Service;

import JForce.JForce.Domain.*;
import JForce.JForce.Repository.InventoryRepository;
import JForce.JForce.Repository.InventoryTypeRepository;
import JForce.JForce.Repository.StaffInventoryAssignmentRepository;
import JForce.JForce.Repository.StaffRepository;
import JForce.JForce.Service.DTO.AssignWithInventoryRequest;
import JForce.JForce.Service.DTO.StaffInventoryAssignmentDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StaffInventoryAssignmentService {

    private final StaffInventoryAssignmentRepository assignmentRepository;
    private final StaffRepository staffRepository;
    private final InventoryRepository inventoryRepository;
    private final InventoryTypeRepository inventoryTypeRepository;
    private final StaffInventoryAssignmentRepository repository;
    private final EmailService emailService;

    /**
     * Assign an inventory to a staff member.
     */
    public StaffInventoryAssignment assignInventory(Long staffId, Integer inventoryId, Long assignedById, LocalDate assignDate) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found with ID: " + staffId));

        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found with ID: " + inventoryId));

        Staff assignedBy = assignedById != null ?
                staffRepository.findById(assignedById).orElse(null) : null;

        StaffInventoryAssignment assignment = new StaffInventoryAssignment();
        assignment.setStaffId(staff);
        assignment.setInventoryId(inventory);
        assignment.setAssignDate(assignDate != null ? assignDate : LocalDate.now());
        assignment.setAssignedBy(assignedBy);

        return assignmentRepository.save(assignment);
    }

    /**
     * Update return date and receiver for an assigned inventory.
     */
    public void updateReturnInfo(Long assignmentId, Long receivedById, LocalDate returnDate) {
        StaffInventoryAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found with ID: " + assignmentId));

        Staff receiver = receivedById != null ?
                staffRepository.findById(receivedById).orElse(null) : null;

        assignment.setReturnDate(returnDate != null ? returnDate : LocalDate.now());
        assignment.setReceivedBy(receiver);

        assignmentRepository.save(assignment);
    }

    @Transactional  // Bu annotation çok önemli!
    public List<StaffInventoryAssignmentDTO> getAllAsDTO() {
        List<StaffInventoryAssignment> assignments = repository.findAllWithStaff(); // yukarıdaki join fetch methodu
        return assignments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<StaffInventoryAssignmentDTO> filterAssignments(String name, String surname, String registrationNumber) {
        return assignmentRepository.findAll().stream()
                .filter(a -> {
                    Staff staff = a.getStaffId();
                    boolean match = true;
                    if (name != null) match &= staff.getName().toLowerCase().contains(name.toLowerCase());
                    if (surname != null) match &= staff.getSurname().toLowerCase().contains(surname.toLowerCase());
                    if (registrationNumber != null) match &= staff.getRegistrationNumber().equals(registrationNumber);
                    return match;
                })
                .map(this::convertToDTO)
                .toList();
    }

    public List<StaffInventoryAssignmentDTO> getAssignmentsByStaffId(Long staffId) {
        return assignmentRepository.findByStaffId_Id(staffId)
                .stream().map(this::convertToDTO).toList();
    }

    public List<Inventory> getUnassignedInventory() {
        List<Integer> assignedInventoryIds = assignmentRepository.findAll().stream()
                .filter(a -> a.getReturnDate() == null)
                .map(a -> a.getInventoryId().getId())
                .toList();

        return inventoryRepository.findAll().stream()
                .filter(i -> !assignedInventoryIds.contains(i.getId()))
                .toList();
    }

    private StaffInventoryAssignmentDTO convertToDTO(StaffInventoryAssignment assignment) {
        Staff staff = assignment.getStaffId();
        String assignedBy = assignment.getAssignedBy() != null
                ? assignment.getAssignedBy().getName() + " " + assignment.getAssignedBy().getSurname()
                : "-";

        String receivedBy = assignment.getReceivedBy() != null
                ? assignment.getReceivedBy().getName() + " " + assignment.getReceivedBy().getSurname()
                : "-";

        return new StaffInventoryAssignmentDTO(
                assignment.getId().longValue(),
                staff.getName(),
                staff.getSurname(),
                staff.getUsername(),
                assignment.getInventoryId().getType().getTypeName(), // 💡 Tip nesnesinden tip ismi alındı
                assignment.getInventoryId().getBrand(),
                assignment.getInventoryId().getModel(),
                assignment.getInventoryId().getSerialNumber(),
                assignment.getAssignDate(),
                assignedBy,
                assignment.getReturnDate(),
                receivedBy
        );
    }

    /**
     * Envanteri oluştur ve otomatik zimmetle
     */
    public void createInventoryAndAssign(AssignWithInventoryRequest request) {
        // typeId'yi DTO'dan al
        Integer typeId = request.getInventory().getTypeId();
        if (typeId == null) {
            throw new IllegalArgumentException("Inventory type ID cannot be null");
        }

        // typeId'ye göre InventoryType bul
        InventoryType inventoryType = inventoryTypeRepository.findById(typeId)
                .orElseThrow(() -> new IllegalArgumentException("InventoryType not found"));

        Inventory inv = new Inventory();
        inv.setType(inventoryType); // Burada null olmamalı!
        inv.setBrand(request.getInventory().getBrand());
        inv.setModel(request.getInventory().getModel());
        inv.setSerialNumber(request.getInventory().getSerialNumber());
        inv.setEntryDate(request.getInventory().getEntryDate());
        inv.setStatus(InventoryStatus.On_Staff);

        Staff staff = staffRepository.findById(request.getStaffId())
                .orElseThrow(() -> new IllegalArgumentException("Staff not found"));

        inv.setAssignedStaff(staff);

        Inventory saved = inventoryRepository.save(inv);

        StaffInventoryAssignment assignment = new StaffInventoryAssignment();
        assignment.setInventoryId(saved);
        assignment.setStaffId(staff);
        assignment.setAssignDate(request.getAssignDate());

        assignmentRepository.save(assignment);
        emailService.sendMail(
                staff.getMail(),
                "New Inventory Assigned",
                "Dear " + staff.getName() + ",\n\n" +
                        "A new inventory has been assigned to you:\n\n" +
                        "Type: " + inventoryType.getTypeName() + "\n" +
                        "Brand: " + request.getInventory().getBrand() + "\n" +
                        "Model: " + request.getInventory().getModel() + "\n" +
                        "Serial Number: " + request.getInventory().getSerialNumber() + "\n" +
                        "Assign Date: " + request.getAssignDate() + "\n\n" +
                        "Please keep this item safe.\n\n" +
                        "Best regards,\nInventory Team"
        );

    }

    public List<InventoryType> getAvailableInventoryTypes() {
        List<Inventory> availableInventories = inventoryRepository.findAll().stream()
                .filter(i -> i.getStatus() == InventoryStatus.Available || i.getStatus() == InventoryStatus.Warehouse)
                .collect(Collectors.toList());

        return availableInventories.stream()
                .map(Inventory::getType)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<InventoryType> getAvailableOrUnassignedInventoryTypes() {
        return inventoryTypeRepository.findAvailableOrUnassignedInventoryTypes();
    }

    public void returnAssignment(Long assignmentId, LocalDate returnDate) {
        StaffInventoryAssignment assignment = repository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        assignment.setReturnDate(returnDate);

        // 📌 Envanteri AVAILABLE yap
        Inventory inventory = assignment.getInventoryId();
        inventory.setStatus(InventoryStatus.Available);

        inventoryRepository.save(inventory);
        repository.save(assignment);

        Staff staff = assignment.getStaffId();
        emailService.sendMail(
                staff.getMail(),
                "Inventory Returned",
                "Dear " + staff.getName() + ",\n\n" +
                        "You have successfully returned the following inventory:\n\n" +
                        "Type: " + inventory.getType().getTypeName() + "\n" +
                        "Serial Number: " + inventory.getSerialNumber() + "\n" +
                        "Return Date: " + returnDate + "\n\n" +
                        "Thank you for your cooperation.\n\n" +
                        "Best regards,\nInventory Team"
        );
    }

}
