package com.gnsoftware.Ordem.Servico.dto;

import com.gnsoftware.Ordem.Servico.model.ProdutoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDto {

    private Long id;
    @NotBlank(message = "DESCRIÇÃO REQUERIDO")
    private String descricao;

    private double preco;
    private String codeBarras;
    private String unEntrada;
    private String unSaida;
    private Double estoque;
    private String codigoNcm;
    private byte[] imagem;

    public ProdutoDto(ProdutoEntity produtoEntity) {
        this.id = produtoEntity.getId_produto();
        this.descricao = produtoEntity.getDescricao();
        this.preco = produtoEntity.getPreco();
        this.codeBarras = produtoEntity.getCodeBarras();
        this.unEntrada = produtoEntity.getUnEntrada();
        this.unSaida = produtoEntity.getUnSaida();
        this.estoque = produtoEntity.getEstoque();
        this.codigoNcm = produtoEntity.getCodigoNcm();
        //this.imagem = produtoEntity.getImagem();
    }
}
