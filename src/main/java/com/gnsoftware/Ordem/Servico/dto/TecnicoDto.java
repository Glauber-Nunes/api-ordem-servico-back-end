package com.gnsoftware.Ordem.Servico.dto;

import com.gnsoftware.Ordem.Servico.model.TecnicoEntity;
import com.gnsoftware.Ordem.Servico.model.enums.Perfil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TecnicoDto {

    private Long id;
    @NotBlank(message = "NOME REQUERIDO")
    private String nome;
    private Perfil perfil;

    public TecnicoDto(TecnicoEntity entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.perfil = entity.getPerfil();
    }
}
