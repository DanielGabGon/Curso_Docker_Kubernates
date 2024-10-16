package org.dangabito.springcloud.msvc.cursos.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.dangabito.springcloud.msvc.cursos.exception.GlobalExceptionHandler;
import org.dangabito.springcloud.msvc.cursos.models.Usuario;
import org.dangabito.springcloud.msvc.cursos.request.SaveCurso;
import org.dangabito.springcloud.msvc.cursos.request.SaveUsuario;
import org.dangabito.springcloud.msvc.cursos.response.GetCurso;
import org.dangabito.springcloud.msvc.cursos.response.GetUsuario;
import org.dangabito.springcloud.msvc.cursos.services.CursoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class CursoController {

    private static Logger logger = LoggerFactory.getLogger(CursoController.class);
    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<Page<GetCurso>> listar(Pageable pageable){
        Page<GetCurso> cursos= cursoService.listar(pageable);
      return ResponseEntity.ok(cursos);
    }

   @GetMapping("/{id}")
    public ResponseEntity<GetCurso> detalle(@PathVariable Long id){
       //return ResponseEntity.ok(cursoService.porId(id));
       return ResponseEntity.ok(cursoService.porIdConUsuarios(id));
   }

   @PostMapping
   public ResponseEntity<GetCurso> crear(@Valid @RequestBody SaveCurso saveCurso, HttpServletRequest request){
        logger.info("ENTRO INICIO:"+ saveCurso.nombre());
       GetCurso usuarioCurso= cursoService.guardar(saveCurso);
       String baseUrl= request.getRequestURL().toString();
       URI newLocation= URI.create(baseUrl+"/"+ saveCurso.id());
       return ResponseEntity.created(newLocation).body(usuarioCurso);
   }

   @PutMapping
    public ResponseEntity<GetCurso> editar(@Valid @RequestBody SaveCurso saveCurso, @PathVariable Long id){
      GetCurso  updateCurso= cursoService.updateCurso(saveCurso,id);
      return ResponseEntity.ok(updateCurso);
   }

     @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        cursoService.eliminar(id);
        return  ResponseEntity.noContent().build();
     }

    @PutMapping("asignar-usuario/{cursoId}")
    public ResponseEntity<GetUsuario> asignarUsuario(@RequestBody SaveUsuario saveUsuario, @PathVariable Long cursoId, HttpServletRequest request){
        logger.info("ENTRO ASIGNAR");
        GetUsuario usuarioAsigando=cursoService.asignarUsuario(saveUsuario, cursoId);
        String baseUrl= request.getRequestURL().toString();
        URI newLocation= URI.create(baseUrl+"/"+ saveUsuario.id());
        return ResponseEntity.created(newLocation).body(usuarioAsigando);
    }

    @PostMapping("crear-usuario/{cursoId}")
    public ResponseEntity<GetUsuario> crearUsuario(@RequestBody SaveUsuario saveUsuario, @PathVariable Long cursoId, HttpServletRequest request){
        GetUsuario usuarioCreado=cursoService.crearUsuario(saveUsuario, cursoId);
        String baseUrl= request.getRequestURL().toString();
        URI newLocation= URI.create(baseUrl+"/"+ saveUsuario.id());
        return ResponseEntity.created(newLocation).body(usuarioCreado);
    }

    @DeleteMapping("eliminar-usuario/{cursoId}")
    public ResponseEntity<GetUsuario> eliminarUsuario(@RequestBody SaveUsuario saveUsuario, @PathVariable Long cursoId, HttpServletRequest request){
        GetUsuario usuarioEliminado=cursoService.eliminarUsuario(saveUsuario, cursoId);
        String baseUrl= request.getRequestURL().toString();
        URI newLocation= URI.create(baseUrl+"/"+ saveUsuario.id());
        return ResponseEntity.created(newLocation).body(usuarioEliminado);
    }

    @DeleteMapping("/eliminar-curso-usuario/{id}")
    public ResponseEntity<?> eliminarCursoUsuarioPorId(@PathVariable Long id){
        logger.info("LLEGANDO ANDO");
        cursoService.eliminarCursoUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }





}
