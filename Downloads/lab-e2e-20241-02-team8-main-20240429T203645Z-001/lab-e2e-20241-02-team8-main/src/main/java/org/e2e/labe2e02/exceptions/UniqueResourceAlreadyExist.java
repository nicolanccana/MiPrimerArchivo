package org.e2e.labe2e02.exceptions;

import lombok.Data;

@Data
public class UniqueResourceAlreadyExist extends RuntimeException{
    public UniqueResourceAlreadyExist(String mensaje){super(mensaje);}
}
