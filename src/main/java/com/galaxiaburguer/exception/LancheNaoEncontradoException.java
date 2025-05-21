package com.galaxiaburguer.exception;

public class LancheNaoEncontradoException extends RuntimeException {
    public LancheNaoEncontradoException(Long id) {
        super("Lanche não encontrado com ID: " + id);
    }
}