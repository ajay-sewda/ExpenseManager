package com.avaj.Expense_Manager.service;

import com.avaj.Expense_Manager.entity.FinalSplit;

import java.util.List;

public interface FinalSplitService {
    List<FinalSplit> getFinalSplit(Long groupId);
}
