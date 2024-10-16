package org.dangabito.springcloud.msvc.cursos.response;

public record GetUsuario(Long id,
                         String nombre,
                         String email,
                         String password) {
}
