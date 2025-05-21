package com.galaxiaburguer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lanche {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser positivo")
    private Double preco;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 10, max = 255, message = "Descrição deve ter entre 10 e 255 caracteres")
    private String descricao;

    @NotBlank(message = "URL da imagem é obrigatória")
    @Size(max = 255, message = "URL muito longa")
    private String imagemUrl;
}