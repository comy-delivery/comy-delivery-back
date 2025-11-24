package com.comy_delivery_back.service;

import com.comy_delivery_back.dto.request.AdminRequestDTO;
import com.comy_delivery_back.dto.response.AdminResponseDTO;
import com.comy_delivery_back.exception.AdminNaoEncontradoException;
import com.comy_delivery_back.model.Admin;
import com.comy_delivery_back.repository.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.comy_delivery_back.exception.RegistrosDuplicadosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder){
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AdminResponseDTO cadastrarAdmin(AdminRequestDTO adminRequestDTO){
        log.warn("Criação de novo ADMINISTRADOR solicitada. Username: {}", adminRequestDTO.username());

        if(adminRepository.findByCpfAdmin(adminRequestDTO.cpfAdmin()).isPresent()){
            log.error("Tentativa de cadastro com CPF duplicado para Admin: {}", adminRequestDTO.cpfAdmin()); // [AJUSTE] Log de Erro
            throw new RegistrosDuplicadosException("CPF já foi cadastrado");
        }

        if (adminRepository.findByEmailAdmin(adminRequestDTO.emailAdmin()).isPresent()){
            log.error("Tentativa de cadastro com Email duplicado para Admin: {}", adminRequestDTO.emailAdmin()); // [AJUSTE] Log de Erro
            throw new RegistrosDuplicadosException("Email já cadastrado.");
        }

        Admin novoAdmin = new Admin();

        novoAdmin.setUsername(adminRequestDTO.username());
        novoAdmin.setPassword(passwordEncoder.encode(adminRequestDTO.password()));
        novoAdmin.setNmAdmin(adminRequestDTO.nmAdmin());
        novoAdmin.setEmailAdmin(adminRequestDTO.emailAdmin());
        novoAdmin.setCpfAdmin(adminRequestDTO.cpfAdmin());

        adminRepository.save(novoAdmin);
        log.info("Novo Admin cadastrado com sucesso. ID: {}", novoAdmin.getId());

        return new AdminResponseDTO(novoAdmin);
    }

    public AdminResponseDTO buscarAdminPorId(Long idAdmin){
        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> {
                    log.warn("Busca por Admin falhou. ID não encontrado: {}", idAdmin); // [AJUSTE] Log de Aviso
                    return new AdminNaoEncontradoException(idAdmin);
                });

        return new AdminResponseDTO(admin);
    }

    @Transactional
    //metodo extra apenas para teste
    public void deletarAdmin(Long idAdmin){
        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> {
                    log.error("Tentativa de inativar Admin falhou. ID não encontrado: {}", idAdmin); // [AJUSTE] Log de Erro
                    return new IllegalArgumentException("Id não pertence a um admin");
                });

        admin.setAtivo(false);
        adminRepository.save(admin);
        log.info("Admin ID {} inativado (soft delete).", idAdmin);

    }

}