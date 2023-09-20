package com.gnsoftware.Ordem.Servico.model.compositekey;

import com.gnsoftware.Ordem.Servico.model.OrdemServicoEntity;
import com.gnsoftware.Ordem.Servico.model.Pks.ProdutoOrdemPK;
import com.gnsoftware.Ordem.Servico.model.ProdutoEntity;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "os_produto")
public class ProdutoOrdemEntity {

    @EmbeddedId
    private ProdutoOrdemPK id = new ProdutoOrdemPK();

    private Double quantidade;
    private Double preco;
    private double subTotalProduto = 0;

    public ProdutoOrdemEntity(OrdemServicoEntity ordemServico, ProdutoEntity produtoEntity, Double quantidade, Double preco) {
        this.id.setOsEntity(ordemServico);
        this.id.setProdutoEntity(produtoEntity);
        this.quantidade = quantidade;
        this.preco = preco;
        this.subTotalProduto += subTotal();
    }

    public OrdemServicoEntity getOsEntity() {
        return id.getOsEntity();
    }

    public void setOsEntity(OrdemServicoEntity ordemServicoEntity) {
        this.id.setOsEntity(ordemServicoEntity);
    }

    public ProdutoEntity getProdutoEntity() {
        return id.getProdutoEntity();
    }

    public void setProdutoEntity(ProdutoEntity produtoEntity) {
        this.id.setProdutoEntity(produtoEntity);
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
