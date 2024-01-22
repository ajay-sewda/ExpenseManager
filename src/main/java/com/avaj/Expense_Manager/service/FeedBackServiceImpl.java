package com.avaj.Expense_Manager.service;

import com.avaj.Expense_Manager.entity.FeedBack;
import com.avaj.Expense_Manager.repository.FeedBackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedBackServiceImpl implements FeedBackService{
    private FeedBackRepository feedBackRepository;

    public FeedBackServiceImpl(FeedBackRepository feedBackRepository) {
        this.feedBackRepository = feedBackRepository;
    }

    @Override
    public List<FeedBack> getFeedBack() {
        return feedBackRepository.findAll();
    }

    @Override
    public void save(FeedBack feedBack) {
        FeedBack feedBack1 = new FeedBack();
        feedBack1.setFeedBack(feedBack.getFeedBack());
        feedBack1.setType(feedBack.getType());
        feedBackRepository.save(feedBack1);
    }
    @Override
    public void delete(Long feedBackId){
        feedBackRepository.deleteById(feedBackId);
    }
}
