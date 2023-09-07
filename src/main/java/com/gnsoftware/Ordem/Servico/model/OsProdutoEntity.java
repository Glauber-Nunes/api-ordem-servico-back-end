package com.gnsoftware.Ordem.Servico.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "os_produto")
public class OsProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ordem_servico_id")
    private OsEntity ordemServico;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private ProdutoEntity produtoEntity;

    private Double quantidade;
    private Double preco;
    private double subTotalProduto = 0;

    public OsProdutoEntity(OsEntity ordemServico, ProdutoEntity produtoEntity, Double quantidade, Double preco) {
        this.ordemServico = ordemServico;
        this.produtoEntity = produtoEntity;
        this.quantidade = quantidade;
        this.preco = preco;
        this.subTotalProduto += subTotal();
    }

    @PrePersist
    @PreUpdate
    private void calcularSubTotalProduto() {
        this.subTotalProduto = quantidade * preco;
    }
    public Double subTotal() {
        return quantidade * preco;
    }

}
