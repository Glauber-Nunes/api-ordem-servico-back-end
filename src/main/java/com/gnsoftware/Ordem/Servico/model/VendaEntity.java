package com.gnsoftware.Ordem.Servico.model;

import com.gnsoftware.Ordem.Servico.model.compositekey.ProdutoOrdemEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "venda")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class VendaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;
    @ManyToOne
    @JoinColumn(name = "atendente_id")
    private AtendenteEntity atendente;

    private List<ProdutoOrdemEntity> produtos = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "pagamento_id")
    private PagamentoEntity pagamento;
    private String observacoes;
    private Double totalVenda;

    // metodo de exemplo ira ser alterado no futuro para a classe ProdutoVendaItem classe de associaçao entre a venda
    // e o produto
    // ProdutoVendaItem que tera dados extras como quantidade preco etc...
    public Double calculaTotalVenda() {

        Double soma = 0.0;

        for (ProdutoOrdemEntity prod : produtos) {
            soma = soma + prod.subTotal();
        }

        return soma;
    }

}
