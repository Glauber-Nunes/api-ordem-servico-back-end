package com.gnsoftware.Ordem.Servico.services.auxiliar;

import com.gnsoftware.Ordem.Servico.dto.ItemPedidoDto;
import com.gnsoftware.Ordem.Servico.dto.OrdemServicoDto;
import com.gnsoftware.Ordem.Servico.dto.PedidoDeCompraDto;
import com.gnsoftware.Ordem.Servico.model.FornecedorEntity;
import com.gnsoftware.Ordem.Servico.model.OrdemServicoEntity;
import com.gnsoftware.Ordem.Servico.model.PedidoDeCompraEntity;
import com.gnsoftware.Ordem.Servico.model.ProdutoEntity;
import com.gnsoftware.Ordem.Servico.model.compositekey.ItemPedidoEntity;
import com.gnsoftware.Ordem.Servico.model.compositekey.ProdutoOrdemEntity;
import com.gnsoftware.Ordem.Servico.repository.FornecedorRepository;
import com.gnsoftware.Ordem.Servico.repository.PedidoDeCompraRepository;
import com.gnsoftware.Ordem.Servico.repository.ProdutoRepository;
import com.gnsoftware.Ordem.Servico.services.exceptions.DataIntegrityViolationException;
import com.gnsoftware.Ordem.Servico.services.exceptions.ModelNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MapperObjectPedidoSave {

    @Autowired
    private FornecedorRepository fornecedorRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private PedidoDeCompraRepository pedidoDeCompraRepository;

    public void mapperObjectSave(PedidoDeCompraDto dto, PedidoDeCompraEntity pedidoDeCompraEntity) {

        Optional<FornecedorEntity> fornecedor = fornecedorRepository.findById(dto.getFornecedor().getId());
        fornecedor.orElseThrow(() -> new ModelNotFound("Fornecedor Not Found"));

        pedidoDeCompraEntity.setFornecedor(fornecedor.get()); //adcionando fornecedor

        for (ItemPedidoDto itemPedidoDto : dto.getPedidoProdutos()) {
            Optional<ProdutoEntity> produto = produtoRepository.findById(itemPedidoDto.getProduto_id());
            produto.orElseThrow(() -> new ModelNotFound("Produto Not Found"));

            //instanciando itemPedido e passando seu constrtutor
            ItemPedidoEntity pedidoEntity = new ItemPedidoEntity(pedidoDeCompraEntity, produto.get(),
                    itemPedidoDto.getQuantidade(), produto.get().getPreco()
                    , itemPedidoDto.getLocalEntrega(), itemPedidoDto.getInfoComplementar());

            pedidoDeCompraEntity.getPedidoProdutos().add(pedidoEntity);

            this.estoqueProdutoDisponivel(pedidoDeCompraEntity);
        }

        pedidoDeCompraEntity.setTotalPedido(pedidoDeCompraEntity.totalPedido());

        pedidoDeCompraRepository.save(pedidoDeCompraEntity);
    }

    private void estoqueProdutoDisponivel(PedidoDeCompraEntity pedidoDeCompra) {

        for (ItemPedidoEntity itemPedido : pedidoDeCompra.getPedidoProdutos()) {

            if (itemPedido.getQuantidade() > itemPedido.getProdutoEntity().getEstoque()) {
                throw new DataIntegrityViolationException("Quantidade Em Estoque Insuficiente:" + " Produto " + itemPedido.getProdutoEntity().getDescricao() + ": Contem : " + itemPedido.getProdutoEntity().getEstoque() + " Em Estoque");
            }

        }

    }

}
