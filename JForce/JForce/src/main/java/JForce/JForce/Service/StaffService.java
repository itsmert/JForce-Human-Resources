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
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;
    private final UnitRepository unitRepository;
    private final WorkRepository workRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Saves a new staff member using the provided DTO.
     *
     * @param dto the staff data transfer object
     * @return the saved staff entity
     */
    public Staff saveStaff(StaffRequestDTO dto) {
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
        Staff staff = staffRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Staff not found with ID: " + dto.getId()));
        staff = mapDtoToEntity(dto, staff);
        return staffRepository.save(staff);
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
        staff.setUnit(unitRepository.findById(dto.getUnitId()).orElseThrow(() ->
                new RuntimeException("Unit not found with ID: " + dto.getUnitId())));
        staff.setWork(workRepository.findById(dto.getWorkId()).orElseThrow(() ->
                new RuntimeException("Work not found with ID: " + dto.getWorkId())));
        staff.setWorkingStatus(dto.getWorkingStatus());
        staff.setPhoto(dto.getPhoto());
        staff.setPassword(passwordEncoder.encode(dto.getPassword()));
        staff.setRole(UserRole.valueOf(dto.getRole().toUpperCase()));
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
     * @param name             the name filter (optional)
     * @param surname          the surname filter (optional)
     * @param turkishIdentity  the Turkish Identity Filter (optional)
     * @param unitId           the unit ID filter (optional)
     * @return list of matched staff members
     */
    public List<StaffFilterResponseDTO> filterStaff(String name, String surname, String turkishIdentity, Integer unitId) {
        List<Staff> staffList = staffRepository.filterStaff(name, surname, turkishIdentity, unitId);

        return staffList.stream()
                .map(   staff -> new StaffFilterResponseDTO(
                        staff.getId(),
                        staff.getName(),
                        staff.getSurname(),
                        staff.getRegistrationNumber(),
                        staff.getUnit().getName(),
                        staff.getWork().getName()
                ))
                .toList();
    }

}
