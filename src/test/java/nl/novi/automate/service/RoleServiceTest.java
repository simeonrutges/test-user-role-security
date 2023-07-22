package nl.novi.automate.service;

import nl.novi.automate.dto.RoleDto;
import nl.novi.automate.model.Role;
import nl.novi.automate.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {
    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    RoleService roleService;



@Test
void createRoleTest() {

    RoleDto roleDto = new RoleDto();
    roleDto.rolename = "BESTUURDER";

    ArgumentCaptor<Role> roleCaptor = ArgumentCaptor.forClass(Role.class);

    roleService.createRole(roleDto);

    verify(roleRepository, times(1)).save(roleCaptor.capture());
    Role capturedRole = roleCaptor.getValue();
    assertEquals("BESTUURDER", capturedRole.getRolename());
}

}