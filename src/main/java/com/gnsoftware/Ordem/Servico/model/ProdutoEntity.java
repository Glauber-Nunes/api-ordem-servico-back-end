package com.gnsoftware.Ordem.Servico.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "produto")
public class ProdutoEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String descricao;
    private Double preco;
    private String codeBarras;
    private String unEntrada;
    private String unSaida;
    private Double estoque;

}
