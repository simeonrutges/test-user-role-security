package com.example.les16.repository;

import com.example.les16.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<User, String> {
// was CRUD!
}
