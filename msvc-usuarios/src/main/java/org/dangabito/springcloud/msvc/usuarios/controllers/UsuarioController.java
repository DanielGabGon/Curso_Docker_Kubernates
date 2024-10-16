package org.dangabito.springcloud.msvc.usuarios.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.dangabito.springcloud.msvc.usuarios.models.entity.Usuario;
import org.dangabito.springcloud.msvc.usuarios.request.SaveUser;
import org.dangabito.springcloud.msvc.usuarios.response.GetUsuario;
import org.dangabito.springcloud.msvc.usuarios.services.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UsuarioController {
    private static Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Page<GetUsuario>> listar(Pageable pageable){
        Page<GetUsuario> usuarios= usuarioService.listar(pageable);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUsuario> detalle(@PathVariable Long id){
           return ResponseEntity.ok(usuarioService.porId(id));
        }

    @PostMapping
    public ResponseEntity<GetUsuario>  crear (@Valid @RequestBody SaveUser saveDto,BindingResult bindingResult, HttpServletRequest request){
        //      if(bindingResult.hasErrors()){
          //        return validar(bindingResult);  Aqui es para mandar sólo mensaje básico de causa error.
            //  }
        GetUsuario  usuarioCreado= usuarioService.guardar(saveDto);
         String baseUrl= request.getRequestURL().toString();
         URI newLocationUri= URI.create(baseUrl+"/"+saveDto.id());
        return ResponseEntity.created(newLocationUri).body(usuarioCreado);
        }

        @PutMapping("/{id}")
      public ResponseEntity<GetUsuario> editar(@Valid @RequestBody SaveUser saveUser, @PathVariable Long id){
        GetUsuario updateUsuario=usuarioService.updateUser(saveUser, id);
        return ResponseEntity.ok(updateUsuario);
    }


        @DeleteMapping("{id}")
       public ResponseEntity<?> eliminar(@PathVariable Long id) {
           usuarioService.eliminar(id);
           return ResponseEntity.noContent().build();
        }

    @GetMapping("/usuarios-por-curso")
    public ResponseEntity<Page<Usuario>> obtenerAlumnosPorCurso(@RequestParam List<Long> ids, Pageable pageable){
          logger.info("ENTRO MSU");
         return ResponseEntity.ok(usuarioService.listarPorIds(ids,pageable));
    }



    private ResponseEntity<Map<String,String>> validar(BindingResult bindingResult){
        Map<String, String> errores= new HashMap<>();
        bindingResult.getFieldErrors().forEach(error->{
            errores.put(error.getField(), "El campo " + error.getField()+ " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }


    }



