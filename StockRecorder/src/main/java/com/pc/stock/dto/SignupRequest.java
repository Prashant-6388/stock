package com.pc.stock.dto;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    @Getter
    @Setter
    private String username;
 
    @NotBlank
    @Size(max = 50)
    @Email
    @Getter
    @Setter
    private String email;
    
    @Getter
    @Setter
    private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    @Getter
    @Setter
    private String password;
  

}