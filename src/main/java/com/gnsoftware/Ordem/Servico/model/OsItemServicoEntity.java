package com.gnsoftware.Ordem.Servico.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "os_servico")
public class OsItemServicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ordem_servico_id")
    private OsEntity ordemServico;

    @ManyToOne
    @JoinColumn(name = "servico_id")
    private ServicoEntity servicoEntity;
  
    private int quantidade;
    private double preco;
    private double subTotalServico = 0;


    public OsItemServicoEntity(OsEntity ordemServico, ServicoEntity servicoEntity, int quantidade, double preco) {
        this.ordemServico = ordemServico;
        this.servicoEntity = servicoEntity;
        this.quantidade = quantidade;
        this.preco = preco;
        this.subTotalServico += subTotal();
    }

    public double subTotal() {
        return preco * quantidade;
    }
}
