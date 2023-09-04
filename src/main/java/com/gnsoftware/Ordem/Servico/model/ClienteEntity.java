package com.gnsoftware.Ordem.Servico.model;

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
}
