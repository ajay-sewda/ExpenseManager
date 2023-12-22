package com.avaj.Expense_Manager.service;

import com.avaj.Expense_Manager.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;
    private LoginService loginService;
    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, LoginService loginService) {
        this.roleRepository = roleRepository;
        this.loginService = loginService;
    }

    @Override
    public void createRole(Role role) {
        Role tempRole = new Role();
        tempRole.setRole(role.getRole());
        tempRole.setLoginDetail(role.getLoginDetail());
        roleRepository.save(tempRole);
    }

    @Override
    public void updateRole(Role role) {
        Role tempRole = roleRepository.findById(role.getLoginDetail().getUserName()).get();
        tempRole.setRole(role.getRole());
        tempRole.setLoginDetail(role.getLoginDetail());
        roleRepository.save(tempRole);
    }

    @Override
    public void deleteRole(String username) {
        roleRepository.deleteById(username);
    }
}
