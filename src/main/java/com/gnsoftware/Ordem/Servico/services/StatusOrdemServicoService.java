package com.gnsoftware.Ordem.Servico.services;

import com.gnsoftware.Ordem.Servico.model.StatusOrdemServicoEntity;

import java.util.List;

public interface StatusOrdemServicoService {
    StatusOrdemServicoEntity findById(Long id);
    List<StatusOrdemServicoEntity> findAll();
    StatusOrdemServicoEntity update(Long id, StatusOrdemServicoEntity statusOrdemServicoEntity);
}
