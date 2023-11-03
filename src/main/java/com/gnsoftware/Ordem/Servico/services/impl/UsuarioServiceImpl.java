package com.gnsoftware.Ordem.Servico.services.impl;

import com.gnsoftware.Ordem.Servico.dto.UsuarioDto;
import com.gnsoftware.Ordem.Servico.model.UsuarioEntity;
import com.gnsoftware.Ordem.Servico.model.enums.Perfil;
import com.gnsoftware.Ordem.Servico.repository.UsuarioRepository;
import com.gnsoftware.Ordem.Servico.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UsuarioDto save(UsuarioDto usuarioDto) {

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(null);
        usuarioEntity.setNome(usuarioDto.getNome());
        usuarioEntity.setEmail(usuarioDto.getEmail());
        usuarioEntity.setSenha(passwordEncoder.encode(usuarioDto.getSenha()));

        usuarioEntity.getPerfils().add(Perfil.ROLE_ADM); //automaticamente ja recebe a permissao de ADM

        usuarioRepository.save(usuarioEntity);

        return new UsuarioDto(usuarioEntity);
    }

}
