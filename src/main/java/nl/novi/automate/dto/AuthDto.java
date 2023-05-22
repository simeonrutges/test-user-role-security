package nl.novi.automate.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AuthDto {
    public String username;

//    @NotBlank(message = "Please enter a password between 4 and 30 characters")
//    @Size(min=4, message = "Name should be at least 4 characters")
//    @Size(max=30, message = "Name should not be greater than 30 characters")
    public String password;
}
