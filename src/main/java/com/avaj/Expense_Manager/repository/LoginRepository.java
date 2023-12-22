package com.avaj.Expense_Manager.repository;

import com.avaj.Expense_Manager.entity.LoginDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<LoginDetail,String> {
}
