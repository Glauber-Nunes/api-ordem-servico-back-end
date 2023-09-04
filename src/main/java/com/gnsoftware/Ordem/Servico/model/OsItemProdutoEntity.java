package com.gnsoftware.Ordem.Servico.model;

import com.gnsoftware.Ordem.Servico.dto.OsItemProdutoDto;
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
@Table(name = "os_produto")
public class OsItemProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ordem_servico_id")
    private OsEntity ordemServico;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private ProdutoEntity produtoEntity;

    private double quantidade;
    private double preco;

    private double subTotalProduto = 0;

    public OsItemProdutoEntity(OsEntity ordemServico, ProdutoEntity produtoEntity, double quantidade, double preco) {
        this.ordemServico = ordemServico;
        this.produtoEntity = produtoEntity;
        this.quantidade = quantidade;
        this.preco = preco;
        this.subTotalProduto+=subTotal();
    }

    public double subTotal() {
        return preco * quantidade;
    }

}
