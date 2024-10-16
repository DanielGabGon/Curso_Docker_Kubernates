package org.dangabito.springcloud.msvc.cursos.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectNotFoundException extends  RuntimeException{

    private static final Logger logger = LoggerFactory.getLogger(ObjectNotFoundException.class);

    private final String objectNotFoundName;

    private final Throwable cause;


    public ObjectNotFoundException(String objectNotFoundName) {
        this.objectNotFoundName = objectNotFoundName;
        this.cause = null;
    }

    public ObjectNotFoundException(String objectNotFoundName, Throwable cause) {
        this.objectNotFoundName = objectNotFoundName;
        this.cause = cause;
    }

    @Override
    public String getMessage() {
        String baseMessage=super.getMessage()!=null? super.getMessage():"" ;
        logger.info("MENSAJE DE RETORNO:{}",this.objectNotFoundName);
        return baseMessage.concat("(object not found").concat(this.objectNotFoundName).concat(")");
    }

    public String getObjectNotFoundName() {
        return objectNotFoundName;
    }
}
