package com.gnsoftware.Ordem.Servico.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gnsoftware.Ordem.Servico.model.ClienteEntity;
import com.gnsoftware.Ordem.Servico.model.EnderecoEntity;
import com.gnsoftware.Ordem.Servico.model.TelefoneEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteDto {

    private Long id;
    @NotEmpty(message = "NOME REQUERIDO")
    private String nome;
    private String cpf;
    private String rg;
    private String email;
    private TelefoneDto telefone;
    private EnderecoDto endereco;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "America/Sao_Paulo")
    private Date dataCadastro;


    public ClienteDto(ClienteEntity entity, TelefoneEntity telefone, EnderecoEntity endereco) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.cpf = entity.getCpf();
        this.rg = entity.getRg();
        this.email = entity.getEmail();
        this.endereco = new EnderecoDto(endereco);
        this.telefone = new TelefoneDto(telefone);
        this.dataCadastro = entity.getDataCadastro();
    }

    public ClienteDto(ClienteEntity entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.cpf = entity.getCpf();
        this.rg = entity.getRg();
        this.email = entity.getEmail();
    }

}
