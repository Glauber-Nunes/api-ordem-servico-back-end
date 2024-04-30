package com.gnsoftware.Ordem.Servico.dto;

import com.gnsoftware.Ordem.Servico.model.ServicoEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServicoQuantidadeDto {

    private ServicoDto servico;
    private int quantidade;

    public ServicoQuantidadeDto(ServicoEntity servico, int quantidade) {
        this.servico = new ServicoDto(servico);
        this.quantidade = quantidade;
    }
}
