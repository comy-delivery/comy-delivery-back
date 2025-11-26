package com.comy_delivery_back.dto.request;

import com.comy_delivery_back.enums.RoleUsuario;

public record SignupRequestDTO(
        String username,
        String password,
        RoleUsuario role
) {
}
