package JForce.JForce.Service.DTO;

import java.time.LocalDate;

public class StaffInventoryAssignmentDTO {

    private Long assignmentId;
    private String staffName;
    private String staffSurname;
    private String username;
    private String inventoryType;
    private String brand;
    private String model;
    private String serialNumber;
    private LocalDate assignDate;
    private String assignedBy;
    private LocalDate returnDate;
    private String receivedBy;

    public StaffInventoryAssignmentDTO(Long assignmentId, String staffName, String staffSurname, String username,
                                          String inventoryType, String brand, String model, String serialNumber,
                                          LocalDate assignDate, String assignedBy, LocalDate returnDate, String receivedBy) {
        this.assignmentId = assignmentId;
        this.staffName = staffName;
        this.staffSurname = staffSurname;
        this.username = username;
        this.inventoryType = inventoryType;
        this.brand = brand;
        this.model = model;
        this.serialNumber = serialNumber;
        this.assignDate = assignDate;
        this.assignedBy = assignedBy;
        this.returnDate = returnDate;
        this.receivedBy = receivedBy;
    }


    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffSurname() {
        return staffSurname;
    }

    public void setStaffSurname(String staffSurname) {
        this.staffSurname = staffSurname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public LocalDate getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(LocalDate assignDate) {
        this.assignDate = assignDate;
    }

    public String getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }
}
