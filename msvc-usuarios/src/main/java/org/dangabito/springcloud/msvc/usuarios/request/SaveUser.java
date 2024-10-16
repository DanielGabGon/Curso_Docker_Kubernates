package org.dangabito.springcloud.msvc.usuarios.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record SaveUser(Long id,
                      @Pattern( regexp =  "[a-zA-Z0-9-_]{8,255}", message = "{save.name.pattern}")
                      @NotBlank(message = "{generic.notblank}")
                      String nombre,
                       @Email(message = "{email.pattern.mensaje}")
                       @NotBlank(message = "{generic.notblank}")
                       String email,
                       @Size(min = 6, max = 255, message = "{generic.size}") @NotBlank(message = "{generic.notblank}")
                       String password
) implements Serializable {
}
