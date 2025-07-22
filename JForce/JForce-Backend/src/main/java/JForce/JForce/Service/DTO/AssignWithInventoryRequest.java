package JForce.JForce.Service.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AssignWithInventoryRequest {
    private Long staffId;
    private LocalDate assignDate;
    private InventoryRequestDTO inventory;

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public LocalDate getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(LocalDate assignDate) {
        this.assignDate = assignDate;
    }

    public InventoryRequestDTO getInventory() {
        return inventory;
    }

    public void setInventory(InventoryRequestDTO inventory) {
        this.inventory = inventory;
    }
}


