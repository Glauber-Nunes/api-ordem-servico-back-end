package com.gnsoftware.Ordem.Servico.services;

import com.gnsoftware.Ordem.Servico.dto.OrdemServicoDto;
import com.gnsoftware.Ordem.Servico.model.ClienteEntity;
import com.gnsoftware.Ordem.Servico.model.OrdemServicoEntity;

public interface EmailService {

    void enviarEmailOSAberta(ClienteEntity clienteEntity, OrdemServicoEntity ordemServico);

    void enviarEmailServicoFinalizado(OrdemServicoEntity ordemServicoEntity);
}
