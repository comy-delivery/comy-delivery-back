package com.comy_delivery_back.service;

import com.comy_delivery_back.dto.request.AdminRequestDTO;
import com.comy_delivery_back.dto.response.AdminResponseDTO;
import com.comy_delivery_back.model.Admin;
import com.comy_delivery_back.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }

    public AdminResponseDTO cadastrarAdmin(AdminRequestDTO adminRequestDTO){
        if(adminRepository.findByCpfAdmin(adminRequestDTO.cpfAdmin()).isPresent()){
            throw new RuntimeException("CPF já foi cadastrado");
        }

        if (adminRepository.findByEmailAdmin(adminRequestDTO.emailAdmin()).isPresent()){
            throw new RuntimeException("Email já cadastrado.");
        }

        Admin novoAdmin = new Admin();

        novoAdmin.setUsername(adminRequestDTO.username());
        novoAdmin.setPassword(adminRequestDTO.password()); //criptografar aqui
        novoAdmin.setNmAdmin(adminRequestDTO.nmAdmin());
        novoAdmin.setEmailAdmin(adminRequestDTO.emailAdmin());
        novoAdmin.setCpfAdmin(adminRequestDTO.cpfAdmin());

        adminRepository.save(novoAdmin);

        return new AdminResponseDTO(novoAdmin);
    }

    public AdminResponseDTO buscarAdminPorId(Long idAdmin){
        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> new IllegalArgumentException("Id não pertence a um admin"));

        return new AdminResponseDTO(admin);
    }

    //metodo extra apenas para teste
    public void deletarAdmin(Long idAdmin){
        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> new IllegalArgumentException("Id não pertence a um admin"));

        admin.setAtivo(false);

    }

}
