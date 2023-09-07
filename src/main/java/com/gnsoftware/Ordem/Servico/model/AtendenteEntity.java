package com.gnsoftware.Ordem.Servico.model;

import com.gnsoftware.Ordem.Servico.model.enums.Perfil;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "atendente")
public class AtendenteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    @CPF
    private String cpf;
    @Column(name = "Perfil")
    private Perfil perfil;

    public AtendenteEntity(Long id, String nome, String cpf) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
    }
}
