package org.dangabito.springcloud.msvc.usuarios.response;

import jakarta.persistence.Column;

import java.io.Serializable;

public record GetUsuario(
        Long id,
        String nombre,
        String email,
        String password) implements Serializable {
}
