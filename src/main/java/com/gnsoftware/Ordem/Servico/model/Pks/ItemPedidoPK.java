package com.gnsoftware.Ordem.Servico.model.Pks;

import com.gnsoftware.Ordem.Servico.model.PedidoDeCompraEntity;
import com.gnsoftware.Ordem.Servico.model.ProdutoEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serial;
import java.io.Serializable;

// classe para servir de id
@Embeddable
@Getter
@Setter
public class ItemPedidoPK implements Serializable {
    @Serial
    private static final long serialVersionUID = 7133976691755621000L;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private PedidoDeCompraEntity pedidoDeCompra;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private ProdutoEntity produto;
}
