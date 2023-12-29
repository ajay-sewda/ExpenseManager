package com.avaj.Expense_Manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordForm {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
