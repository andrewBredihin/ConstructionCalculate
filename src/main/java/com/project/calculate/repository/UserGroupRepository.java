package com.project.calculate.repository;

import com.project.calculate.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
}
