package com.gnsoftware.Ordem.Servico.model.compositekey;


import com.gnsoftware.Ordem.Servico.model.PedidoDeCompraEntity;
import com.gnsoftware.Ordem.Servico.model.Pks.ItemPedidoPK;
import com.gnsoftware.Ordem.Servico.model.ProdutoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedido_produto")
@Getter
@Setter
public class ItemPedidoEntity implements Serializable {
    @EmbeddedId
    private ItemPedidoPK id = new ItemPedidoPK();

    private Double quantidade;
    private Double preco;
    private String localEntrega;
    private String infoComplementar;
    private double subTotalPedido = 0;

    public ItemPedidoEntity(PedidoDeCompraEntity pedidoDeCompra, ProdutoEntity produto, Double quantidade, Double preco, String localEntrega, String infoComplementar) {
        this.id.setPedidoDeCompra(pedidoDeCompra);
        this.id.setProduto(produto);
        this.quantidade = quantidade;
        this.preco = preco;
        this.localEntrega = localEntrega;
        this.infoComplementar = infoComplementar;
        this.subTotalPedido += subTotal();
    }

    @PrePersist
    @PreUpdate
    private void calcularSubTotalProduto() {
        this.subTotalPedido = quantidade * preco;
    }

    public Double subTotal() {
        return quantidade * preco;
    }

    public ProdutoEntity getProdutoEntity() {
        return id.getProduto();
    }

    public void setProduto(ProdutoEntity produto) {
        this.id.setProduto(produto);
    }

    public PedidoDeCompraEntity getPedidoDeCompraEntity() {
        return id.getPedidoDeCompra();
    }

    public void setPedidoDeCompra(PedidoDeCompraEntity pedidoDeCompra) {
        this.id.setPedidoDeCompra(pedidoDeCompra);
    }
}
