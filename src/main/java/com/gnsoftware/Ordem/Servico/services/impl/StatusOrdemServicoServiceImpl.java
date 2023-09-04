package com.gnsoftware.Ordem.Servico.services.impl;

import com.gnsoftware.Ordem.Servico.model.StatusOrdemServicoEntity;
import com.gnsoftware.Ordem.Servico.repository.StatusOrdemServicoRepository;
import com.gnsoftware.Ordem.Servico.services.StatusOrdemServicoService;
import com.gnsoftware.Ordem.Servico.services.exceptions.ModelNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusOrdemServicoServiceImpl implements StatusOrdemServicoService {

    @Autowired
    private StatusOrdemServicoRepository statusOrdemServicoRepository;

    @Override
    public StatusOrdemServicoEntity findById(Long id) {
        Optional<StatusOrdemServicoEntity> statusOrdemServico = statusOrdemServicoRepository.findById(id);

        return statusOrdemServico.orElseThrow(() -> new ModelNotFound("Not Found"));
    }

    @Override
    public List<StatusOrdemServicoEntity> findAll() {
        return statusOrdemServicoRepository.findAll();
    }

    @Override
    public StatusOrdemServicoEntity update(Long id, StatusOrdemServicoEntity statusOrdemServicoEntity) {

        StatusOrdemServicoEntity statusBanco = this.findById(id);

        StatusOrdemServicoEntity statusOrdemServicoEntity1 = StatusOrdemServicoEntity.builder()
                .id(statusBanco.getId())
                .nome(statusOrdemServicoEntity.getNome())
                .build();

        return statusOrdemServicoRepository.save(statusOrdemServicoEntity1);
    }
}
