package com.avaj.Expense_Manager.service;

import com.avaj.Expense_Manager.entity.LoginDetail;

public interface LoginService {
    void saveUser(LoginDetail loginDetail);
    void update(LoginDetail loginDetail);
    void delete(LoginDetail loginDetail);
}
