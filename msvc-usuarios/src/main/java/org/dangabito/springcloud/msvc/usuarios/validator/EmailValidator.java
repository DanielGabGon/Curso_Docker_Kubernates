package org.dangabito.springcloud.msvc.usuarios.validator;

import org.dangabito.springcloud.msvc.usuarios.exception.InvalidEmailException;
import org.dangabito.springcloud.msvc.usuarios.models.entity.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class EmailValidator {

    private static final Logger logger = LoggerFactory.getLogger(EmailValidator.class);

    public static void validateEmail(String email, Usuario usuarioEmail){
              logger.info("CORREO A CAPTURAR:"+ email);
              logger.info("CORRE QUE EXISTE EN BASE:"+ usuarioEmail.getEmail());
        if(!email.isEmpty()&&email.equalsIgnoreCase(usuarioEmail.getEmail())){
           throw  new InvalidEmailException(email,"Ya existe un usuario con ese correo electronico!");
        }
        if(email.isEmpty()){
            throw  new InvalidEmailException(email,"El Email del usuario viene vació!");
        }
    }




}
