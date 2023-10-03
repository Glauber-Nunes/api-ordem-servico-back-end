package com.gnsoftware.Ordem.Servico.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tb_ncm")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CodigoNcm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    private String descricao;
}
