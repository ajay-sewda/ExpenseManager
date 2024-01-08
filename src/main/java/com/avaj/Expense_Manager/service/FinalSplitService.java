package com.avaj.Expense_Manager.service;

import com.avaj.Expense_Manager.entity.FinalSplit;

import java.util.List;

public interface FinalSplitService {
    void createFinalSplits(Long groupId);
    List<FinalSplit> getFinalSplit(Long groupId);
    void updateFinalSplit(Long groupId);
    void deleteFinalSplit(Long finalSplitId);
}
