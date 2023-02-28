package com.project.calculate.repository;

import com.project.calculate.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {

    @Transactional
    UserStatus findById(Integer id);
}
