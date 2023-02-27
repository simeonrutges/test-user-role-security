package com.example.les16.service;

import com.example.les16.dto.RoleDto;
import com.example.les16.model.Role;
import com.example.les16.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public String createRole(RoleDto roleDto) {
        Role newRole = new Role();
        newRole.setRolename(roleDto.rolename);
        roleRepository.save(newRole);

        return "Done";
    }
}
