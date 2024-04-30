package com.gnsoftware.Ordem.Servico.services.impl;

import com.gnsoftware.Ordem.Servico.dto.ClienteDto;

import com.gnsoftware.Ordem.Servico.model.ClienteEntity;
import com.gnsoftware.Ordem.Servico.model.EnderecoEntity;

import com.gnsoftware.Ordem.Servico.model.TelefoneEntity;
import com.gnsoftware.Ordem.Servico.repository.ClienteRepository;
import com.gnsoftware.Ordem.Servico.services.ClienteService;
import com.gnsoftware.Ordem.Servico.services.auxiliar.MapperObjectSaveCliente;
import com.gnsoftware.Ordem.Servico.services.auxiliar.MapperObjectUpdateCliente;
import com.gnsoftware.Ordem.Servico.services.exceptions.DataIntegrityViolationException;
import com.gnsoftware.Ordem.Servico.services.exceptions.ModelNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    MapperObjectSaveCliente mapperObjectSaveCliente;

    @Autowired
    MapperObjectUpdateCliente mapperObjectUpdateCliente;

    @Override
    @Transactional
    public ClienteDto save(ClienteDto dto) {

        this.findByExistsCPF(dto); // verifica se o cpf ja esta cadastrado
        this.findByExistsEmail(dto); // verifica se o email ja esta cadastrado

        TelefoneEntity telefone = new TelefoneEntity();
        EnderecoEntity endereco = new EnderecoEntity();
        ClienteEntity clienteEntity = new ClienteEntity();

        mapperObjectSaveCliente.mapperSave(clienteEntity, dto, telefone, endereco);

        return new ClienteDto(clienteEntity, clienteEntity.getTelefone(), clienteEntity.getEndereco());

    }

    @Override
    @Transactional
    public ClienteDto update(Long id, ClienteDto dto) {

        Optional<ClienteEntity> clienteBanco = clienteRepository.findById(id);
        clienteBanco.orElseThrow(() -> new ModelNotFound("Cliente Not Found"));

        if (!clienteBanco.get().getCpf().equals(dto.getCpf())) {
            this.findByExistsCPF(dto); // verifica se o cpf ja esta cadastrado ao atualizar
        }

        if (!clienteBanco.get().getEmail().equals(dto.getEmail())) {
            this.findByExistsEmail(dto);
        }

        mapperObjectUpdateCliente.mapperUpdate(clienteBanco.get(), dto);

        if (!clienteBanco.get().getCpf().equals(dto.getCpf())) {
            this.findByExistsCPF(dto); // verifica se o cpf ja esta cadastrado ao atualizar
        }

        if (!clienteBanco.get().getEmail().equals(dto.getEmail())) {
            this.findByExistsEmail(dto);
        }

        return new ClienteDto(clienteBanco.get(), clienteBanco.get().getTelefone(), clienteBanco.get().getEndereco());

    }

    @Override
    public ClienteDto findById(Long id) {

        Optional<ClienteEntity> cliente = clienteRepository.findById(id);

        cliente.orElseThrow(() -> new ModelNotFound("CLIENTE NÃO ENCONTRADO"));

        return new ClienteDto(cliente.get(), cliente.get().getTelefone(), cliente.get().getEndereco());
    }


    @Override
    public List<ClienteDto> findAll() {

        return clienteRepository.findAll().
                stream().map(cliente -> new ClienteDto(cliente, cliente.getTelefone(), cliente.getEndereco())).collect(Collectors.toList());

    }

    @Override
    public void delete(Long id) {

        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
        } else {
            throw new ModelNotFound("CLIENTE Not Found");
        }
    }

    @Override
    public Long countCliente(){
        return clienteRepository.countCliente();
    }


    private void findByExistsCPF(ClienteDto clienteDto) {
        if (clienteRepository.existsByCpf(clienteDto.getCpf())) {
            throw new DataIntegrityViolationException("CPF Ja Cadastrado na base de dados");
        }
    }

    private void findByExistsEmail(ClienteDto clienteDto) {
        if (clienteRepository.existsByemail(clienteDto.getEmail())) {
            throw new DataIntegrityViolationException("Email Ja Cadastrado na base de dados");
        }
    }
}
