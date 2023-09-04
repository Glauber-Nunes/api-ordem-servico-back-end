package com.gnsoftware.Ordem.Servico.controller.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationError extends StandardError implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<FieldMessage> fieldMessages = new ArrayList<>();

    public ValidationError(Instant timestamp, int status, String Error, String path) {
        super(timestamp, status, Error, path);
    }


    public void addErro(String fieldName, String message) {
        this.fieldMessages.add(new FieldMessage(fieldName, message));
    }
}
