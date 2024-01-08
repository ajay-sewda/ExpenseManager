package com.avaj.Expense_Manager.service;


import com.avaj.Expense_Manager.entity.FeedBack;

import java.util.List;

public interface FeedBackService{
    List<FeedBack>  getFeedBack();
    void save(FeedBack feedBack);
}
