package com.gnsoftware.Ordem.Servico.model;

import com.gnsoftware.Ordem.Servico.model.enums.Perfil;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "atendente")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AtendenteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String nome;
    @CPF
    private String cpf;

    @OneToMany(mappedBy = "atendenteEntity")
    private List<OrdemServicoEntity> ordemServicos;

    public AtendenteEntity(Long id, String nome, String cpf) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
    }
}
