package com.gnsoftware.Ordem.Servico.model;

import com.gnsoftware.Ordem.Servico.model.compositekey.ProdutoOrdemEntity;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Table(name = "venda")
public class VendaEntity {
    private Long id;
    private ClienteEntity cliente;
    private AtendenteEntity atendente;
    private List<ProdutoOrdemEntity> produtos = new ArrayList<>();
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
