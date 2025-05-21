package com.galaxiaburguer.service;

import com.galaxiaburguer.dto.LancheDTO;
import com.galaxiaburguer.exception.LancheNaoEncontradoException;
import com.galaxiaburguer.model.Lanche;
import com.galaxiaburguer.repository.LancheRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LancheService {

    @Autowired
    private LancheRepository lancheRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<LancheDTO> listarTodos() {
        return lancheRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public LancheDTO buscarPorId(Long id) {
        Lanche lanche = lancheRepository.findById(id)
                .orElseThrow(() -> new LancheNaoEncontradoException(id));
        return convertToDto(lanche);
    }

    public LancheDTO criar(LancheDTO lancheDTO) {
        Lanche lanche = convertToEntity(lancheDTO);
        Lanche salvo = lancheRepository.save(lanche);
        return convertToDto(salvo);
    }

    public LancheDTO atualizar(Long id, LancheDTO lancheDTO) {
        Lanche lancheExistente = lancheRepository.findById(id)
                .orElseThrow(() -> new LancheNaoEncontradoException(id));

        modelMapper.map(lancheDTO, lancheExistente);
        Lanche atualizado = lancheRepository.save(lancheExistente);
        return convertToDto(atualizado);
    }

    public void deletar(Long id) {
        if (!lancheRepository.existsById(id)) {
            throw new LancheNaoEncontradoException(id);
        }
        lancheRepository.deleteById(id);
    }

    private LancheDTO convertToDto(Lanche lanche) {
        return modelMapper.map(lanche, LancheDTO.class);
    }

    private Lanche convertToEntity(LancheDTO lancheDTO) {
        return modelMapper.map(lancheDTO, Lanche.class);
    }
}