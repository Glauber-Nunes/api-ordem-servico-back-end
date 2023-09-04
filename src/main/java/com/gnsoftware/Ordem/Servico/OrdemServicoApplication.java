package com.gnsoftware.Ordem.Servico;

import com.gnsoftware.Ordem.Servico.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrdemServicoApplication implements CommandLineRunner {

    @Autowired
    private PermissaoRepository permissaoRepository;
    
    public static void main(String[] args) {
        SpringApplication.run(OrdemServicoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
