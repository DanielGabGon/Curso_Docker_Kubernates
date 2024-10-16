package org.dangabito.springcloud.msvc.cursos.response;

import org.dangabito.springcloud.msvc.cursos.models.Usuario;
import org.dangabito.springcloud.msvc.cursos.models.entity.CursoUsuario;

import java.io.Serializable;
import java.util.List;

public record GetCurso(
        Long id,

        String nombre,

        List<CursoUsuario> cursoUsuarios,

        List<Usuario> usuarios

) implements Serializable {
}
