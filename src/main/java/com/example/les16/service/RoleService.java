package com.example.les16.service;

import com.example.les16.dto.RoleDto;
import com.example.les16.model.Role;
import com.example.les16.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public String createRole(RoleDto roleDto) {
        Role newRole = new Role();
        newRole.setRolename(roleDto.rolename);
        roleRepository.save(newRole);

        return "Done";
    }
}
