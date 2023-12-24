package com.avaj.Expense_Manager.repository;


import com.avaj.Expense_Manager.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByRole(String roleName);
}
