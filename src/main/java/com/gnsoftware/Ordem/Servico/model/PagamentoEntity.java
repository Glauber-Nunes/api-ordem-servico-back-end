package com.gnsoftware.Ordem.Servico.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "pagamento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private Double quantia;
}
