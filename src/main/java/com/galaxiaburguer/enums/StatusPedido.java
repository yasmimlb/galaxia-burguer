package com.galaxiaburguer.enums;

public enum StatusPedido {
    RECEBIDO("Recebido"),
    PREPARANDO("Preparando"),
    ENTREGUE("Entregue");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}