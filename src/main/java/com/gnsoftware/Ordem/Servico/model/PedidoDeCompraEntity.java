package com.gnsoftware.Ordem.Servico.model;

import com.gnsoftware.Ordem.Servico.model.compositekey.ItemPedidoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedido_de_compra")
public class PedidoDeCompraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private FornecedorEntity fornecedor;
    @OneToMany(mappedBy = "id.pedidoDeCompra", cascade = CascadeType.ALL)
    private List<ItemPedidoEntity> pedidoProdutos = new ArrayList<>();
    private Double totalPedido;

    public Double totalPedido() {

        double sumProdutos = 0;

        for (ItemPedidoEntity itemPedidoEntity : pedidoProdutos) {
            sumProdutos = sumProdutos + itemPedidoEntity.subTotal();
        }


        return sumProdutos;
    }
}
