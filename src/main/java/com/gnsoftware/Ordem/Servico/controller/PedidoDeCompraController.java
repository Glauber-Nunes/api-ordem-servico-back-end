package com.gnsoftware.Ordem.Servico.controller;

import com.gnsoftware.Ordem.Servico.dto.PedidoDeCompraDto;
import com.gnsoftware.Ordem.Servico.services.PedidoDeCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/pedidoDeCompra")
public class PedidoDeCompraController {

    @Autowired
    private PedidoDeCompraService pedidoDeCompraService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    public ResponseEntity<PedidoDeCompraDto> save(@RequestBody PedidoDeCompraDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(pedidoDeCompraService.save(dto));
    }
}
