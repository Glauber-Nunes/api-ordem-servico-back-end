package com.gnsoftware.Ordem.Servico.controller.exceptions;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
public class FieldMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String fieldName;
    private String message;

    public FieldMessage(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

}
