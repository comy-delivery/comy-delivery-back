package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.model.Admin;

public record AdminResponseDTO(
        Long id,
        String username,
        String nmAdmin,
        String cpfAdmin,
        String emailAdmin
) {
    public AdminResponseDTO(Admin a){
        this(
                a.getId(),
                a.getUsername(),
                a.getNmAdmin(),
                a.getCpfAdmin(),
                a.getEmailAdmin()
        );
    }
}
