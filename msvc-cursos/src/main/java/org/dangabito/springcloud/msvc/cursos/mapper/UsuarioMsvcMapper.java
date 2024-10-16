package org.dangabito.springcloud.msvc.cursos.mapper;

import org.dangabito.springcloud.msvc.cursos.models.Usuario;
import org.dangabito.springcloud.msvc.cursos.models.entity.Curso;
import org.dangabito.springcloud.msvc.cursos.response.GetUsuario;

public class UsuarioMsvcMapper {


    public static GetUsuario toGetDto(Usuario usuarioMsvc) {
        if(usuarioMsvc==null) return null;
        return new GetUsuario(usuarioMsvc.getId(), usuarioMsvc.getNombre(), usuarioMsvc.getEmail(), usuarioMsvc.getPassword());
    }



}
