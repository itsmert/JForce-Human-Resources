package JForce.JForce.Service;

import JForce.JForce.Domain.Staff;
import JForce.JForce.Repository.StaffRepository;
import JForce.JForce.Service.DTO.StaffLoginResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service layer for handling Staff authentication and login operations.
 */
@Service
public class StaffService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final Logger logger = LoggerFactory.getLogger(StaffService.class);

    @Autowired
    private StaffRepository staffRepository;

    /**
     * Validates staff credentials and returns the Staff object if valid.
     *
     * @param username Staff username
     * @param password Plain password
     * @return Staff object if credentials are valid otherwise null
     */

    public StaffLoginResponseDTO validateUserAndReturnDTO(String username, String password) {
        Staff staff = staffRepository.findByUsername(username);
        if (staff != null && passwordEncoder.matches(password, staff.getPassword())) {
            return staffRepository.findLoginDTOByUserName(username);
        }
        return null;
    }

}