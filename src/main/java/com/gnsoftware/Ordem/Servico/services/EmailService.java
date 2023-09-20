package com.gnsoftware.Ordem.Servico.services;

import com.gnsoftware.Ordem.Servico.dto.OrdemServicoDto;
import com.gnsoftware.Ordem.Servico.model.ClienteEntity;
import com.gnsoftware.Ordem.Servico.model.OrdemServicoEntity;
import com.gnsoftware.Ordem.Servico.model.UsuarioEntity;

public interface EmailService {

    void enviarEmailBoasVindasUsuario(UsuarioEntity entity);

    void enviarEmailOSAberta(ClienteEntity clienteEntity, OrdemServicoDto ordemServicoDto);

    void enviarEmailServicoFinalizado(OrdemServicoEntity ordemServicoEntity);
}
