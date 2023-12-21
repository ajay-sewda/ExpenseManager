package com.avaj.Expense_Manager.repository;

import com.avaj.Expense_Manager.entity.FinalSplit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinalSplitRepository extends JpaRepository<FinalSplit,Long> {

}
