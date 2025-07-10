package JForce.Controller_Auth;

import JForce.JForce.Service.StaffService;
import JForce.JForce.Service.DTO.StaffLoginResponseDTO;
import JForce.Security.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller responsible for handling authentication-related endpoints.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final StaffService staffService;
    private final JWTUtil jwtUtil;


    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String username) {
        staffService.sendPasswordResetEmailIfUserExists(username);
        return ResponseEntity.ok("If the user exists, a reset email has been sent.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String newPassword = body.get("newPassword");

        boolean success = staffService.resetPasswordWithToken(token, newPassword);
        if (success) {
            return ResponseEntity.ok("Password reset successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }
    }

    public AuthController(StaffService staffService, JWTUtil jwtUtil) {
        this.staffService = staffService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Endpoint for user login. Validates credentials and returns JWT token with user info.
     *
     * @param loginData A JSON map containing "username" and "password"
     * @return JWT token and user info if valid, or 401 Unauthorized if invalid
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        StaffLoginResponseDTO user = staffService.validateUserAndReturnDTO(username, password);
        if (user != null) {
            String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "username", user.getUsername(),
                    "role", user.getRole()
            ));
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        }
    }
}
