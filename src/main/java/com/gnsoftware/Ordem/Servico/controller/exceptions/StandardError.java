package com.gnsoftware.Ordem.Servico.controller.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandardError implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Instant timestamp;
    private int status;
    private String Error;
    private String path;
}
