package com.gnsoftware.Ordem.Servico.services.auxiliar;

import com.gnsoftware.Ordem.Servico.dto.OsDto;
import com.gnsoftware.Ordem.Servico.dto.OsProdutoDto;
import com.gnsoftware.Ordem.Servico.dto.OsServicoDto;
import com.gnsoftware.Ordem.Servico.model.*;
import com.gnsoftware.Ordem.Servico.repository.*;
import com.gnsoftware.Ordem.Servico.services.EmailService;
import com.gnsoftware.Ordem.Servico.services.StatusOrdemServicoService;
import com.gnsoftware.Ordem.Servico.services.exceptions.DataIntegrityViolationException;
import com.gnsoftware.Ordem.Servico.services.exceptions.ModelNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


import java.util.Date;

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
    @Autowired
    private OsItemProdutoEntityRepository osItemProdutoEntityRepository;

    public void mapperObjectUpdate(OsDto osDto, OsEntity osBancoUpdate, OsProdutoEntity osItemProdutoBanco, OsServicoEntity osItemServicoBanco) {
        this.atualizarDadosBasicosOs(osDto, osBancoUpdate); // chama metodo
        this.atualizarOsProdutos(osDto, osBancoUpdate);// chama metodo
        this.atualizarOsServico(osDto, osBancoUpdate);// chama metodo
        this.atualizarValorTotalOs(osBancoUpdate);// chama metodo
    }

    private void atualizarDadosBasicosOs(OsDto osDto, OsEntity osBancoUpdate) {
        AtendenteEntity atendente = getEntityByIdOrThrow(osDto.getAtendente_id(), atendenteRepository, "Atendente");
        SituacaoOrdemEntity situacaoOrdem = getEntityByIdOrThrow(osDto.getSituacaoOrdem_id(), situacaoOrdemRepository, "Situação");
        ClienteEntity cliente = getEntityByIdOrThrow(osDto.getCliente_id(), clienteRepository, "Cliente");
        TecnicoEntity tecnico = getEntityByIdOrThrow(osDto.getTecnico_id(), tecnicoRepository, "Técnico");
        FornecedorEntity fornecedor = getEntityByIdOrThrow(osDto.getFornecedor_id(), fornecedorRepository, "Fornecedor");
        StatusOrdemServicoEntity statusOrdemServicoEntity = statusOrdemServicoService.findById(1L);

        osBancoUpdate.setAtendenteEntity(atendente);
        osBancoUpdate.setSituacaoOrdemEntity(situacaoOrdem);
        osBancoUpdate.setClienteEntity(cliente);
        osBancoUpdate.setDescricao(osDto.getDescricao());
        osBancoUpdate.setTecnicoEntity(tecnico);
        osBancoUpdate.setDataDoServico(new Date());
        osBancoUpdate.setFornecedorEntity(fornecedor);
        osBancoUpdate.setObservacoes(osDto.getObservacoes());
        osBancoUpdate.setStatusOrdemServicoEntity(statusOrdemServicoEntity);
    }

    private void atualizarOsProdutos(OsDto osDto, OsEntity osBancoUpdate) {

        for (OsProdutoDto itemProduto : osDto.getProdutos()) {
            Optional<ProdutoEntity> prod = produtoRepository.findById(itemProduto.getProduto_id());
            prod.orElseThrow(() -> new ModelNotFound("Produto Not Found ID:" + itemProduto.getProduto_id()));

            // Verifique se o produto já existe na lista
            OsProdutoEntity produtoExistente = this.findExistingProduct(osBancoUpdate, prod.get());

            if (produtoExistente != null) {
                // Produto já existe, atualize os valores
                produtoExistente.setQuantidade(itemProduto.getQuantidade());
                produtoExistente.setPreco(itemProduto.getPreco());
            } else {
                // Produto não existe, crie um novo
                OsProdutoEntity itemProdutoBanco = new OsProdutoEntity();
                itemProdutoBanco.setOrdemServico(osBancoUpdate);
                itemProdutoBanco.setProdutoEntity(prod.get());
                itemProdutoBanco.setQuantidade(itemProduto.getQuantidade());
                itemProdutoBanco.setPreco(itemProduto.getPreco());
                osBancoUpdate.getItemProdutoOs().add(itemProdutoBanco);
            }

            estoqueProdutoDisponivel(osBancoUpdate);
        }
    }

    private OsProdutoEntity findExistingProduct(OsEntity osBancoUpdate, ProdutoEntity produto) {
        // Iterar sobre a lista de produtos existentes na ordem de serviço
        for (OsProdutoEntity itemProduto : osBancoUpdate.getItemProdutoOs()) {
            // Verificar se o produto existe com base no ID
            if (itemProduto.getProdutoEntity().getId().equals(produto.getId())) {
                return itemProduto; // Produto encontrado na lista existente
            }
        }
        return null; // Produto não encontrado na lista existente
    }

    private void atualizarOsServico(OsDto osDto, OsEntity osBancoUpdate) {

        for (OsServicoDto itemServico : osDto.getServicos()) {
            Optional<ServicoEntity> serv = servicoRepository.findById(itemServico.getServico_id());
            serv.orElseThrow(() -> new ModelNotFound("Produto Not Found ID:" + itemServico.getServico_id()));

            // Verifique se o servico já existe na lista
            OsServicoEntity servicoExistente = this.findExistingServico(osBancoUpdate, serv.get());

            if (servicoExistente != null) {
                // servico já existe, atualize os valores
                servicoExistente.setQuantidade(itemServico.getQuantidade());
                servicoExistente.setPreco(serv.get().getPreco());
            } else {
                // Servico não existe, crie um novo
                OsServicoEntity novoOsServico = new OsServicoEntity();
                novoOsServico.setOrdemServico(osBancoUpdate);
                novoOsServico.setServicoEntity(serv.get());
                novoOsServico.setQuantidade(itemServico.getQuantidade());
                novoOsServico.setPreco(serv.get().getPreco());
                osBancoUpdate.getItemServicoOs().add(novoOsServico);
            }

        }
    }

    private OsServicoEntity findExistingServico(OsEntity os, ServicoEntity servico) {

        // Iterar sobre a lista de serviços existentes na ordem de serviço
        for (OsServicoEntity iteOsServico : os.getItemServicoOs()) {
            //verficar se o produto existe com base no ID
            if (iteOsServico.getServicoEntity().getId().equals(servico.getId())) {
                return iteOsServico;
            }
        }
        return null; // Produto não encontrado na lista existente
    }


    private void atualizarValorTotalOs(OsEntity osBancoUpdate) {

        osBancoUpdate.setValorTotalOrdem(osBancoUpdate.totalOs());
        OSRepository.save(osBancoUpdate);
    }

    // remover em breve das pesquisas do objeto
    private <T> T getEntityByIdOrThrow(Long id, JpaRepository<T, Long> repository, String entityName) {
        Optional<T> entity = repository.findById(id);
        return entity.orElseThrow(() -> new ModelNotFound(entityName + " Not Found"));
    }

    private void estoqueProdutoDisponivel(OsEntity os) {

        for (OsProdutoEntity itemProduto : os.getItemProdutoOs()) {

            if (itemProduto.getQuantidade() > itemProduto.getProdutoEntity().getEstoque()) {
                throw new DataIntegrityViolationException("Quantidade Em Estoque Insuficiente:" + " Produto " + itemProduto.getProdutoEntity().getDescricao() + ": Contem : " + itemProduto.getProdutoEntity().getEstoque() + " Em Estoque");
            }

        }

    }

}
