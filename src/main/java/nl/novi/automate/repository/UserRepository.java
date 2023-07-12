package nl.novi.automate.repository;

import nl.novi.automate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    void deleteByUsername(String username);

   User findByFileName(String fileName);

    User findByCarId(Long carId);

    // deze erbij voor de systeem User
    User findByUsernameIgnoreCase(String username);
}
