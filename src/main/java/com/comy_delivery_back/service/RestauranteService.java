package com.comy_delivery_back.service;

import com.comy_delivery_back.dto.request.EnderecoRequestDTO;
import com.comy_delivery_back.dto.request.RestauranteRequestDTO;
import com.comy_delivery_back.dto.response.EnderecoResponseDTO;
import com.comy_delivery_back.dto.response.ProdutoResponseDTO;
import com.comy_delivery_back.dto.response.RestauranteResponseDTO;
import com.comy_delivery_back.model.Endereco;
import com.comy_delivery_back.model.Restaurante;
import com.comy_delivery_back.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Service
public class RestauranteService {
    @Autowired
    private RestauranteRepository restauranteRepository;

    public RestauranteResponseDTO cadastrarRestaurante(RestauranteRequestDTO restauranteRequestDTO){
        if (restauranteRepository.findByCpnj(restauranteRequestDTO.emailRestaurante()).isPresent()){
            throw new IllegalArgumentException("CNPJ já cadastrado.");
        }

        if (restauranteRepository.findByEmailRestaurante(restauranteRequestDTO.emailRestaurante()).isPresent()){
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        if (restauranteRepository.findByUsername(restauranteRequestDTO.username()).isPresent()){
            throw new IllegalArgumentException("Username já cadastrado.");
        }

        Restaurante novoRestaurante = new Restaurante();

        novoRestaurante.setUsername(restauranteRequestDTO.username());
        novoRestaurante.setPassword(restauranteRequestDTO.password()); //encoder aqui
        novoRestaurante.setNmRestaurante(restauranteRequestDTO.nmRestaurante());
        novoRestaurante.setEmailRestaurante(restauranteRequestDTO.emailRestaurante());
        novoRestaurante.setCnpj(restauranteRequestDTO.cnpj());
        novoRestaurante.setTelefoneRestaurante(restauranteRequestDTO.telefoneRestaurante());

        String imagemBase64 = restauranteRequestDTO.imagemLogo();

        if (imagemBase64 != null && !imagemBase64.isEmpty()){
            try{
                byte[] imagemBytes = Base64.getDecoder().decode(imagemBase64);
                novoRestaurante.setImagemLogo(imagemBytes);
            }catch (IllegalArgumentException e){
                throw new IllegalArgumentException("Erro ao decodificar a imagem Base64. O formato é inválido.", e);
            }
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

                    return endereco;
                }).toList();
        novoRestaurante.setEnderecos(enderecos);

        novoRestaurante.setCategoria(restauranteRequestDTO.categoria());
        novoRestaurante.setHorarioAbertura(restauranteRequestDTO.horarioAbertura());
        novoRestaurante.setHorarioFechamento(restauranteRequestDTO.horarioFechamento());
        novoRestaurante.setDiasFuncionamento(restauranteRequestDTO.diasFuncionamento());


        return new RestauranteResponseDTO(
                novoRestaurante.getId(),
                novoRestaurante.getUsername(),
                novoRestaurante.getNmRestaurante(),
                novoRestaurante.getEmailRestaurante(),
                novoRestaurante.getCnpj(),
                novoRestaurante.getTelefoneRestaurante(),
                Base64.getEncoder().encodeToString(novoRestaurante.getImagemLogo()),
                novoRestaurante.getDescricaoRestaurante(),
                novoRestaurante.getEnderecos().stream().map(EnderecoResponseDTO::new).toList(),
                novoRestaurante.getCategoria(),
                novoRestaurante.getHorarioAbertura(),
                novoRestaurante.getHorarioFechamento(),
                novoRestaurante.getDiasFuncionamento(),
                novoRestaurante.getProdutos().stream().map(ProdutoResponseDTO::new).toList(),
                novoRestaurante.getTempoMediaEntrega(),
                novoRestaurante.getAvaliacaoMediaRestaurante(),
                novoRestaurante.isAberto(),
                novoRestaurante.isDisponivel(),
                novoRestaurante.getDataCadastro(),
                novoRestaurante.isAtivo()
        );
    }

    public RestauranteResponseDTO buscarRestaurantePorId(Long idRestaurante){
        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> new IllegalArgumentException("Id Restaurante não encontrado"));

        List<EnderecoResponseDTO> enderecoResponseDTOS = restaurante.getEnderecos()
                .stream()
                .map(EnderecoResponseDTO::new)
                .toList();

