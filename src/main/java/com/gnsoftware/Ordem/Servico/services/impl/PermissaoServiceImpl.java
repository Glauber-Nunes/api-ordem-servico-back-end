package com.gnsoftware.Ordem.Servico.services.impl;

import com.gnsoftware.Ordem.Servico.dto.PermissaoDto;
import com.gnsoftware.Ordem.Servico.model.PermissaoEntity;
import com.gnsoftware.Ordem.Servico.repository.PermissaoRepository;
import com.gnsoftware.Ordem.Servico.services.PermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissaoServiceImpl implements PermissaoService {

    @Autowired
    PermissaoRepository permissaoRepository;

    @Override
    public PermissaoDto save(PermissaoDto permissaoDto) {
        PermissaoEntity permissao = new PermissaoEntity();
        permissao.setAutorizacao(permissaoDto.getAutorizacao());
        permissaoRepository.save(permissao);
        return new PermissaoDto(permissao);
    }
}
