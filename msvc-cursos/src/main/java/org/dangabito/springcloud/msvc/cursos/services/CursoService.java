package org.dangabito.springcloud.msvc.cursos.services;

import org.dangabito.springcloud.msvc.cursos.models.Usuario;
import org.dangabito.springcloud.msvc.cursos.models.entity.Curso;
import org.dangabito.springcloud.msvc.cursos.request.SaveCurso;
import org.dangabito.springcloud.msvc.cursos.request.SaveUsuario;
import org.dangabito.springcloud.msvc.cursos.response.GetCurso;
import org.dangabito.springcloud.msvc.cursos.response.GetUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CursoService {
    
    Page<GetCurso> listar(Pageable pageable);

    GetCurso porId(Long id);

    GetCurso porIdConUsuarios(Long id);

    GetCurso guardar(SaveCurso saveCurso);

    Curso findOneEntityById(Long id);

    GetCurso updateCurso(SaveCurso saveCurso, Long id);

    void eliminar(Long id);

    void eliminarCursoUsuarioPorId(Long id);


    GetUsuario asignarUsuario(SaveUsuario saveUsuario, Long cursoId);

    GetUsuario crearUsuario(SaveUsuario saveUsuario, Long cursoId);

    GetUsuario eliminarUsuario(SaveUsuario saveUsuario, Long cursoId);



}
