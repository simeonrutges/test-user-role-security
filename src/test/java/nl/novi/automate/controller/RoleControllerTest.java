package nl.novi.automate.controller;

import nl.novi.automate.security.JwtService;
import nl.novi.automate.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import nl.novi.automate.dto.RoleDto;
import nl.novi.automate.service.RoleService;


@ExtendWith(SpringExtension.class)
@WebMvcTest(RoleController.class)
@AutoConfigureMockMvc(addFilters = false)
class RoleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @MockBean
    JwtService jwtService;

    @MockBean
    private UserService userService;

    @Test
//    @WithMockUser(roles = "BESTUURDER")
    public void createRole_shouldReturnRoleString() throws Exception {
        // Arrange
        RoleDto roleDto = new RoleDto();
        roleDto.rolename = "TestRole";
        String expected = "Done";

        // Define behavior for roleService
        when(roleService.createRole(any(RoleDto.class))).thenReturn(expected);

        // Convert object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(roleDto);

        // Act and Assert
        mockMvc.perform(post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));

        verify(roleService, times(1)).createRole(any(RoleDto.class));
    }
}