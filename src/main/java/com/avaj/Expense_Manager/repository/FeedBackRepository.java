package com.avaj.Expense_Manager.repository;

import com.avaj.Expense_Manager.entity.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack,Long> {

}
