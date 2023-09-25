package com.gnsoftware.Ordem.Servico.services.auxiliar;

import com.gnsoftware.Ordem.Servico.dto.OrdemServicoDto;
import com.gnsoftware.Ordem.Servico.dto.ProdutoOrdemDto;
import com.gnsoftware.Ordem.Servico.dto.ServicoOrdemDto;
import com.gnsoftware.Ordem.Servico.model.*;
import com.gnsoftware.Ordem.Servico.model.compositekey.ProdutoOrdemEntity;
import com.gnsoftware.Ordem.Servico.model.compositekey.ServicoOrdemEntity;
import com.gnsoftware.Ordem.Servico.repository.*;
import com.gnsoftware.Ordem.Servico.services.StatusOrdemServicoService;
import com.gnsoftware.Ordem.Servico.services.exceptions.DataIntegrityViolationException;
import com.gnsoftware.Ordem.Servico.services.exceptions.ModelNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MapperObjectOsUpdate {
    @Autowired
    com.gnsoftware.Ordem.Servico.repository.OSRepository OSRepository;

    @Autowired
    StatusOrdemServicoService statusOrdemServicoService;

    @Autowired
    ServicoRepository servicoRepository;
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    AtendenteRepository atendenteRepository;
    @Autowired
    SituacaoOrdemRepository situacaoOrdemRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    TecnicoRepository tecnicoRepository;
    @Autowired
    FornecedorRepository fornecedorRepository;

    public void mapperObjectUpdate(OrdemServicoDto ordemServicoDto, OrdemServicoEntity osBancoUpdate) {

        this.atualizaCabecalhoOrdemServico(ordemServicoDto, osBancoUpdate); // chama metodo
        this.atualizaProdutoOrdemServico(ordemServicoDto, osBancoUpdate);// chama metodo
        this.atualizaServicoOrdemServico(ordemServicoDto, osBancoUpdate);// chama metodo
        this.salvarOsAtualizada(osBancoUpdate);// chama metodo
    }

    private void atualizaCabecalhoOrdemServico(OrdemServicoDto ordemServicoDto, OrdemServicoEntity osBancoUpdate) {

        Optional<AtendenteEntity> atendente = atendenteRepository.findById(ordemServicoDto.getAtendente_id());
        atendente.orElseThrow(() -> new ModelNotFound("Atendente Not Found ID: " + ordemServicoDto.getAtendente_id()));

        Optional<SituacaoOrdemEntity> situacaoOrdem = situacaoOrdemRepository.findById(ordemServicoDto.getSituacaoOrdem_id());
        situacaoOrdem.orElseThrow(() -> new ModelNotFound("Situação Not Found ID: " + ordemServicoDto.getSituacaoOrdem_id()));

        Optional<ClienteEntity> cliente = clienteRepository.findById(ordemServicoDto.getCliente_id());
        cliente.orElseThrow(() -> new ModelNotFound("Cliente Not Found ID:" + ordemServicoDto.getCliente_id()));

        Optional<TecnicoEntity> tecnico = tecnicoRepository.findById(ordemServicoDto.getTecnico_id());
        tecnico.orElseThrow(() -> new ModelNotFound("Tecnico Not Found ID:" + ordemServicoDto.getTecnico_id()));

        Optional<FornecedorEntity> fornecedor = fornecedorRepository.findById(ordemServicoDto.getFornecedor_id());
        fornecedor.orElseThrow(() -> new ModelNotFound("Tecnico Not Found ID: " + ordemServicoDto.getFornecedor_id()));

        StatusOrdemServicoEntity statusOrdemServicoEntity = statusOrdemServicoService.findById(1L);

        osBancoUpdate.setAtendenteEntity(atendente.get());
        osBancoUpdate.setSituacaoOrdemEntity(situacaoOrdem.get());
        osBancoUpdate.setClienteEntity(cliente.get());
        osBancoUpdate.setDescricao(ordemServicoDto.getDescricao());
        osBancoUpdate.setTecnicoEntity(tecnico.get());
        osBancoUpdate.setDataDoServico(osBancoUpdate.getDataDoServico());
        osBancoUpdate.setFornecedorEntity(fornecedor.get());
        osBancoUpdate.setObservacoes(ordemServicoDto.getObservacoes());
        osBancoUpdate.setStatusOrdemServicoEntity(statusOrdemServicoEntity);
    }

    private void atualizaProdutoOrdemServico(OrdemServicoDto ordemServicoDto, OrdemServicoEntity osBancoUpdate) {

        for (ProdutoOrdemDto itemProdutos : ordemServicoDto.getProdutos()) {

            Optional<ProdutoEntity> produto = produtoRepository.findById(itemProdutos.getProduto_id());
            produto.orElseThrow(() -> new ModelNotFound("Produto Não Encontrado"));

            ProdutoOrdemEntity produtoExistente = this.encontraProdutoExistente(osBancoUpdate, produto.get());
            this.estoqueProdutoDisponivel(osBancoUpdate);

            //produto existe entao irei atualizar
            if (produtoExistente != null) {
                produtoExistente.setPreco(itemProdutos.getPreco() != null ? itemProdutos.getPreco() : produtoExistente.getPreco());
                produtoExistente.setQuantidade(itemProdutos.getQuantidade() != null ? itemProdutos.getQuantidade() : produtoExistente.getQuantidade());

            } else {
                //adciona um produto ja cadastrado no banco de dados a lista da associaçao produto e os
                ProdutoOrdemEntity produtoNovo = new ProdutoOrdemEntity();
                produtoNovo.setOsEntity(osBancoUpdate);
                produtoNovo.setProdutoEntity(produto.get());
                produtoNovo.setQuantidade(itemProdutos.getQuantidade() != null ? itemProdutos.getQuantidade() : 0);
                produtoNovo.setPreco(itemProdutos.getPreco() != null ? itemProdutos.getPreco() : 0);
                osBancoUpdate.getItemProdutoOs().add(produtoNovo);
            }
        }


    }

    private void atualizaServicoOrdemServico(OrdemServicoDto ordemServicoDto, OrdemServicoEntity osBancoUpdate) {

        for (ServicoOrdemDto itemServicos : ordemServicoDto.getServicos()) {
            Optional<ServicoEntity> servico = servicoRepository.findById(itemServicos.getServico_id());
            servico.orElseThrow(() -> new ModelNotFound("Produto Not Found ID:"));

            // Verifique se o servico já existe na lista
            ServicoOrdemEntity servicoExistente = this.encontraServicoExistente(osBancoUpdate, servico.get());

            // servico já existe, atualize os valores
            if (servicoExistente != null) {
                servicoExistente.setQuantidade(itemServicos.getQuantidade() != null ? itemServicos.getQuantidade() : servicoExistente.getQuantidade());
                servicoExistente.setPreco(servico.get().getPreco() != null ? itemServicos.getPreco() : servicoExistente.getPreco());
            } else {
                ServicoOrdemEntity servicoNovo = new ServicoOrdemEntity();
                servicoNovo.setOsEntity(osBancoUpdate);
                servicoNovo.setServicoEntity(servico.get());
                servicoNovo.setQuantidade(itemServicos.getQuantidade());
                servicoNovo.setPreco(itemServicos.getPreco());
                osBancoUpdate.getItemServicoOs().add(servicoNovo);
            }

        }
    }

    private void salvarOsAtualizada(OrdemServicoEntity osBancoUpdate) {
        osBancoUpdate.setValorTotalOrdem(osBancoUpdate.totalOs());
        OSRepository.save(osBancoUpdate);
    }

    private ProdutoOrdemEntity encontraProdutoExistente(OrdemServicoEntity osBancoUpdate, ProdutoEntity produto) {

        // Iterar sobre a lista de produtos existentes na ordem de serviço
        for (ProdutoOrdemEntity itemProduto : osBancoUpdate.getItemProdutoOs()) {

            // Verificar se o produto existe com base no ID
            if (itemProduto.getProdutoEntity().getId_produto().equals(produto.getId_produto())) {
                return itemProduto; // Produto encontrado na lista existente
            }
        }
        return null; // Produto não encontrado na lista existente
    }

    private ServicoOrdemEntity encontraServicoExistente(OrdemServicoEntity os, ServicoEntity servico) {

        // Iterar sobre a lista de serviços existentes na ordem de serviço
        for (ServicoOrdemEntity iteOsServico : os.getItemServicoOs()) {
            //verficar se o produto existe com base no ID
            if (iteOsServico.getServicoEntity().getId().equals(servico.getId())) {
                return iteOsServico;
            }
        }
        return null; // Produto não encontrado na lista existente
    }


    private void estoqueProdutoDisponivel(OrdemServicoEntity os) {

        for (ProdutoOrdemEntity itemProduto : os.getItemProdutoOs()) {

            if (itemProduto.getQuantidade() > itemProduto.getProdutoEntity().getEstoque()) {
                throw new DataIntegrityViolationException("Quantidade Em Estoque Insuficiente:" + " Produto " + itemProduto.getProdutoEntity().getDescricao() + ": Contem : " + itemProduto.getProdutoEntity().getEstoque() + " Em Estoque");
            }

        }

    }

    /*
      private <T> T getEntityByIdOrThrow(Long id, JpaRepository<T, Long> repository, String entityName) {
          Optional<T> entity = repository.findById(id);
          return entity.orElseThrow(() -> new ModelNotFound(entityName + " Not Found"));
      }
  */


}
