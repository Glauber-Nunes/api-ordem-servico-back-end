package com.gnsoftware.Ordem.Servico.services.auxiliar;

import com.gnsoftware.Ordem.Servico.dto.ClienteDto;
import com.gnsoftware.Ordem.Servico.model.ClienteEntity;
import com.gnsoftware.Ordem.Servico.model.EnderecoEntity;
import com.gnsoftware.Ordem.Servico.model.TelefoneEntity;
import com.gnsoftware.Ordem.Servico.model.enums.Perfil;
import com.gnsoftware.Ordem.Servico.repository.ClienteRepository;
import com.gnsoftware.Ordem.Servico.repository.EnderecoRepository;
import com.gnsoftware.Ordem.Servico.repository.TelefoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapperObjectSaveCliente {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private TelefoneRepository telefoneRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    public void mapperSave(ClienteEntity clienteEntity, ClienteDto clienteDto, TelefoneEntity telefone
            , EnderecoEntity endereco) {

        this.saveTelefone(telefone, clienteDto); // chamada do metodo
        this.saveEndereco(endereco, clienteDto); // chamada do metodo
        this.saveCliente(clienteEntity, clienteDto, telefone, endereco); // chama metodo

    }

    private void saveCliente(ClienteEntity clienteEntity, ClienteDto clienteDto,
                             TelefoneEntity telefone, EnderecoEntity endereco) {

        clienteEntity.setNome(clienteDto.getNome());
        clienteEntity.setCpf(clienteDto.getCpf());
        clienteEntity.setRg(clienteDto.getRg());
        clienteEntity.setEmail(clienteDto.getEmail());
        clienteEntity.setEndereco(endereco);
        clienteEntity.setTelefone(telefone);
        clienteEntity.setPerfil(Perfil.CLIENTE);

        clienteRepository.save(clienteEntity);
    }

    private void saveTelefone(TelefoneEntity telefone, ClienteDto clienteDto) {

        telefone.setDd(clienteDto.getTelefone().getDd());
        telefone.setNumero(clienteDto.getTelefone().getNumero());

        telefoneRepository.save(telefone);
    }

    private void saveEndereco(EnderecoEntity endereco, ClienteDto clienteDto) {

        endereco.setRua(clienteDto.getEndereco().getRua());
        endereco.setNumero(clienteDto.getEndereco().getNumero());
        endereco.setComplemento(clienteDto.getEndereco().getComplemento());
        endereco.setBairro(clienteDto.getEndereco().getBairro());
        endereco.setCidade(clienteDto.getEndereco().getCidade());
        endereco.setCidade(clienteDto.getEndereco().getCidade());
        endereco.setEstado(clienteDto.getEndereco().getEstado());
        endereco.setCep(clienteDto.getEndereco().getCep());
        endereco.setPais(clienteDto.getEndereco().getPais());

        enderecoRepository.save(endereco);

    }

}
