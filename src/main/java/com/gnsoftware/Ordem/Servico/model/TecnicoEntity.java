package com.gnsoftware.Ordem.Servico.model;

import com.gnsoftware.Ordem.Servico.model.enums.Perfil;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tecnico")
public class TecnicoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(name = "Perfil")
    private Perfil perfil;

    public TecnicoEntity(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
