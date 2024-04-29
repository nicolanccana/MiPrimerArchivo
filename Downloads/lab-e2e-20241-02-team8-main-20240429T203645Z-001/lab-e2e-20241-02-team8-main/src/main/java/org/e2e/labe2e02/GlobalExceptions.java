package org.e2e.labe2e02;

import org.e2e.labe2e02.exceptions.ResourceNotFound;
import org.e2e.labe2e02.exceptions.UniqueResourceAlreadyExist;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptions {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFound.class)
    public String handleResourceNotFound(ResourceNotFound e){return e.getMessage();}

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UniqueResourceAlreadyExist.class)
    public String handleUniqueResourceAlreadyExist(UniqueResourceAlreadyExist e){return e.getMessage();}

}