        return new RestauranteResponseDTO(
                restaurante.getId(),
                restaurante.getUsername(),
                restaurante.getNmRestaurante(),
                restaurante.getEmailRestaurante(),
                restaurante.getCnpj(),
                restaurante.getTelefoneRestaurante(),
                Base64.getEncoder().encodeToString(restaurante.getImagemLogo()),
                restaurante.getDescricaoRestaurante(),
                restaurante.getEnderecos().stream().map(EnderecoResponseDTO::new).toList(),
                restaurante.getCategoria(),
                restaurante.getHorarioAbertura(),
                restaurante.getHorarioFechamento(),
                restaurante.getDiasFuncionamento(),
                restaurante.getProdutos().stream().map(ProdutoResponseDTO::new).toList(),
                restaurante.getTempoMediaEntrega(),
                restaurante.getAvaliacaoMediaRestaurante(),
                restaurante.isAberto(),
                restaurante.isDisponivel(),
                restaurante.getDataCadastro(),
                restaurante.isAtivo()
        );

    }

    public RestauranteResponseDTO buscarRestaurantePorCnpj(String cnpj){
        Restaurante restaurante = restauranteRepository.findByCpnj(cnpj)
                .orElseThrow(() -> new IllegalArgumentException("Cnpj Restaurante não encontrado"));

        List<EnderecoResponseDTO> enderecoResponseDTOS = restaurante.getEnderecos()
                .stream()
                .map(EnderecoResponseDTO::new)
                .toList();

        return new RestauranteResponseDTO(
                restaurante.getId(),
                restaurante.getUsername(),
                restaurante.getNmRestaurante(),
                restaurante.getEmailRestaurante(),
                restaurante.getCnpj(),
                restaurante.getTelefoneRestaurante(),
                Base64.getEncoder().encodeToString(restaurante.getImagemLogo()),
                restaurante.getDescricaoRestaurante(),
                restaurante.getEnderecos().stream().map(EnderecoResponseDTO::new).toList(),
                restaurante.getCategoria(),
                restaurante.getHorarioAbertura(),
                restaurante.getHorarioFechamento(),
                restaurante.getDiasFuncionamento(),
                restaurante.getProdutos().stream().map(ProdutoResponseDTO::new).toList(),
                restaurante.getTempoMediaEntrega(),
                restaurante.getAvaliacaoMediaRestaurante(),
                restaurante.isAberto(),
                restaurante.isDisponivel(),
                restaurante.getDataCadastro(),
                restaurante.isAtivo()
        );
    }

    public List<ProdutoResponseDTO> listarProdutosRestaurante(Long idRestaurante){
        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> new IllegalArgumentException("id restaurante não encontrado."));

        List<ProdutoResponseDTO> produtosRestaurante = restaurante.getProdutos()
                .stream()
                .map(ProdutoResponseDTO::new)
                .toList();

        return produtosRestaurante; //ver depois
    }

    public List<RestauranteResponseDTO> listarRestaurantesAbertos(){
        List<RestauranteResponseDTO> restaurantesAbertos = restauranteRepository.findAllByIsAbertoTrue()
                .stream()
                .map(restaurante -> new RestauranteResponseDTO(
                        restaurante.getId(),
                        restaurante.getUsername(),
                        restaurante.getNmRestaurante(),
                        restaurante.getEmailRestaurante(),
                        restaurante.getCnpj(),
                        restaurante.getTelefoneRestaurante(),
                        Base64.getEncoder().encodeToString(restaurante.getImagemLogo()),
                        restaurante.getDescricaoRestaurante(),
                        restaurante.getEnderecos().stream().map(EnderecoResponseDTO::new).toList(),
                        restaurante.getCategoria(),
                        restaurante.getHorarioAbertura(),
                        restaurante.getHorarioFechamento(),
                        restaurante.getDiasFuncionamento(),
                        restaurante.getProdutos().stream().map(ProdutoResponseDTO::new).toList(),
                        restaurante.getTempoMediaEntrega(),
                        restaurante.getAvaliacaoMediaRestaurante(),
                        restaurante.isAberto(),
                        restaurante.isDisponivel(),
                        restaurante.getDataCadastro(),
                        restaurante.isAtivo()
                )).toList();

        return restaurantesAbertos;

    }

    public double calcularAvaliacaoMediaRestaurante (){

    }

    //fechar automatico nos dias e horarios necessarios
    public void fecharRestaurante(Long idRestaurante){
        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> new IllegalArgumentException("id restaurante não encontrado"));

        restaurante.setAberto(false);

    }

    //abrir automatico nos dias e horarios necessarios
    public void abrirRestaurante(Long idRestaurante){
        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> new IllegalArgumentException("id restaurante não encontrado"));

        restaurante.setAberto(true);

    }

    public void indiponibilizarRestaurante(Long idRestaurante){
        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> new IllegalArgumentException("id restaurante não encontrado"));

        restaurante.setDisponivel(false);
    }

    public void disponibilidarRestaurante(Long idRestaurante){
        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> new IllegalArgumentException("id restaurante não encontrado"));

        restaurante.setDisponivel(true);
    }

    public void deletarRestaurante(Long idRestaurante){
        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> new IllegalArgumentException("id restaurante não encontrado"));
        restaurante.setAtivo(false);

    }

}
