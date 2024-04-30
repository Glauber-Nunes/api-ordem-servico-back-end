package com.gnsoftware.Ordem.Servico.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "produto")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProdutoEntity  {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_produto;
    @Column(unique = true)
    private String descricao;
    private Double preco;
    private String codeBarras;
    private String unEntrada;
    private String unSaida;
    private Double estoque;

    private String codigoNcm;

   // @Lob // Indica que este atributo será armazenado como um grande objeto no banco de dados
    //private byte[] imagem; // Atributo para armazenar a imagem do produto

    public Double subTotal() {
        return this.preco;
    }
}
