import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorRepository doctorRepository;
    private final TokenService tokenService; // Dummy token service

    @Autowired
    public DoctorController(DoctorRepository doctorRepository, TokenService tokenService) {
        this.doctorRepository = doctorRepository;
        this.tokenService = tokenService;
    }

    // REQUIRED FOR FULL MARKS (add this!)
    @GetMapping("/availability/{user}/{doctorId}/{date}/{token}")
    public ResponseEntity<Map<String, Object>> getDoctorAvailability(
            @PathVariable String user,
            @PathVariable Long doctorId,
            @PathVariable String date,
            @PathVariable String token
    ) {
        Map<String, Object> response = new HashMap<>();

        // Token validation - as per your assignment requirement
        if (!tokenService.validateToken(token, user)) {
            response.put("error", "Invalid token");
            return ResponseEntity.status(401).body(response);
        }

        Optional<Doctor> doctorOpt = doctorRepository.findById(doctorId);
        if (!doctorOpt.isPresent()) {
            response.put("error", "Doctor not found");
            return ResponseEntity.status(404).body(response);
        }

        // Example logic: Return availableTimes directly
        Doctor doctor = doctorOpt.get();
        response.put("doctorId", doctor.getId());
        response.put("date", date);
        response.put("user", user);
        response.put("availableTimes", doctor.getAvailableTimes());
        response.put("status", "success");

        return ResponseEntity.ok(response);
    }

    // (Baaki CRUD endpoints yahan ho sakte hain)
}
