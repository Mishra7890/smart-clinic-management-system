import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findBySpecialization(String specialization);
    Optional<Doctor> findByEmail(String email); // यह credentials validation के लिए जरूरी है!
}
