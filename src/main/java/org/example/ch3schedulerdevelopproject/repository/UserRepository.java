package org.example.ch3schedulerdevelopproject.repository;

import org.example.ch3schedulerdevelopproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
