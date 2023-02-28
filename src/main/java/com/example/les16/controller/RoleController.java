package com.example.les16.controller;

import com.example.les16.dto.RoleDto;
import com.example.les16.model.Role;
import com.example.les16.repository.RoleRepository;
import com.example.les16.service.RoleService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin
//@CrossOrigin
@RestController
public class RoleController {
    private final RoleService roleService;
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/roles")
    public String createRole(@RequestBody RoleDto dto) {
        String dto1 = roleService.createRole(dto);
        return dto1;
    }

}
