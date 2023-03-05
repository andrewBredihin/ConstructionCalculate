package com.project.calculate.repository;

import com.project.calculate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


    User findByLogin(String login);
}
