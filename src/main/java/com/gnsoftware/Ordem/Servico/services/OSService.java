package com.gnsoftware.Ordem.Servico.services;

import com.gnsoftware.Ordem.Servico.dto.OrdemServicoDto;

import java.util.List;

public interface OSService {

    OrdemServicoDto save(OrdemServicoDto OrdemServicoDto);

    OrdemServicoDto update(Long id, OrdemServicoDto OrdemServicoDto);

    OrdemServicoDto findById(Long id);

    void delete(Long id);

    List<OrdemServicoDto> findAll();

    void finalizaOs(Long id);

    OrdemServicoDto removeProdutoDaOrdemDeServico(Long id, Long id_produto);

    OrdemServicoDto removeServicoDaOrdemDeServico(Long id, Long id_servico);

}
