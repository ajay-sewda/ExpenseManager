package com.avaj.Expense_Manager.service;

import com.avaj.Expense_Manager.entity.Expense;
import com.avaj.Expense_Manager.entity.FinalSplit;
import com.avaj.Expense_Manager.entity.User;

import java.util.List;

public interface FinalSplitService {
//    void createFinalSplits(Long groupId);
    FinalSplit getFinalSplitById(Long finalSplitId);
    List<FinalSplit> getFinalSplit(Long groupId);
    void updateFinalSplit(Long groupId);
    void settleUp(FinalSplit temp, User user,Long groupId);
    void createFinalSplits(Long groupId);
    void deleteFinalSplit(Long finalSplitId);
}
