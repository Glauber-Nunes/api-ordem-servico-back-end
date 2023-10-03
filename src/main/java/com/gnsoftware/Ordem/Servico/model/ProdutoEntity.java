package com.gnsoftware.Ordem.Servico.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @ManyToOne
    @JoinColumn(name = "codNcm_id")
    private CodigoNcm codigoNcm;
}
