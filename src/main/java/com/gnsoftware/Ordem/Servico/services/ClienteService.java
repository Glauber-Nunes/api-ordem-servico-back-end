package com.gnsoftware.Ordem.Servico.services;

import com.gnsoftware.Ordem.Servico.dto.ClienteDto;
import com.gnsoftware.Ordem.Servico.dto.OrdemServicoDto;
import com.gnsoftware.Ordem.Servico.model.ClienteEntity;
import com.gnsoftware.Ordem.Servico.model.OrdemServicoEntity;
import com.gnsoftware.Ordem.Servico.services.exceptions.DataIntegrityViolationException;

import java.util.List;

public interface ClienteService {
    ClienteDto save(ClienteDto clienteDto);

    ClienteDto update(Long id, ClienteDto clienteDto);

    ClienteDto findById(Long id);

    List<ClienteDto> findAll();

    void delete(Long id);

    Long countCliente();

}
