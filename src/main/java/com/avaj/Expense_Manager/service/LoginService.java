package com.avaj.Expense_Manager.service;

import com.avaj.Expense_Manager.entity.LoginDetail;

public interface LoginService {
    void createUser(LoginDetail loginDetail);
    void update(LoginDetail loginDetail);
    void delete(LoginDetail loginDetail);
}
