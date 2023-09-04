package com.gnsoftware.Ordem.Servico.services;

import com.gnsoftware.Ordem.Servico.dto.UsuarioDto;

import java.util.List;

public interface UsuarioService {
    UsuarioDto save(UsuarioDto usuarioDto);

    UsuarioDto update(Long id, UsuarioDto usuarioDto);

    UsuarioDto findById(Long id);

    List<UsuarioDto> findAll();

    void delete(Long id);
}
