package org.dangabito.springcloud.msvc.cursos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record SaveUsuario(
        @NotBlank(message = "{generic.notblank}")
        Long id,
        @NotBlank(message = "{generic.notblank}")
        String nombre,
        @Email(message = "{email.pattern.mensaje}")
        @NotBlank(message = "{generic.notblank}")
        String email,
        @Size(min = 6, max = 255, message = "{generic.size}") @NotBlank(message = "{generic.notblank}")
        String password) implements Serializable {
}
