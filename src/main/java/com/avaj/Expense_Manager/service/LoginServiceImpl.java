package com.avaj.Expense_Manager.service;

import com.avaj.Expense_Manager.entity.LoginDetail;
import com.avaj.Expense_Manager.repository.LoginRepository;
import jakarta.transaction.Transactional;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    private LoginRepository theLoginRepository;
//    private PasswordEncoder passwordEncoder;

    public LoginServiceImpl(LoginRepository theLoginRepository) {
        this.theLoginRepository = theLoginRepository;

    }

    @Override
    @Transactional
    public void saveUser(LoginDetail loginDetail) {
        LoginDetail theLoginDetail = new LoginDetail();
        theLoginDetail.setUserName(loginDetail.getUserName());
//        theLoginDetail.setPassword(passwordEncoder.encode(loginDetail.getPassword()));
        theLoginRepository.save(theLoginDetail);
    }

    @Override
    @Transactional
    public void update(LoginDetail loginDetail) {
        LoginDetail tempLoginDetail = theLoginRepository.findById(loginDetail.getUserName()).get();
        tempLoginDetail.setUserName(loginDetail.getUserName());
//        tempLoginDetail.setPassword(passwordEncoder.encode(loginDetail.getPassword()));
        theLoginRepository.save(tempLoginDetail);
    }

    @Override
    public void delete(LoginDetail loginDetail) {
        theLoginRepository.delete(loginDetail);
    }
}
