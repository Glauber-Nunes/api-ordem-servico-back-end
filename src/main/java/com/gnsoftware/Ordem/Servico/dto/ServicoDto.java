package com.gnsoftware.Ordem.Servico.dto;

import com.gnsoftware.Ordem.Servico.model.OsEntity;
import com.gnsoftware.Ordem.Servico.model.ServicoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicoDto {

    private Long id;
    @NotBlank(message = "DESCRIÇÃO REQUERIDO")
    private String descricao;

    private double preco;

    private List<OsEntity> ordemServicoServicos = new ArrayList<>();

    public ServicoDto(ServicoEntity entity) {
        this.id = entity.getId();
        this.descricao = entity.getDescricao();
        this.preco = entity.getPreco();
    }
}
