package com.galaxiaburguer.service;

import com.galaxiaburguer.dto.PedidoDTO;
import com.galaxiaburguer.exception.LancheNaoEncontradoException;
import com.galaxiaburguer.model.Lanche;
import com.galaxiaburguer.model.Pedido;
import com.galaxiaburguer.repository.LancheRepository;
import com.galaxiaburguer.repository.PedidoRepository;
import com.galaxiaburguer.enums.StatusPedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private LancheRepository lancheRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<PedidoDTO> listarTodos() {
        return pedidoRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public PedidoDTO buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        return convertToDto(pedido);
    }

    public PedidoDTO criar(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        pedido.setStatus(pedidoDTO.getStatus() != null ? pedidoDTO.getStatus() : StatusPedido.RECEBIDO);

        // Converter IDs de lanches para OBJETOS Lanche
        List<Lanche> lanches = pedidoDTO.getLanchesIds().stream()
                .map(id -> lancheRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Lanche não encontrado: " + id)))
                .collect(Collectors.toList());

        pedido.setLanches(lanches);
        Pedido salvo = pedidoRepository.save(pedido);
        return convertToDto(salvo);
    }

    public PedidoDTO atualizarStatus(Long id, StatusPedido status) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedido.setStatus(status);
        Pedido atualizado = pedidoRepository.save(pedido);
        return convertToDto(atualizado);
    }

    public void deletar(Long id) {
        pedidoRepository.deleteById(id);
    }

    public List<PedidoDTO> listarPorStatus(StatusPedido status) {
        return pedidoRepository.findByStatus(status)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Endpoint criativo: gera um pedido aleatório
    public PedidoDTO gerarPedidoAleatorio() {
        List<Lanche> todosLanches = lancheRepository.findAll();
        if (todosLanches.isEmpty()) {
            throw new RuntimeException("Nenhum lanche disponível para criar pedido aleatório");
        }

        Random random = new Random();
        int quantidadeItens = random.nextInt(3) + 1; // 1 a 3 itens

        Pedido pedido = new Pedido();
        pedido.setStatus(StatusPedido.RECEBIDO);

        for (int i = 0; i < quantidadeItens; i++) {
            Lanche lancheAleatorio = todosLanches.get(random.nextInt(todosLanches.size()));
            pedido.getLanches().add(lancheAleatorio);
        }

        Pedido salvo = pedidoRepository.save(pedido);
        return convertToDto(salvo);
    }

    private PedidoDTO convertToDto(Pedido pedido) {
        PedidoDTO pedidoDTO = modelMapper.map(pedido, PedidoDTO.class);
        List<String> nomes = pedido.getLanches().stream()
                .map(Lanche::getNome)
                .collect(Collectors.toList());

        pedidoDTO.setNomesLanches(nomes);
        return pedidoDTO;
    }

    private Pedido convertToEntity(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        pedido.setStatus(pedidoDTO.getStatus());

        List<Lanche> lanches = pedidoDTO.getLanchesIds().stream()
                .map(id -> lancheRepository.findById(id)
                        .orElseThrow(() -> new LancheNaoEncontradoException(id)))
                .collect(Collectors.toList());

        pedido.setLanches(lanches);
        return pedido;
    }
}