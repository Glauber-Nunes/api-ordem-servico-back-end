package com.gnsoftware.Ordem.Servico.services.auxiliar;

import com.gnsoftware.Ordem.Servico.dto.OrdemServicoDto;
import com.gnsoftware.Ordem.Servico.dto.ProdutoOrdemDto;
import com.gnsoftware.Ordem.Servico.dto.ServicoOrdemDto;
import com.gnsoftware.Ordem.Servico.model.*;
import com.gnsoftware.Ordem.Servico.model.compositekey.ProdutoOrdemEntity;
import com.gnsoftware.Ordem.Servico.model.compositekey.ServicoOrdemEntity;
import com.gnsoftware.Ordem.Servico.repository.*;
import com.gnsoftware.Ordem.Servico.services.EmailService;
import com.gnsoftware.Ordem.Servico.services.StatusOrdemServicoService;
import com.gnsoftware.Ordem.Servico.services.exceptions.DataIntegrityViolationException;
import com.gnsoftware.Ordem.Servico.services.exceptions.ModelNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Optional;

@Component
public class MapperObjectOsUpdate {
    @Autowired
    private com.gnsoftware.Ordem.Servico.repository.OSRepository OSRepository;

    @Autowired
    private StatusOrdemServicoService statusOrdemServicoService;

    @Autowired
    private EmailService emailService;
    @Autowired
    private ServicoRepository servicoRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private AtendenteRepository atendenteRepository;
    @Autowired
    private SituacaoOrdemRepository situacaoOrdemRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private FornecedorRepository fornecedorRepository;

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

        for (ProdutoOrdemDto itemProduto : ordemServicoDto.getProdutos()) {
            Optional<ProdutoEntity> prod = produtoRepository.findById(itemProduto.getProduto_id());
            prod.orElseThrow(() -> new ModelNotFound("Produto Not Found ID:"));

            // Verifique se o produto já existe na lista
            ProdutoOrdemEntity produtoExistente = this.findExistingProduct(osBancoUpdate, prod.get());

            if (produtoExistente != null) {
                // Produto já existe, atualize os valores
                produtoExistente.setQuantidade(itemProduto.getQuantidade());
                produtoExistente.setPreco(itemProduto.getPreco());
            } else {
                // Produto não existe, crie um novo
                ProdutoOrdemEntity itemProdutoBanco = new ProdutoOrdemEntity();
                itemProdutoBanco.getId().setProdutoEntity(itemProdutoBanco.getProdutoEntity());
                itemProdutoBanco.setProdutoEntity(prod.get());
                itemProdutoBanco.setQuantidade(itemProduto.getQuantidade());
                itemProdutoBanco.setPreco(itemProduto.getPreco());
                osBancoUpdate.getItemProdutoOs().add(itemProdutoBanco);
            }

            estoqueProdutoDisponivel(osBancoUpdate);
        }
    }

    private ProdutoOrdemEntity findExistingProduct(OrdemServicoEntity osBancoUpdate, ProdutoEntity produto) {
        // Iterar sobre a lista de produtos existentes na ordem de serviço
        for (ProdutoOrdemEntity itemProduto : osBancoUpdate.getItemProdutoOs()) {
            // Verificar se o produto existe com base no ID
            if (itemProduto.getProdutoEntity().getId().equals(produto.getId())) {
                return itemProduto; // Produto encontrado na lista existente
            }
        }
        return null; // Produto não encontrado na lista existente
    }

    private void atualizaServicoOrdemServico(OrdemServicoDto ordemServicoDto, OrdemServicoEntity osBancoUpdate) {

        for (ServicoOrdemDto itemServico : ordemServicoDto.getServicos()) {
            Optional<ServicoEntity> serv = servicoRepository.findById(itemServico.getServico_id());
            serv.orElseThrow(() -> new ModelNotFound("Produto Not Found ID:"));

            // Verifique se o servico já existe na lista
            ServicoOrdemEntity servicoExistente = this.findExistingServico(osBancoUpdate, serv.get());

            if (servicoExistente != null) {
                // servico já existe, atualize os valores
                servicoExistente.setQuantidade(itemServico.getQuantidade());
                servicoExistente.setPreco(serv.get().getPreco());
            } else {
                // Servico não existe, crie um novo
                ServicoOrdemEntity novoOsServico = new ServicoOrdemEntity();
                novoOsServico.getId().setOsEntity(osBancoUpdate);
                novoOsServico.setServicoEntity(serv.get());
                novoOsServico.setQuantidade(itemServico.getQuantidade());
                novoOsServico.setPreco(serv.get().getPreco());
                osBancoUpdate.getItemServicoOs().add(novoOsServico);
            }

        }
    }

    private ServicoOrdemEntity findExistingServico(OrdemServicoEntity os, ServicoEntity servico) {

        // Iterar sobre a lista de serviços existentes na ordem de serviço
        for (ServicoOrdemEntity iteOsServico : os.getItemServicoOs()) {
            //verficar se o produto existe com base no ID
            if (iteOsServico.getServicoEntity().getId().equals(servico.getId())) {
                return iteOsServico;
            }
        }
        return null; // Produto não encontrado na lista existente
    }


    private void salvarOsAtualizada(OrdemServicoEntity osBancoUpdate) {
        osBancoUpdate.setValorTotalOrdem(osBancoUpdate.totalOs());
        OSRepository.save(osBancoUpdate);
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
