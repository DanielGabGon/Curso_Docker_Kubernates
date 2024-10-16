package org.dangabito.springcloud.msvc.usuarios.mapper;

import org.dangabito.springcloud.msvc.usuarios.models.entity.Usuario;
import org.dangabito.springcloud.msvc.usuarios.request.SaveUser;
import org.dangabito.springcloud.msvc.usuarios.response.GetUsuario;

public class UsuarioMapper {

    public static GetUsuario toGetDtoUsuario(Usuario usuario){
        if(usuario==null)  return null;

        return  new GetUsuario(usuario.getId(),usuario.getNombre(),usuario.getEmail(), usuario.getPassword());
    }

    public static Usuario toEntity(SaveUser saveUser){
         if(saveUser==null) return null;
         Usuario nuevoUsuario= new Usuario();
         nuevoUsuario.setNombre(saveUser.nombre());
         nuevoUsuario.setEmail(saveUser.email());
         nuevoUsuario.setPassword(saveUser.password());
         return nuevoUsuario;
    }


    public static void updateEntity(Usuario usuarioOld, SaveUser saveUser){
        if(usuarioOld==null) return;
        usuarioOld.setNombre(saveUser.nombre());
        usuarioOld.setEmail(saveUser.email());
        usuarioOld.setPassword(saveUser.password());
    }

    public static Long numerico(String id) {
        Long idL = 0L;
        if(id!=null && id.matches("\\d+")){
              idL= Long.parseLong(id);
        }

        return idL;
    }




}
