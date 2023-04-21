package com.example.les16.repository;

import com.example.les16.model.Role;
import com.example.les16.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    // was Crud en String
    Optional<User> findByUsername(String username);

    void deleteByUsername(String username);

//    List<User> findByRoles(String role);
//   @Query("SELECT u FROM User u WHERE u.fileName = ?1")
   User findByFileName(String fileName);

    User findByCarId(Long carId);
}
