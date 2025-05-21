package com.galaxiaburguer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.galaxiaburguer.enums.StatusPedido;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Status é obrigatório")
    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    private LocalDateTime dataHora;

    @ManyToMany
    @JoinTable(
            name = "pedido_lanche",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "lanche_id")
    )
    private List<Lanche> lanches;

    @PrePersist
    protected void onCreate() {
        dataHora = LocalDateTime.now();
        if (status == null) {
            status = StatusPedido.RECEBIDO;
        }
    }
}