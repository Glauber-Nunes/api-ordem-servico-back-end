package com.gnsoftware.Ordem.Servico.dto;

import com.gnsoftware.Ordem.Servico.model.AtendenteEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AtendenteDto {
    private Long id;
    @NotBlank(message = "NOME REQUERIDO")
    private String nome;
    @CPF
    @Column(unique = true)
    private String cpf;

    public AtendenteDto(AtendenteEntity entity) {

        if (entity != null) {
            this.id = entity.getId();
            this.nome = entity.getNome();
            this.cpf = entity.getCpf();
        }else {
            System.out.print("SEJA O QUE DEUS QUISER , SE FOR DA VONTADE DELE TUDO IRÁ DA CERTO, AMÉM");
        }

    }
}
