package com.avaj.Expense_Manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Table(name="loginDetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDetail {

//    @Column(name="username")
    @Id
    @NotEmpty(message = "Username is required")
    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters")
    private String userName;

//    @Column(name="password", unique = true)
    @JsonIgnore
    @NotEmpty(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

//    @Column(name="enabled")
//    private Boolean enabled = true;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name="username")
//    private User user;
}
