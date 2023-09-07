package com.gnsoftware.Ordem.Servico.dto;

import com.gnsoftware.Ordem.Servico.model.AtendenteEntity;
import com.gnsoftware.Ordem.Servico.model.enums.Perfil;
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

    private Perfil perfil;
    public AtendenteDto(AtendenteEntity entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.cpf = entity.getCpf();
        this.perfil = entity.getPerfil();
    }
}
