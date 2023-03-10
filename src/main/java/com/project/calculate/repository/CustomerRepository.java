package com.project.calculate.repository;

import com.project.calculate.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query (value = "Select max(c.id) from customers c", nativeQuery = true)
    Long getMaxId();
}
