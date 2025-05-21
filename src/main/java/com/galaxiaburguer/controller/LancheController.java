package com.galaxiaburguer.controller;

import com.galaxiaburguer.dto.LancheDTO;
import com.galaxiaburguer.service.LancheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lanches")
@CrossOrigin(origins = "*") // Permite conexão com o frontend
public class LancheController {

    @Autowired
    private LancheService lancheService;

    // Lista todos os lanches (GET /api/lanches)
    @GetMapping
    public ResponseEntity<List<LancheDTO>> listarTodos() {
        return ResponseEntity.ok(lancheService.listarTodos());
    }

    // Busca um lanche por ID (GET /api/lanches/{id})
    @GetMapping("/{id}")
    public ResponseEntity<LancheDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(lancheService.buscarPorId(id));
    }

    // Cria um novo lanche (POST /api/lanches)
    @PostMapping
    public ResponseEntity<LancheDTO> criar(@RequestBody LancheDTO lancheDTO) {
        return new ResponseEntity<>(lancheService.criar(lancheDTO), HttpStatus.CREATED);
    }

    // Atualiza um lanche (PUT /api/lanches/{id})
    @PutMapping("/{id}")
    public ResponseEntity<LancheDTO> atualizar(
            @PathVariable Long id,
            @RequestBody LancheDTO lancheDTO
    ) {
        return ResponseEntity.ok(lancheService.atualizar(id, lancheDTO));
    }

    // Deleta um lanche (DELETE /api/lanches/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        lancheService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}