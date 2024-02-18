package com.gnsoftware.Ordem.Servico.services.auxiliar;

import com.gnsoftware.Ordem.Servico.dto.*;
import com.gnsoftware.Ordem.Servico.model.*;
import com.gnsoftware.Ordem.Servico.repository.*;
import com.gnsoftware.Ordem.Servico.services.EmailService;
import com.gnsoftware.Ordem.Servico.services.StatusOrdemServicoService;
import com.gnsoftware.Ordem.Servico.services.exceptions.ModelNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Optional;

@Component
public class MapperObjectOsSave {
    @Autowired
    private OSRepository osRepository;

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


    public void mapperObjectSave(OrdemServicoDto ordemServicoDto, OrdemServicoEntity ordemServicoEntity) {

        this.salvaCabecalhoOrdemServico(ordemServicoDto, ordemServicoEntity);
        this.salvaServicoOrdemServico(ordemServicoDto, ordemServicoEntity);
        this.salvaProdutoOrdemServico(ordemServicoDto, ordemServicoEntity);
        this.calculaValorTotalSalvaOrdemServico(ordemServicoEntity, ordemServicoDto);
        emailService.enviarEmailOSAberta(ordemServicoEntity.getClienteEntity(), ordemServicoEntity); //envia email para o clienteEntity
    }

    private void salvaCabecalhoOrdemServico(OrdemServicoDto dto, OrdemServicoEntity ordemServicoEntity) {

        if (dto.getAtendente().getId() == null){
            ordemServicoEntity.setAtendenteEntity(null);
        }else{
            Optional<AtendenteEntity> atendente = atendenteRepository.findById(dto.getAtendente().getId());
            atendente.orElseThrow(() -> new ModelNotFound("Atendente Not Found ID:"));
            ordemServicoEntity.setAtendenteEntity(atendente.get());
        }

        Optional<SituacaoOrdemEntity> situacaoOrdem = situacaoOrdemRepository.findById(dto.getSituacaoOrdem().getId());
        situacaoOrdem.orElseThrow(() -> new ModelNotFound("Situação Not Found ID:"));

        Optional<ClienteEntity> cliente = clienteRepository.findById(dto.getCliente().getId());
        cliente.orElseThrow(() -> new ModelNotFound("Cliente Not Found ID:" ));

        Optional<TecnicoEntity> tecnico = tecnicoRepository.findById(dto.getTecnico().getId());
        tecnico.orElseThrow(() -> new ModelNotFound("Tecnico Not Found ID:" ));

        StatusOrdemServicoEntity statusOrdemServicoEntity = statusOrdemServicoService.findById(1L); // recebe automatico


        ordemServicoEntity.setSituacaoOrdemEntity(situacaoOrdem.get());
        ordemServicoEntity.setClienteEntity(cliente.get());
        ordemServicoEntity.setTecnicoEntity(tecnico.get());
        ordemServicoEntity.setDataDoServico(new Date());
        ordemServicoEntity.setObservacoes(dto.getObservacoes());
        ordemServicoEntity.setStatusOrdemServicoEntity(statusOrdemServicoEntity);
        ordemServicoEntity.setProtocolo(this.gerarProtocolo());
    }

    private void salvaProdutoOrdemServico(OrdemServicoDto ordemServicoDto, OrdemServicoEntity ordemServicoEntity) {

        for (ProdutoDto produtoDto : ordemServicoDto.getProdutos()) {

            Optional<ProdutoEntity> produto = produtoRepository.findById(produtoDto.getId());
            produto.orElseThrow(() -> new ModelNotFound("Produto Not Found ID:" ));

            ordemServicoEntity.getProdutos().add(produto.get());
            //this.estoqueProdutoDisponivel(ordemServicoEntity); // verifica se tem estoque no produto
        }
    }

    private void salvaServicoOrdemServico(OrdemServicoDto ordemServicoDto, OrdemServicoEntity ordemServicoEntity) {

        for (ServicoDto servicoDto : ordemServicoDto.getServicos()) {

            Optional<ServicoEntity> servico = servicoRepository.findById(servicoDto.getId());
            servico.orElseThrow(() -> new ModelNotFound("Serviço Not Found ID:"));

            ordemServicoEntity.getServicos().add(servico.get());
        }
    }

    private void calculaValorTotalSalvaOrdemServico(OrdemServicoEntity ordemServicoEntity, OrdemServicoDto ordemServicoDto) {
        ordemServicoEntity.setValorTotalOrdem(ordemServicoEntity.totalOs());
        osRepository.save(ordemServicoEntity);

    }

    private String gerarProtocolo() {
        String CHARACTERS = "0123456789";
        int CODE_LENGTH = 7;
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    /*
    private void estoqueProdutoDisponivel(OrdemServicoEntity os) {

        for (ProdutoOrdemEntity itemProduto : os.getItemProdutoOs()) {

            if (itemProduto.getQuantidade() > itemProduto.getProdutoEntity().getEstoque()) {
                throw new DataIntegrityViolationException("Quantidade Em Estoque Insuficiente:" + " Produto " + itemProduto.getProdutoEntity().getDescricao() + ": Contem : " + itemProduto.getProdutoEntity().getEstoque() + " Em Estoque");
            }

        }

    }
     */
}
