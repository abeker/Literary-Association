package com.lu.literaryassociation.dto.request;

import com.lu.literaryassociation.guard.SQLInjectionSafe;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {
    @NotNull(message = "Username is mandatory")
    @Size(min=5, max=30, message = "Username length must be between 5 and 30 characters.")
    @Pattern(regexp = "^(?!<.+?>).*$", message = "Username cannot contain html elements.")
    @SQLInjectionSafe
    private String username;

    @NotNull(message = "Password is mandatory")
    @Pattern.List({
            @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&].{9,}",
                    message = "Password must contain digit, special character, lowercase and uppercase letter. Min length is 9."),
            @Pattern(regexp = "^(?!<.+?>).*$", message = "Password cannot contain html elements.")
    })
    @SQLInjectionSafe
    private String password;

}
