package com.gnsoftware.Ordem.Servico.services.impl;

import com.gnsoftware.Ordem.Servico.model.UsuarioEntity;
import com.gnsoftware.Ordem.Servico.repository.UsuarioRepository;
import com.gnsoftware.Ordem.Servico.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<UsuarioEntity> usuario = usuarioRepository.findByEmail(email);
        usuario.orElseThrow(() -> new UsernameNotFoundException(email));

        return new UserSpringSecurity(usuario.get().getId(), usuario.get().getNome(),
                usuario.get().getEmail(), usuario.get().getSenha(), usuario.get().getPerfils());
    }
}