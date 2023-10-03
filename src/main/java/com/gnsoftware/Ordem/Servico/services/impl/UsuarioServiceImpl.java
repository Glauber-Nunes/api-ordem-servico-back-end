package com.gnsoftware.Ordem.Servico.services.impl;

import com.gnsoftware.Ordem.Servico.dto.UsuarioDto;
import com.gnsoftware.Ordem.Servico.model.UsuarioEntity;
import com.gnsoftware.Ordem.Servico.model.enums.Perfil;
import com.gnsoftware.Ordem.Servico.repository.UsuarioRepository;
import com.gnsoftware.Ordem.Servico.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UsuarioDto save(UsuarioDto usuarioDto) {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(null);
        usuarioEntity.setSenha(usuarioDto.getSenha());
        usuarioEntity.setNome(usuarioDto.getNome());

        usuarioEntity.getPerfils().add(Perfil.ROLE_ADM); //automaticamente ja recebe a permissao de ADM

        usuarioRepository.save(usuarioEntity);

        return new UsuarioDto(usuarioEntity);
    }

    @Override
    public UsuarioDto update(Long id, UsuarioDto usuarioDto) {
        return null;
    }

    @Override
    public UsuarioDto findById(Long id) {
        return null;
    }

    @Override
    public List<UsuarioDto> findAll() {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
