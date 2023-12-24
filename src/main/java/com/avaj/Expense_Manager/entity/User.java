package com.avaj.Expense_Manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "username", unique = true)
    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String userName;

    @Column(name = "password")
    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "first_name")
    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinTable(name = "user_group",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")})
    private List<Group> userGroups;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinTable(name = "user_expense",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "expense_id")})
    private List<Expense> expenses;
}
