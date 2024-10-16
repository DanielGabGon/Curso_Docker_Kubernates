package org.dangabito.springcloud.msvc.cursos.clients;

import org.dangabito.springcloud.msvc.cursos.models.Usuario;
import org.dangabito.springcloud.msvc.cursos.request.SaveUsuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-usuarios" , url = "localhost:8001")
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    Usuario detalle(@PathVariable("id") Long id);

    @PostMapping("/")
    Usuario crear(@RequestBody SaveUsuario saveUsuario);

    @GetMapping("/usuarios-por-curso")
    Page<Usuario> obtenerAlumnosPorCurso(@RequestParam Iterable<Long> ids);



}
