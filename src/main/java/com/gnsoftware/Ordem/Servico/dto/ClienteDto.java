package com.gnsoftware.Ordem.Servico.dto;

import com.gnsoftware.Ordem.Servico.model.ClienteEntity;
import com.gnsoftware.Ordem.Servico.model.EnderecoEntity;
import com.gnsoftware.Ordem.Servico.model.OrdemServicoEntity;
import com.gnsoftware.Ordem.Servico.model.TelefoneEntity;
import com.gnsoftware.Ordem.Servico.model.enums.Perfil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

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
    private Perfil perfil;


    public ClienteDto(ClienteEntity entity, TelefoneEntity telefone, EnderecoEntity endereco) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.cpf = entity.getCpf();
        this.rg = entity.getRg();
        this.email = entity.getEmail();
        this.endereco = new EnderecoDto(endereco);
        this.telefone = new TelefoneDto(telefone);
    }

}
