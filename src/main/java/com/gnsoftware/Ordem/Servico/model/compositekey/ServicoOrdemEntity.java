package com.gnsoftware.Ordem.Servico.model.compositekey;


import com.gnsoftware.Ordem.Servico.model.OrdemServicoEntity;
import com.gnsoftware.Ordem.Servico.model.Pks.ServicoOrdemPK;
import com.gnsoftware.Ordem.Servico.model.ServicoEntity;
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
@Table(name = "os_servico")
public class ServicoOrdemEntity {

    @EmbeddedId
    private ServicoOrdemPK id = new ServicoOrdemPK();

    private Double quantidade;
    private Double preco;
    private double subTotalServico = 0;

    public ServicoOrdemEntity(OrdemServicoEntity ordemServico, ServicoEntity servicoEntity, Double quantidade, Double preco) {
        this.id.setOsEntity(ordemServico);
        this.id.setServicoEntity(servicoEntity);
        this.quantidade = quantidade;
        this.preco = preco;
        this.subTotalServico += subTotal();
    }

    public OrdemServicoEntity getOsEntity() {
        return id.getOsEntity();
    }

    public void setOsEntity(OrdemServicoEntity ordemServicoEntity) {
        this.id.setOsEntity(ordemServicoEntity);
    }

    public ServicoEntity getServicoEntity() {
        return id.getServicoEntity();
    }
    public void setServicoEntity(ServicoEntity servicoEntity) {
        this.id.setServicoEntity(servicoEntity);
    }

    @PrePersist
    @PreUpdate
    private void calcularSubTotalServico() {
        this.subTotalServico = quantidade * preco;
    }

    public Double subTotal() {
        return quantidade * preco;
    }
}
