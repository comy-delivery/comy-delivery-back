package com.comy_delivery_back.service;

import com.comy_delivery_back.dto.request.EnderecoRequestDTO;
import com.comy_delivery_back.dto.request.RestauranteRequestDTO;
import com.comy_delivery_back.dto.response.EnderecoResponseDTO;
import com.comy_delivery_back.dto.response.ProdutoResponseDTO;
import com.comy_delivery_back.dto.response.RestauranteResponseDTO;
import com.comy_delivery_back.enums.DiasSemana;
import com.comy_delivery_back.enums.RoleUsuario;
import com.comy_delivery_back.exception.EnderecoNaoEncontradoException;
import com.comy_delivery_back.exception.RestauranteNaoEncontradoException;
import com.comy_delivery_back.model.Endereco;
import com.comy_delivery_back.model.Restaurante;
import com.comy_delivery_back.repository.EnderecoRepository;
import com.comy_delivery_back.repository.RestauranteRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final EnderecoRepository enderecoRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public RestauranteService(RestauranteRepository restauranteRepository,
                              EnderecoRepository enderecoRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.restauranteRepository = restauranteRepository;
        this.enderecoRepository = enderecoRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Transactional
    public RestauranteResponseDTO cadastrarRestaurante(RestauranteRequestDTO restauranteRequestDTO, MultipartFile imagemFile) throws IOException {

        log.info("Iniciando cadastro do novo restaurante: {}", restauranteRequestDTO.nmRestaurante());

        if (restauranteRepository.findByCnpj(restauranteRequestDTO.cnpj()).isPresent()){
            log.error("CNPJ duplicado detectado: {}", restauranteRequestDTO.cnpj());
            throw new IllegalArgumentException("CNPJ já cadastrado.");
        }

        if (restauranteRepository.findByEmailRestaurante(restauranteRequestDTO.emailRestaurante()).isPresent()){
            log.error("E-mail duplicado detectado: {}", restauranteRequestDTO.emailRestaurante());
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        if (restauranteRepository.findByUsername(restauranteRequestDTO.username()).isPresent()){
            log.error("Username duplicado detectado: {}", restauranteRequestDTO.username());
            throw new IllegalArgumentException("Username já cadastrado.");
        }

        //refatorado para receber instancia no signup
        Restaurante novoRestaurante = Restaurante.builder()
                .username(restauranteRequestDTO.username())
                .password(passwordEncoder.encode(restauranteRequestDTO.password()))
                .roleUsuario(RoleUsuario.RESTAURANTE)

                .nmRestaurante(restauranteRequestDTO.nmRestaurante())
                .emailRestaurante(restauranteRequestDTO.emailRestaurante())
                .cnpj(restauranteRequestDTO.cnpj())
                .telefoneRestaurante(restauranteRequestDTO.telefoneRestaurante())
                .descricaoRestaurante(restauranteRequestDTO.descricaoRestaurante())
                .categoria(restauranteRequestDTO.categoria())
                .horarioAbertura(restauranteRequestDTO.horarioAbertura())
                .horarioFechamento(restauranteRequestDTO.horarioFechamento())
                .diasFuncionamento(restauranteRequestDTO.diasFuncionamento())
                .dataCadastro(LocalDate.now())
                .build();

        if (imagemFile != null && !imagemFile.isEmpty()) {
            //converte o MultipartFile em byte
            byte[] imagemBytes = imagemFile.getBytes();
            novoRestaurante.setImagemLogo(imagemBytes);
        } else {
            novoRestaurante.setImagemLogo(null);
        }

        List<Endereco> enderecos = restauranteRequestDTO.enderecos().stream()
                .map(enderecoRequestDTO -> {
                    Endereco endereco = new Endereco();

                    endereco.setLogradouro(enderecoRequestDTO.logradouro());
                    endereco.setNumero(enderecoRequestDTO.numero());
                    endereco.setComplemento(enderecoRequestDTO.complemento());
                    endereco.setBairro(enderecoRequestDTO.bairro());
                    endereco.setCidade(enderecoRequestDTO.cidade());
                    endereco.setCep(enderecoRequestDTO.cep());
                    endereco.setEstado(enderecoRequestDTO.estado());
                    endereco.setTipoEndereco(enderecoRequestDTO.tipoEndereco());
                    endereco.setRestaurante(novoRestaurante);

                    return endereco;

                }).toList();
        novoRestaurante.setEnderecos(enderecos);

        Restaurante restauranteSalvo = restauranteRepository.save(novoRestaurante);
        log.info("Restaurante cadastrado com sucesso. ID: {}", restauranteSalvo.getId());

        return new RestauranteResponseDTO(restauranteSalvo);
    }

    @Transactional
    public RestauranteResponseDTO atualizarRestaurante(Long idRestaurante,
                                                       RestauranteRequestDTO restauranteRequestDTO,
                                                       MultipartFile imagemLogo) throws IOException {

        log.info("Tentativa de atualização do restaurante ID: {}", idRestaurante);

        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> {
                    log.error("Restaurante não encontrado para atualização, ID: {}", idRestaurante);
                    return new RestauranteNaoEncontradoException(idRestaurante);
                });


        if(restauranteRequestDTO.nmRestaurante() != null && !restauranteRequestDTO.nmRestaurante().isBlank()){
            restaurante.setNmRestaurante(restauranteRequestDTO.nmRestaurante());
        }
        if (restauranteRequestDTO.emailRestaurante() != null && !restauranteRequestDTO.emailRestaurante().isBlank() &&
                !restauranteRequestDTO.emailRestaurante().equalsIgnoreCase(restaurante.getEmailRestaurante())){
            if (restauranteRepository.findByEmailRestaurante(restauranteRequestDTO.emailRestaurante()).isPresent()){
                log.error("E-mail '{}' já está em uso por outro restaurante.", restauranteRequestDTO.emailRestaurante());
                throw new IllegalArgumentException("E-mail já cadastrado para outro restaurante.");
            }

            restaurante.setEmailRestaurante(restauranteRequestDTO.emailRestaurante());
        }

        if (restauranteRequestDTO.telefoneRestaurante() != null && !restauranteRequestDTO.telefoneRestaurante().isBlank()){
            restaurante.setTelefoneRestaurante(restauranteRequestDTO.telefoneRestaurante());
        }

        if (imagemLogo != null && !imagemLogo.isEmpty()){
            try{
                byte[] logoBytes = imagemLogo.getBytes();
                restaurante.setImagemLogo(logoBytes);
            } catch (IOException e) {
                log.error("Falha ao ler o arquivo de imagem durante o cadastro.", e);
                throw new RuntimeException("Falha ao ler o arquivo de imagem.", e);
            }
        }

        if(restauranteRequestDTO.descricaoRestaurante() != null && !restauranteRequestDTO.descricaoRestaurante().isBlank()){
            restaurante.setDescricaoRestaurante(restauranteRequestDTO.descricaoRestaurante());
        }

        if (restauranteRequestDTO.categoria() != null){
            restaurante.setCategoria(restauranteRequestDTO.categoria());
        }

        if (restauranteRequestDTO.horarioAbertura() != null){
            restaurante.setHorarioAbertura(restauranteRequestDTO.horarioAbertura());
        }

        if (restauranteRequestDTO.horarioFechamento() != null){
            restaurante.setHorarioFechamento(restauranteRequestDTO.horarioFechamento());
        }

        if (restauranteRequestDTO.diasFuncionamento() != null){
            restaurante.setDiasFuncionamento(restauranteRequestDTO.diasFuncionamento());
        }

        if (restauranteRequestDTO.tempoMediaEntrega() != null){
            restaurante.setTempoMediaEntrega(restauranteRequestDTO.tempoMediaEntrega());
        }

        restauranteRepository.save(restaurante);
        log.info("Restaurante ID {} atualizado com sucesso.", idRestaurante);
        return new RestauranteResponseDTO(restaurante);
    }

    @Transactional
    public EnderecoResponseDTO alterarEnderecoRestaurante(Long idRestaurante, Long idEndereco,
                                                          EnderecoRequestDTO enderecoRequestDTO){
        log.info("Restaurante ID {} atualizado com sucesso.", idRestaurante);

        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> {
                    log.error("Restaurante não encontrado ao tentar alterar endereço. ID: {}", idRestaurante);
                    return new IllegalArgumentException("Restaurante não encontrado.");
                });

        Endereco endereco = enderecoRepository.findByIdEndereco(idEndereco)
                .orElseThrow(() -> {
                    log.error("Endereço não encontrado para alteração. ID: {}", idEndereco);
                    return new EnderecoNaoEncontradoException(idEndereco);
                });

        if (!endereco.getRestaurante().getId().equals(idRestaurante)){
            log.error("Tentativa de alterar endereço ID {} que pertence ao restaurante, com o restaurante ID", idRestaurante);
            throw new IllegalArgumentException("Endereço não pertence ao restaurante informado.");
        }

        if (enderecoRequestDTO.logradouro() != null && !enderecoRequestDTO.logradouro().isBlank()){
            endereco.setLogradouro(enderecoRequestDTO.logradouro());
        }

        if (enderecoRequestDTO.numero() != null && !enderecoRequestDTO.numero().isBlank()){
            endereco.setNumero(enderecoRequestDTO.numero());
        }

        if (enderecoRequestDTO.complemento() != null && !enderecoRequestDTO.complemento().isBlank()){
            endereco.setComplemento(enderecoRequestDTO.complemento());
        }

        if (enderecoRequestDTO.bairro() != null && !enderecoRequestDTO.bairro().isBlank()){
            endereco.setBairro(enderecoRequestDTO.bairro());
        }

        if (enderecoRequestDTO.cidade() != null && !enderecoRequestDTO.cidade().isBlank()){
            endereco.setCidade(enderecoRequestDTO.cidade());
        }

        if (enderecoRequestDTO.cep() != null && !enderecoRequestDTO.cep().isBlank()){
            endereco.setCep(enderecoRequestDTO.cep());
        }

        if (enderecoRequestDTO.estado() != null && !enderecoRequestDTO.estado().isBlank()){
            endereco.setEstado(enderecoRequestDTO.estado());
        }

        if (enderecoRequestDTO.tipoEndereco() != null){
            endereco.setTipoEndereco(enderecoRequestDTO.tipoEndereco());
        }

        enderecoRepository.save(endereco);

        return new EnderecoResponseDTO(endereco);
    }

    @Transactional
    public RestauranteResponseDTO buscarRestaurantePorId(Long idRestaurante){
        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> {
                    log.warn("Busca por restaurante falhou. ID não encontrado: {}", idRestaurante);
                    return new IllegalArgumentException("Id Restaurante não encontrado");
                } );

        List<EnderecoResponseDTO> enderecoResponseDTOS = restaurante.getEnderecos()
                .stream()
                .map(EnderecoResponseDTO::new)
                .toList();

        return new RestauranteResponseDTO(restaurante);

    }
    @Transactional
    public RestauranteResponseDTO buscarRestaurantePorCnpj(String cnpj){
        Restaurante restaurante = restauranteRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new IllegalArgumentException("Cnpj Restaurante não encontrado"));

        List<EnderecoResponseDTO> enderecoResponseDTOS = restaurante.getEnderecos()
                .stream()
                .map(EnderecoResponseDTO::new)
                .toList();

        return new RestauranteResponseDTO(restaurante);
    }

    @Transactional
    public List<ProdutoResponseDTO> listarProdutosRestaurante(Long idRestaurante){
        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> new IllegalArgumentException("id restaurante não encontrado."));

        List<ProdutoResponseDTO> produtosRestaurante = restaurante.getProdutos()
                .stream()
                .map(ProdutoResponseDTO::new)
                .toList();

        return produtosRestaurante; //ver depois
    }

    @Transactional
    public List<EnderecoResponseDTO> listarEnderecosRestaurante(Long idRestaurante){
        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(()-> new RestauranteNaoEncontradoException(idRestaurante));

        return restaurante.getEnderecos().stream().map(EnderecoResponseDTO::new).toList();
    }

    @Transactional
    public List<RestauranteResponseDTO> listarRestaurantesAbertos(){
        List<RestauranteResponseDTO> restaurantesAbertos = restauranteRepository.findAllByIsAbertoTrue()
                .stream()
                .map(RestauranteResponseDTO::new).toList();

        return restaurantesAbertos;

    }

    @Transactional
    public void fecharRestaurante(Long idRestaurante){
        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> {
                    log.error("Tentativa de fechar restaurante falhou. ID não encontrado: {}", idRestaurante);
                    return new IllegalArgumentException("id restaurante não encontrado");
                });

        restaurante.setAberto(false);

    }

    @Transactional
    public void abrirRestaurante(Long idRestaurante){
        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> new IllegalArgumentException("id restaurante não encontrado"));

        restaurante.setAberto(true);

    }

    @Transactional
    public void atualizarStatusAberturaFechamento(){
        LocalTime horaAtual = LocalTime.now();
        DayOfWeek diaAtualDayOfWeek = LocalDateTime.now().getDayOfWeek();

        //metodo static do enum DiasSemana
        DiasSemana diaAtualEnum = DiasSemana.fromDayOfWeek(diaAtualDayOfWeek);

        List<Restaurante> restaurantes = restauranteRepository.findAll();

        for (Restaurante restaurante : restaurantes){
            if (restaurante.isAtivo() && restaurante.isDisponivel()){
                boolean deveEstarAberto = false;

                if(restaurante.getDiasFuncionamento().contains(diaAtualEnum)){
                    LocalTime abertura = restaurante.getHorarioAbertura();
                    LocalTime fechamento = restaurante.getHorarioFechamento();

                    if (abertura.isBefore(fechamento)) {
                        //Horario normal (10:00 - 22:00)
                        if (horaAtual.isAfter(abertura) && horaAtual.isBefore(fechamento)) {
                            deveEstarAberto = true;
                        }
                    } else {
                        //Horario Noturno/Virada de Dia (22:00 - 02:00)
                        // Aberto se for DEPOIS da abertura (22:00) OU ANTES do fechamento (02:00)
                        if (horaAtual.isAfter(abertura) || horaAtual.isBefore(fechamento)) {
                            deveEstarAberto = true;
                        }
                    }
                }

                if (restaurante.isAberto() != deveEstarAberto){
                    restaurante.setAberto(deveEstarAberto);
                    restauranteRepository.save(restaurante);
                }
            }
        }
    }

    @Transactional
    public void indiponibilizarRestaurante(Long idRestaurante){
        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> new IllegalArgumentException("id restaurante não encontrado"));

        restaurante.setDisponivel(false);
    }

    @Transactional
    public void disponibilidarRestaurante(Long idRestaurante){
        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> new IllegalArgumentException("id restaurante não encontrado"));

        restaurante.setDisponivel(true);
    }

    @Transactional
    public void deletarRestaurante(Long idRestaurante){
        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> new IllegalArgumentException("id restaurante não encontrado"));
        restaurante.setAtivo(false);

    }

    @Transactional
    public boolean iniciarRecuperacaoSenha (String email){
        Restaurante restaurante = restauranteRepository.findByEmailRestaurante(email)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(email));

        String token = UUID.randomUUID().toString();
        LocalDateTime expiracao = LocalDateTime.now().plusMinutes(15);

        restaurante.setTokenRecuperacaoSenha(token);
        restaurante.setExpiracaoToken(expiracao);

        restauranteRepository.save(restaurante);

        String linkRecuperacao = "http://localhost/8084/reset-password?token=" + token;

        emailService.enviarEmailRecuperacao(restaurante.getEmailRestaurante(), linkRecuperacao)
                .exceptionally(ex ->{
                    return false;
                });
        return true;
    }

    @Transactional
    public boolean redefinirSenha(String token, String novaSenha){
        Restaurante restaurante = restauranteRepository.findByTokenRecuperacaoSenha(token)
                .orElseThrow(() -> new IllegalArgumentException("Token de recuperação inválido ou não encontrado."));

        if (restaurante.getExpiracaoToken() != null && restaurante.getExpiracaoToken().isBefore(LocalDateTime.now())){
            restaurante.setTokenRecuperacaoSenha(null);
            restaurante.setExpiracaoToken(null);
            restauranteRepository.save(restaurante);
            throw new RuntimeException("Token de recuperação expirado. Solicite uma nova recuperação.");
        }

        restaurante.setPassword(passwordEncoder.encode(novaSenha));

        restaurante.setTokenRecuperacaoSenha(null);
        restaurante.setExpiracaoToken(null);
        restauranteRepository.save(restaurante);

        return true;
    }

}
