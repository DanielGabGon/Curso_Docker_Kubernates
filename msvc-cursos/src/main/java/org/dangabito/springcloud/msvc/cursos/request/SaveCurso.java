package org.dangabito.springcloud.msvc.cursos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

public record SaveCurso(
        Long id,
        @NotBlank(message = "{generic.notblank}")
        String nombre

) implements Serializable {
}
