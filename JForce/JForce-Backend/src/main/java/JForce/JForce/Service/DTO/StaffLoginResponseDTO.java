package JForce.JForce.Service.DTO;

/**
 * DTO for transferring staff login response data.
 */
public class StaffLoginResponseDTO {

    private String username;
    private String firstName;
    private String lastName;
    private String role;

    /**
     * Constructor to initialize StaffLoginResponseDTO.
     *
     * @param username   Staff's username
     * @param firstName  Staff's first name
     * @param lastName   Staff's last name
     * @param role       Staff's role (as string, usually from enum)
     */
    public StaffLoginResponseDTO(String username, String firstName, String lastName, Enum<?> role) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role.name();
    }

    // Getters

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public String getRole() {
        return role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
