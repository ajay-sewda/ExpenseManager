package com.avaj.Expense_Manager.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordForm {

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String oldPassword;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String newPassword;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String confirmPassword;
}
