package com.galaxiaburguer.dto;

import com.galaxiaburguer.enums.StatusPedido;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private Long id;

    @NotNull(message = "Status é obrigatório")
    private StatusPedido status;

    private LocalDateTime dataHora;
    private List<Long> lanchesIds;
}