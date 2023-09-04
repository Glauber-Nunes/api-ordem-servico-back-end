package com.gnsoftware.Ordem.Servico.dto;

import com.gnsoftware.Ordem.Servico.model.PermissaoEntity;
import com.gnsoftware.Ordem.Servico.model.UsuarioEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissaoDto {

    private Long id;
    private String autorizacao;
    private List<UsuarioDto> usuarios = new ArrayList<>();

    public PermissaoDto(PermissaoEntity entity) {
        this.id = entity.getId();
        this.autorizacao = entity.getAutorizacao();
    }

}

