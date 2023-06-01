package ru.itis.springsemwork.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.springsemwork.validation.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpForm {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 2, max = 32)
    private String name;

    @NotBlank
    @ValidPassword
    private String password;

}
