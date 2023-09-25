package com.gnsoftware.Ordem.Servico.services.auxiliar;

import com.gnsoftware.Ordem.Servico.dto.ClienteDto;
import com.gnsoftware.Ordem.Servico.model.ClienteEntity;
import com.gnsoftware.Ordem.Servico.model.EnderecoEntity;
import com.gnsoftware.Ordem.Servico.model.TelefoneEntity;
import com.gnsoftware.Ordem.Servico.repository.ClienteRepository;
import com.gnsoftware.Ordem.Servico.repository.EnderecoRepository;
import com.gnsoftware.Ordem.Servico.repository.TelefoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapperObjectUpdateCliente {

    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    TelefoneRepository telefoneRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    public void mapperUpdate(ClienteEntity clienteBanco, ClienteDto clienteDto) {

        this.updateTelefone(clienteBanco.getTelefone(), clienteDto); // chamada do metodo
        this.updateEndereco(clienteBanco.getEndereco(), clienteDto); // chamada do metodo
        this.updateCliente(clienteBanco, clienteDto); // chamado do metodo


    }

    private void updateCliente(ClienteEntity clienteBanco, ClienteDto clienteDto) {

        clienteBanco.setNome(clienteDto.getNome() != null ? clienteDto.getNome() : clienteBanco.getNome());
        clienteBanco.setCpf(clienteDto.getCpf() != null ? clienteDto.getCpf() : clienteBanco.getCpf());
        clienteBanco.setRg(clienteDto.getRg() != null ? clienteDto.getRg() : clienteBanco.getRg());
        clienteBanco.setEmail(clienteDto.getEmail() != null ? clienteDto.getEmail() : clienteBanco.getEmail());

        clienteRepository.saveAndFlush(clienteBanco);

    }

    private void updateTelefone(TelefoneEntity foneBanco, ClienteDto clienteDto) {

        foneBanco.setDd(clienteDto.getTelefone().getDd() != null ? clienteDto.getTelefone().getDd() : foneBanco.getDd());
        foneBanco.setNumero(clienteDto.getTelefone().getNumero() != null ? clienteDto.getTelefone().getNumero() : foneBanco.getNumero());

        telefoneRepository.saveAndFlush(foneBanco);
    }

    private void updateEndereco(EnderecoEntity enderecoBanco, ClienteDto clienteDto) {

        enderecoBanco.setRua(clienteDto.getEndereco().getRua() != null ? clienteDto.getEndereco().getRua() : enderecoBanco.getRua());
        enderecoBanco.setNumero(clienteDto.getEndereco().getNumero() != null ? clienteDto.getEndereco().getNumero() : enderecoBanco.getNumero());
        enderecoBanco.setComplemento(clienteDto.getEndereco().getComplemento() != null ? clienteDto.getEndereco().getComplemento() : enderecoBanco.getComplemento());
        enderecoBanco.setBairro(clienteDto.getEndereco().getBairro() != null ? clienteDto.getEndereco().getBairro() : enderecoBanco.getBairro());
        enderecoBanco.setCidade(clienteDto.getEndereco().getCidade() != null ? clienteDto.getEndereco().getCidade() : enderecoBanco.getCidade());
        enderecoBanco.setCidade(clienteDto.getEndereco().getCidade() != null ? clienteDto.getEndereco().getCidade() : enderecoBanco.getCidade());
        enderecoBanco.setEstado(clienteDto.getEndereco().getEstado() != null ? clienteDto.getEndereco().getEstado() : enderecoBanco.getEstado());
        enderecoBanco.setCep(clienteDto.getEndereco().getCep() != null ? clienteDto.getEndereco().getCep() : enderecoBanco.getCep());
        enderecoBanco.setPais(clienteDto.getEndereco().getPais() != null ? clienteDto.getEndereco().getPais() : enderecoBanco.getPais());

        enderecoRepository.saveAndFlush(enderecoBanco);

    }
}
