package com.gnsoftware.Ordem.Servico.model;

import com.gnsoftware.Ordem.Servico.model.enums.Perfil;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente")
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cpf;
    private String rg;
    private String telefone;
    @Email(message = "Email INCORRETO")
    private String email;
    private String endereco;
    @Column(name = "Perfil")
    private Perfil perfil;

    public ClienteEntity(Long id, String nome, String cpf, String rg, String telefone, String email, String endereco) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
    }
}
