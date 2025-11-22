package com.comy_delivery_back.service;

import com.comy_delivery_back.dto.request.EnderecoRequestDTO;
import com.comy_delivery_back.dto.request.RestauranteRequestDTO;
import com.comy_delivery_back.dto.response.EnderecoResponseDTO;
import com.comy_delivery_back.dto.response.ProdutoResponseDTO;
import com.comy_delivery_back.dto.response.RestauranteResponseDTO;
import com.comy_delivery_back.enums.DiasSemana;
import com.comy_delivery_back.exception.EnderecoNaoEncontradoException;
import com.comy_delivery_back.exception.RestauranteNaoEncontradoException;
import com.comy_delivery_back.model.Endereco;
import com.comy_delivery_back.model.Restaurante;
import com.comy_delivery_back.repository.EnderecoRepository;
import com.comy_delivery_back.repository.RestauranteRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

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
        if (restauranteRepository.findByCnpj(restauranteRequestDTO.emailRestaurante()).isPresent()){
            throw new IllegalArgumentException("CNPJ já cadastrado.");
        }

        if (restauranteRepository.findByEmailRestaurante(restauranteRequestDTO.cnpj()).isPresent()){
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        if (restauranteRepository.findByUsername(restauranteRequestDTO.username()).isPresent()){
            throw new IllegalArgumentException("Username já cadastrado.");
        }

        Restaurante novoRestaurante = new Restaurante();

        novoRestaurante.setUsername(restauranteRequestDTO.username());
        novoRestaurante.setPassword(passwordEncoder.encode(restauranteRequestDTO.password()));
        novoRestaurante.setNmRestaurante(restauranteRequestDTO.nmRestaurante());
        novoRestaurante.setEmailRestaurante(restauranteRequestDTO.emailRestaurante());
        novoRestaurante.setCnpj(restauranteRequestDTO.cnpj());
        novoRestaurante.setTelefoneRestaurante(restauranteRequestDTO.telefoneRestaurante());

        if (imagemFile != null && !imagemFile.isEmpty()) {
            //converte o MultipartFile em byte
            byte[] imagemBytes = imagemFile.getBytes();
            novoRestaurante.setImagemLogo(imagemBytes);
        } else {
            novoRestaurante.setImagemLogo(null);
        }

        novoRestaurante.setDescricaoRestaurante(restauranteRequestDTO.descricaoRestaurante());

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

        novoRestaurante.setCategoria(restauranteRequestDTO.categoria());
        novoRestaurante.setHorarioAbertura(restauranteRequestDTO.horarioAbertura());
        novoRestaurante.setHorarioFechamento(restauranteRequestDTO.horarioFechamento());
        novoRestaurante.setDiasFuncionamento(restauranteRequestDTO.diasFuncionamento());

        Restaurante restauranteSalvo = restauranteRepository.save(novoRestaurante);


        return new RestauranteResponseDTO(restauranteSalvo);
    }

    @Transactional
    public RestauranteResponseDTO atualizarRestaurante(Long idRestaurante,
                                                       RestauranteRequestDTO restauranteRequestDTO,
                                                       MultipartFile imagemLogo) throws IOException {
        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> new IllegalArgumentException("Id restaurante não encontrado."));


        if(restauranteRequestDTO.nmRestaurante() != null && !restauranteRequestDTO.nmRestaurante().isBlank()){
            restaurante.setNmRestaurante(restauranteRequestDTO.nmRestaurante());
        }
        if (restauranteRequestDTO.emailRestaurante() != null && !restauranteRequestDTO.emailRestaurante().isBlank() &&
                !restauranteRequestDTO.emailRestaurante().equalsIgnoreCase(restaurante.getEmailRestaurante())){
            if (restauranteRepository.findByEmailRestaurante(restauranteRequestDTO.emailRestaurante()).isPresent()){
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

        return new RestauranteResponseDTO(restaurante);
    }

    @Transactional
    public EnderecoResponseDTO alterarEnderecoRestaurante(Long idRestaurante, Long idEndereco,
                                                          EnderecoRequestDTO enderecoRequestDTO){
        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado."));

        Endereco endereco = enderecoRepository.findByIdEndereco(idEndereco)
                .orElseThrow(() -> new EnderecoNaoEncontradoException(idEndereco));

        if (!endereco.getRestaurante().getId().equals(idRestaurante)){
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
                .orElseThrow(() -> new IllegalArgumentException("Id Restaurante não encontrado"));

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
    public List<RestauranteResponseDTO> listarRestaurantesAbertos(){
        List<RestauranteResponseDTO> restaurantesAbertos = restauranteRepository.findAllByIsAbertoTrue()
                .stream()
                .map(RestauranteResponseDTO::new).toList();

        return restaurantesAbertos;

    }

    @Transactional
    public void fecharRestaurante(Long idRestaurante){
        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> new IllegalArgumentException("id restaurante não encontrado"));

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
        DayOfWeek diaAtualDayOfWeek = DayOfWeek.from(LocalDateTime.now().getDayOfWeek());

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
                    System.err.println("Falha ao enviar e-mail de recuperação: " + ex.getMessage());
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
