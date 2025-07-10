package JForce.JForce.Service;

import JForce.JForce.Domain.Staff;
import JForce.JForce.Domain.UserRole;
import JForce.JForce.Repository.StaffRepository;
import JForce.JForce.Repository.UnitRepository;
import JForce.JForce.Repository.WorkRepository;
import JForce.JForce.Service.DTO.StaffFilterResponseDTO;
import JForce.JForce.Service.DTO.StaffLoginResponseDTO;
import JForce.JForce.Service.DTO.StaffRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing Staff-related operations.
 */
@Service

public class StaffService {

    private final StaffRepository staffRepository;
    private final UnitRepository unitRepository;
    private final WorkRepository workRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final EmailService emailService;
    private final PasswordResetTokenService passwordResetTokenService;

    public StaffService(StaffRepository staffRepository, UnitRepository unitRepository, WorkRepository workRepository, BCryptPasswordEncoder passwordEncoder, EmailService emailService) {
        this.staffRepository = staffRepository;
        this.unitRepository = unitRepository;
        this.workRepository = workRepository;
        this.passwordResetTokenService = new PasswordResetTokenService();
        this.emailService = emailService;

    }

    /**
     * Saves a new staff member using the provided DTO.
     *
     * @param dto the staff data transfer object
     * @return the saved staff entity
     */
    public Staff saveStaff(StaffRequestDTO dto) {
        validateRequiredFields(dto);
        Staff staff = mapDtoToEntity(dto, new Staff());
        return staffRepository.save(staff);
    }

    /**
     * Updates an existing staff member using the provided DTO.
     *
     * @param dto the staff data transfer object
     * @return the updated staff entity
     */
    public Staff updateStaff(StaffRequestDTO dto) {
        if (dto.getId() == null) {
            throw new IllegalArgumentException("Staff ID must not be null for update operation.");
        }
        Staff staff = staffRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Staff not found with ID: " + dto.getId()));
        validateRequiredFields(dto);
        staff = mapDtoToEntity(dto, staff);
        return staffRepository.save(staff);
    }

    /**
     * Validates required foreign key fields before saving.
     */
    private void validateRequiredFields(StaffRequestDTO dto) {
        if (dto.getUnit_id() == null) {
            throw new IllegalArgumentException("Unit ID must not be null.");
        }
        if (dto.getWork_id() == null) {
            throw new IllegalArgumentException("Work ID must not be null.");
        }
        if (dto.getRole() == null || dto.getRole().isEmpty()) {
            throw new IllegalArgumentException("Role must not be null or empty.");
        }
    }

    /**
     * Maps a StaffRequestDTO to a Staff entity.
     *
     * @param dto   the data transfer object
     * @param staff the staff entity to be filled
     * @return the filled staff entity
     */
    private Staff mapDtoToEntity(StaffRequestDTO dto, Staff staff) {
        staff.setUsername(dto.getUsername());
        staff.setName(dto.getName());
        staff.setSurname(dto.getSurname());
        staff.setSex(dto.getSex());
        staff.setDateOfBirth(dto.getDateOfBirth());
        staff.setMaritalStatus(dto.getMaritalStatus());
        staff.setTurkishIdentity(dto.getTurkishIdentity());
        staff.setRegistrationNumber(dto.getRegistrationNumber());
        staff.setGraduationStatus(dto.getGraduationStatus());
        staff.setUnit(unitRepository.findById(dto.getUnit_id()).orElseThrow(() ->
                new RuntimeException("Unit not found with ID: " + dto.getUnit_id())));
        staff.setWork(workRepository.findById(dto.getWork_id()).orElseThrow(() ->
                new RuntimeException("Work not found with ID: " + dto.getWork_id())));
        staff.setWorkingStatus(dto.getWorkingStatus());
        staff.setPhoto(dto.getPhoto());
        staff.setPassword(passwordEncoder.encode(dto.getPassword()));
        staff.setRole(UserRole.valueOf(dto.getRole()));
        staff.setMail(dto.getEmail());
        return staff;
    }

    /**
     * Validates staff credentials and returns login DTO if valid.
     *
     * @param username the staff username
     * @param password the plain text password
     * @return the login response DTO or null if invalid
     */
    public StaffLoginResponseDTO validateUserAndReturnDTO(String username, String password) {
        Staff staff = staffRepository.findByUsername(username);
        if (staff != null && passwordEncoder.matches(password, staff.getPassword())) {
            return staffRepository.findLoginDTOByUserName(username);
        }
        return null;
    }

    /**
     * Retrieves a staff member by ID.
     *
     * @param id the staff ID
     * @return optional containing staff if found
     */
    public Optional<Staff> getStaffById(Long id) {
        return staffRepository.findById(id);
    }

    /**
     * Saves or updates a staff entity directly.
     *
     * @param staff the staff entity
     * @return the saved entity
     */
    public Staff saveOrUpdateStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    /**
     * Deletes a staff member by ID.
     *
     * @param id the ID of the staff to delete
     */
    public void deleteStaff(Long id) {
        staffRepository.deleteById(id);
    }

    /**
     * Filters staff based on provided parameters.
     *
     * @param name            the name filter (optional)
     * @param surname         the surname filter (optional)
     * @param turkishIdentity the Turkish Identity Filter (optional)
     * @param unitId          the unit ID filter (optional)
     * @param email
     * @return list of matched staff members
     */
    public List<StaffFilterResponseDTO> filterStaff(String name, String surname, String turkishIdentity, Integer unitId, String email) {
        List<Staff> staffList = staffRepository.filterStaff(name, surname, turkishIdentity, unitId, email);

        return staffList.stream()
                .map(staff -> new StaffFilterResponseDTO(
                        staff.getId(),
                        staff.getName(),
                        staff.getSurname(),
                        staff.getRegistrationNumber(),
                        staff.getUnit().getName(),
                        staff.getWork().getName(),
                        staff.getMail()
                ))
                .toList();
    }


    /**
     * Sends a password reset email to the staff with the given username.
     *
     * @param username the username of the staff
     */
    public void sendPasswordResetEmailIfUserExists(String username) {
        Staff staff = staffRepository.findByUsername(username);

        if (staff == null) {
            System.out.println("⚠️ No staff found with username: " + username);
            return;
        }

        String token = passwordResetTokenService.createPasswordResetToken(username);
        String resetLink = "http://localhost:3000/reset-password?token=" + token;

        try {
            emailService.PasswordResetMail(
                    staff.getMail(),
                    staff.getUsername(),
                    resetLink
            );
        } catch (Exception e) {
            throw new RuntimeException(" Failed to send reset email to " + staff.getUsername(), e);
        }
    }

    public boolean resetPasswordWithToken(String token, String newPassword) {
        String username = passwordResetTokenService.getUsernameFromToken(token);
        if (username == null) {
            return false;
        }

        Staff staff = staffRepository.findByUsername(username);
        if (staff == null) {
            return false;
        }

        staff.setPassword(passwordEncoder.encode(newPassword));
        staffRepository.save(staff);
        passwordResetTokenService.invalidateToken(token);
        return true;
    }
}