package com.veiter.animalcare.repositories;

import com.veiter.animalcare.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, String> {

    User findByEmail(String email);
    Collection<User> findAllByRole(String role);
}
