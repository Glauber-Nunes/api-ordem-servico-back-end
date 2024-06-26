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
    OSRepository OSRepository;

    @Autowired
    StatusOrdemServicoService statusOrdemServicoService;

    @Autowired
    MapperObjectOsUpdate mapperObjectOs;

    @Autowired
    MapperObjectOsSave mapperObjectOsSave;

    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ServicoRepository servicoRepository;
    @Autowired
    private EmailService emailService;


    @Override
    @Transactional
    public OrdemServicoDto save(OrdemServicoDto ordemServicoDto) {

        OrdemServicoEntity ordemServico = new OrdemServicoEntity();

        mapperObjectOsSave.mapperObjectSave(ordemServicoDto, ordemServico);
        return new OrdemServicoDto(ordemServico, ordemServico.getServicos(), ordemServico.getProdutos());

    }

    @Override
    @Transactional
    public OrdemServicoDto update(Long id_os, OrdemServicoDto ordemServicoDto) {

        Optional<OrdemServicoEntity> osBancoUpdate = OSRepository.findById(id_os);
        osBancoUpdate.orElseThrow(() -> new ModelNotFound("Ordem Serviço Not Found"));

        this.verificaSatusOsParaUpdate(osBancoUpdate.get());

        mapperObjectOs.mapperObjectUpdate(ordemServicoDto, osBancoUpdate.get());

        return new OrdemServicoDto(osBancoUpdate.get(), osBancoUpdate.get().getServicos(), osBancoUpdate.get().getProdutos());
    }

    @Override
    public OrdemServicoDto findById(Long id) {
        Optional<OrdemServicoEntity> ordemServico = OSRepository.findById(id);

        ordemServico.orElseThrow(() -> new ModelNotFound("Ordem De Serviço Não Encontrada"));

        return new OrdemServicoDto(ordemServico.get(), ordemServico.get().getServicos(), ordemServico.get().getProdutos());
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

        return entities.stream().map(osEntity -> new OrdemServicoDto(osEntity, osEntity.getServicos(), osEntity.getProdutos())).collect(Collectors.toList());
    }

    @Override
    public OrdemServicoEntity finalizaOs(Long id_os, OrdemServicoEntity ordemServico) {

        StatusOrdemServicoEntity statusENCERRADO = statusOrdemServicoService.findById(2L); //faz a busca automatica pelo status encerrado

        Optional<OrdemServicoEntity> osEntityBanco = OSRepository.findById(id_os);
        osEntityBanco.orElseThrow(() -> new ModelNotFound("Ordem De Serviço Não Encontrada"));

        if (osEntityBanco.get().getStatusOrdemServicoEntity() == statusENCERRADO) {
            throw new DataIntegrityViolationException("ORDEM DE SERVIÇO JA ESTA ENCERRADA");

        } else {
            osEntityBanco.get().setStatusOrdemServicoEntity(statusENCERRADO);
            osEntityBanco.get().setDataFechamento(new Date());
            OSRepository.saveAndFlush(osEntityBanco.get());
            emailService.enviarEmailServicoFinalizado(osEntityBanco.get()); // envia email serviço finalizado
        }

        return osEntityBanco.get();
    }

    @Override
    public Long countOrdemServico(){
        return OSRepository.countOrdemServico();
    }

    //CONSULTAR SE O ID DO STATUS DA OrdemServico É IGUAL A ENCERRADO, CASO FOR NÃO DEIXA O USUARIO EDITAR A OrdemServico
    private void verificaSatusOsParaUpdate(OrdemServicoEntity ordemServico) {

        StatusOrdemServicoEntity statusEncerrado = statusOrdemServicoService.findById(2L);

        if (ordemServico.getStatusOrdemServicoEntity().equals(statusEncerrado)) {
            throw new DataIntegrityViolationException("VOÇE NÃO PODE EDITAR UMA OS FINALIZADA");
        }
    }

    private void descontaEstoqueProduto(OrdemServicoEntity os) {

        for (ProdutoEntity produto : os.getProdutos()) {
            double quantidadeParaSubtrair = 1; // Obtém a quantidade a ser subtraída
            double estoqueAtual = produto.getEstoque(); // Obtém o estoque atual

            produto.setEstoque(estoqueAtual - quantidadeParaSubtrair);

            produtoRepository.save(produto);
        }

    }


}