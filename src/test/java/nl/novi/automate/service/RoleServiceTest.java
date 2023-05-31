package nl.novi.automate.service;

import nl.novi.automate.dto.RoleDto;
import nl.novi.automate.model.Role;
import nl.novi.automate.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
        // Arrange
        RoleDto roleDto = new RoleDto();
        roleDto.rolename = "TestRole";

        // Act
        roleService.createRole(roleDto);

        // Assert
        verify(roleRepository, times(1)).save(any(Role.class));
    }
}