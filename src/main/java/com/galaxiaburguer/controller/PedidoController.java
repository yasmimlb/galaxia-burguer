package com.galaxiaburguer.controller;

import com.galaxiaburguer.dto.PedidoDTO;
import com.galaxiaburguer.enums.StatusPedido;
import com.galaxiaburguer.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;


    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarTodos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> criar(@Valid @RequestBody PedidoDTO pedidoDTO) {
        if (pedidoDTO.getStatus() == null) {
            pedidoDTO.setStatus(StatusPedido.RECEBIDO);
        }
        return new ResponseEntity<>(pedidoService.criar(pedidoDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pedidoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusPedido status
    ) {
        return ResponseEntity.ok(pedidoService.atualizarStatus(id, status));
    }
}
