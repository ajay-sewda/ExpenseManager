package com.avaj.Expense_Manager.repository;

import com.avaj.Expense_Manager.entity.FinalSplit;
import com.avaj.Expense_Manager.entity.Group;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinalSplitRepository extends JpaRepository<FinalSplit,Long> {
 List<FinalSplit> findByFinalSplitGrp(Group finalSplitGrp);
// FinalSplit findByFinalPayByAndFinalPayToAndFinalSplitGrpId(Long debtor,Long creditor,Long groupId);
 @Transactional
 void deleteAllByFinalSplitGrp(Group finalSplitGrp);
}
