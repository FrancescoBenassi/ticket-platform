package org.milestone4.ticket_platform.service;


import java.util.List;

import org.milestone4.ticket_platform.model.Role;
import org.milestone4.ticket_platform.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}