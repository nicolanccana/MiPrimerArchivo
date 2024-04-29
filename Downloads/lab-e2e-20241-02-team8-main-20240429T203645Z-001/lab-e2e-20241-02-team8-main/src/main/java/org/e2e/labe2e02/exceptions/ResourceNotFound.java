package org.e2e.labe2e02.exceptions;

import jakarta.validation.constraints.DecimalMax;
import lombok.Data;

@Data
public class ResourceNotFound extends RuntimeException{
    public ResourceNotFound(String mensaje){
        super(mensaje);
    }
}
