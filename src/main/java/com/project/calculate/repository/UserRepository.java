package com.project.calculate.repository;

import com.project.calculate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {


    User findByLogin(String login);

    User findById(int id);

    User findByEmail(String email);

    @Query(value = "SELECT MAX(`id`) from `User`", nativeQuery = true)
    Integer getMaxId();
}
