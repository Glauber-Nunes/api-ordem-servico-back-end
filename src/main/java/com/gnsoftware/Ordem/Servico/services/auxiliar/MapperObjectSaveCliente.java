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
    ClienteRepository clienteRepository;
    @Autowired
    TelefoneRepository telefoneRepository;
    @Autowired
    EnderecoRepository enderecoRepository;

    public void mapperSave(ClienteEntity clienteEntity, ClienteDto clienteDto, TelefoneEntity telefone
            , EnderecoEntity endereco) {

        telefone.setDd(clienteDto.getTelefone().getDd());
        telefone.setNumero(clienteDto.getTelefone().getNumero());

        endereco.setRua(clienteDto.getEndereco().getRua());
        endereco.setNumero(clienteDto.getEndereco().getNumero());
        endereco.setComplemento(clienteDto.getEndereco().getComplemento());
        endereco.setBairro(clienteDto.getEndereco().getBairro());
        endereco.setCidade(clienteDto.getEndereco().getCidade());
        endereco.setCidade(clienteDto.getEndereco().getCidade());
        endereco.setEstado(clienteDto.getEndereco().getEstado());
        endereco.setCep(clienteDto.getEndereco().getCep());
        endereco.setPais(clienteDto.getEndereco().getPais());

        clienteEntity.setNome(clienteDto.getNome());
        clienteEntity.setCpf(clienteDto.getCpf());
        clienteEntity.setRg(clienteDto.getRg());
        clienteEntity.setEmail(clienteDto.getEmail());
        clienteEntity.setEndereco(endereco);
        clienteEntity.setTelefone(telefone);

        telefoneRepository.save(telefone);
        enderecoRepository.saveAndFlush(endereco);
        clienteRepository.save(clienteEntity);

    }


}
