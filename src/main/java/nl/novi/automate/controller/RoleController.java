package nl.novi.automate.controller;

import nl.novi.automate.dto.RoleDto;
import nl.novi.automate.service.RoleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
