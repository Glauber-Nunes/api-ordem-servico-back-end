package com.gnsoftware.Ordem.Servico.services;

import com.gnsoftware.Ordem.Servico.dto.OsDto;
import com.gnsoftware.Ordem.Servico.model.ClienteEntity;
import com.gnsoftware.Ordem.Servico.model.OsEntity;
import com.gnsoftware.Ordem.Servico.model.UsuarioEntity;

public interface EmailService {

    void enviarEmailBoasVindasUsuario(UsuarioEntity entity);

    void enviarEmailOSAberta(ClienteEntity clienteEntity, OsDto osDto);

    void enviarEmailServicoFinalizado(OsEntity osEntity);
}
