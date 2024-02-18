package com.gnsoftware.Ordem.Servico.model;

import com.gnsoftware.Ordem.Servico.model.enums.Perfil;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Builder
@Table(name = "tecnico")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TecnicoEntity {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;


    public TecnicoEntity(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
