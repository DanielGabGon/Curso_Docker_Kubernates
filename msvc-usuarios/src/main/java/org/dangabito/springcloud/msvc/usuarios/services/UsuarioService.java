package org.dangabito.springcloud.msvc.usuarios.services;

import org.dangabito.springcloud.msvc.usuarios.models.entity.Usuario;
import org.dangabito.springcloud.msvc.usuarios.request.SaveUser;
import org.dangabito.springcloud.msvc.usuarios.response.GetUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService {

    Page<GetUsuario> listar(Pageable pageable);

    GetUsuario porId(Long id);

    GetUsuario guardar(SaveUser saveUser);

    void eliminar(Long id);

    Usuario findOneEntityById(Long Id);

    GetUsuario updateUser(SaveUser saveUser, Long id);

    boolean existePorEmail(String email);

    Page<Usuario> listarPorIds(Iterable<Long> ids, Pageable pageable);

}

