package nl.novi.automate.service;

import nl.novi.automate.dto.RoleDto;
import nl.novi.automate.model.Role;
import nl.novi.automate.repository.RoleRepository;
import org.springframework.stereotype.Service;

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
