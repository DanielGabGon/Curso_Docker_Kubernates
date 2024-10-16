package org.dangabito.springcloud.msvc.usuarios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidEmailException extends  RuntimeException{

      private final String email;

      private final String errorDescription;


    public InvalidEmailException(String email, String errorDescription) {
        this.email = email;
        this.errorDescription = errorDescription;
    }

    @Override
    public String getMessage() {
        return "Invalid Email: "+errorDescription;
    }

    public String getEmail() {
        return email;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
