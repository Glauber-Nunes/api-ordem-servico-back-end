package com.gnsoftware.Ordem.Servico.services.impl;

import com.gnsoftware.Ordem.Servico.dto.OrdemServicoDto;
import com.gnsoftware.Ordem.Servico.model.*;
import com.gnsoftware.Ordem.Servico.model.compositekey.ProdutoOrdemEntity;
import com.gnsoftware.Ordem.Servico.model.compositekey.ServicoOrdemEntity;
import com.gnsoftware.Ordem.Servico.repository.*;
import com.gnsoftware.Ordem.Servico.services.*;
import com.gnsoftware.Ordem.Servico.services.auxiliar.MapperObjectOsSave;
import com.gnsoftware.Ordem.Servico.services.auxiliar.MapperObjectOsUpdate;
import com.gnsoftware.Ordem.Servico.services.exceptions.DataIntegrityViolationException;
import com.gnsoftware.Ordem.Servico.services.exceptions.ModelNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class OSServiceImpl implements OSService {

    @Autowired
    private OSRepository OSRepository;

    @Autowired
    private StatusOrdemServicoService statusOrdemServicoService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private MapperObjectOsUpdate mapperObjectOs;

    @Autowired
    private MapperObjectOsSave mapperObjectOsSave;

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ProdutoOrdemRepository produtoOrdemRepository;
    @Autowired
    private ServicoRepository servicoRepository;
    @Autowired
    ServicoOrdemRepository servicoOrdemRepository;

    @Override
    @Transactional
    public OrdemServicoDto save(OrdemServicoDto ordemServicoDto) {

        OrdemServicoEntity ordemServico = new OrdemServicoEntity();

        mapperObjectOsSave.mapperObjectSave(ordemServicoDto, ordemServico);

        return new OrdemServicoDto(ordemServico, ordemServico.getItemServicoOs(), ordemServico.getItemProdutoOs());

    }

    @Override
    @Transactional
    public OrdemServicoDto update(Long id_os, OrdemServicoDto ordemServicoDto) {

        Optional<OrdemServicoEntity> osBancoUpdate = OSRepository.findById(id_os);
        osBancoUpdate.orElseThrow(() -> new ModelNotFound("Ordem Serviço Not Found"));

        this.verificaSatusOsParaUpdate(osBancoUpdate.get());

        mapperObjectOs.mapperObjectUpdate(ordemServicoDto, osBancoUpdate.get());

        return new OrdemServicoDto(osBancoUpdate.get(), osBancoUpdate.get().getItemServicoOs(), osBancoUpdate.get().getItemProdutoOs());
    }

    @Override
    public OrdemServicoDto findById(Long id) {
        Optional<OrdemServicoEntity> ordemServico = OSRepository.findById(id);

        ordemServico.orElseThrow(() -> new ModelNotFound("Ordem De Serviço Não Encontrada"));

        return new OrdemServicoDto(ordemServico.get(), ordemServico.get().getItemServicoOs(), ordemServico.get().getItemProdutoOs());
    }


    @Override
    public void delete(Long id) {
        Optional<OrdemServicoEntity> ordemServico = OSRepository.findById(id);
        ordemServico.orElseThrow(() -> new ModelNotFound("Ordem De Serviço Não Encontrada"));

        OSRepository.delete(ordemServico.get());
    }

    @Override
    public List<OrdemServicoDto> findAll() {
        List<OrdemServicoEntity> entities = OSRepository.findAll();

        return entities.stream().map(osEntity -> new OrdemServicoDto(osEntity)).collect(Collectors.toList());
    }

    @Override
    public void finalizaOs(Long id_ordem_servico) {

        StatusOrdemServicoEntity statusENCERRADO = statusOrdemServicoService.findById(2L); //faz a busca automatica pelo status encerrado

        Optional<OrdemServicoEntity> osEntityBanco = OSRepository.findById(id_ordem_servico);
        osEntityBanco.orElseThrow(() -> new ModelNotFound("Ordem De Serviço Não Encontrada"));

        if (osEntityBanco.get().getStatusOrdemServicoEntity() == statusENCERRADO) {
            throw new DataIntegrityViolationException("ORDEM DE SERVIÇO JA ESTA ENCERRADA");

        } else {
            osEntityBanco.get().setStatusOrdemServicoEntity(statusENCERRADO);
            osEntityBanco.get().setDataFechamento(new Date());
            this.descontaEstoqueProduto(osEntityBanco.get());
            OSRepository.save(osEntityBanco.get());
            // emailService.enviarEmailServicoFinalizado(osEntity); // envia email serviço finalizado
        }

    }

    @Override
    @Transactional
    public OrdemServicoDto removeProdutoDaOrdemDeServico(Long id, Long id_produto) {

        Optional<OrdemServicoEntity> ordemServico = OSRepository.findById(id);
        ordemServico.orElseThrow(() -> new ModelNotFound("Ordem De Serviço Not Found"));

        Optional<ProdutoEntity> produto = produtoRepository.findById(id_produto);
        produto.orElseThrow(() -> new ModelNotFound("Produto Not Found"));

        for (ProdutoOrdemEntity produtoOrdemEntity : ordemServico.get().getItemProdutoOs()) {

            if (produtoOrdemEntity.getProdutoEntity().equals(produto.get())) {
                ordemServico.get().getItemProdutoOs().remove(produtoOrdemEntity);
                produtoOrdemRepository.delete(produtoOrdemEntity); // exclui relacionamneto do produto
                break;
            }
        }
        // Salva a ordem de serviço atualizada no banco de dados
        ordemServico.get().setValorTotalOrdem(ordemServico.get().totalOs());
        OSRepository.save(ordemServico.get());

        return new OrdemServicoDto(ordemServico.get(), ordemServico.get().getItemServicoOs(), ordemServico.get().getItemProdutoOs());
    }

    @Override
    @Transactional
    public OrdemServicoDto removeServicoDaOrdemDeServico(Long id, Long id_servico) {

        Optional<OrdemServicoEntity> ordemServico = OSRepository.findById(id);
        ordemServico.orElseThrow(() -> new ModelNotFound("OS Not Found"));

        Optional<ServicoEntity> servico = servicoRepository.findById(id_servico);
        servico.orElseThrow(() -> new ModelNotFound("Serviço Not Found"));

        for (ServicoOrdemEntity itemServico : ordemServico.get().getItemServicoOs()) {
            if (itemServico.getServicoEntity().equals(servico.get())) {
                ordemServico.get().getItemServicoOs().remove(itemServico);
                servicoOrdemRepository.delete(itemServico);
                break;
            }
        }
        ordemServico.get().setValorTotalOrdem(ordemServico.get().totalOs()); // atualiza total da ordem de servico
        OSRepository.save(ordemServico.get());
        return new OrdemServicoDto(ordemServico.get(), ordemServico.get().getItemServicoOs(), ordemServico.get().getItemProdutoOs());
    }

    //CONSULTAR SE O ID DO STATUS DA OrdemServico É IGUAL A ENCERRADO, CASO FOR NÃO DEIXA O USUARIO EDITAR A OrdemServico
    private void verificaSatusOsParaUpdate(OrdemServicoEntity ordemServico) {

        StatusOrdemServicoEntity statusEncerrado = statusOrdemServicoService.findById(2L);

        if (ordemServico.getStatusOrdemServicoEntity().equals(statusEncerrado)) {
            throw new DataIntegrityViolationException("VOÇE NÃO PODE EDITAR UMA OS FINALIZADA");
        }
    }

    private void descontaEstoqueProduto(OrdemServicoEntity os) {

        for (ProdutoOrdemEntity produto : os.getItemProdutoOs()) {
            double quantidadeParaSubtrair = produto.getQuantidade(); // Obtém a quantidade a ser subtraída
            double estoqueAtual = produto.getProdutoEntity().getEstoque(); // Obtém o estoque atual

            produto.getProdutoEntity().setEstoque(estoqueAtual - quantidadeParaSubtrair);

            produtoRepository.save(produto.getProdutoEntity());
        }

    }


}