package org.dangabito.springcloud.msvc.cursos.mapper;

import org.dangabito.springcloud.msvc.cursos.models.entity.Curso;
import org.dangabito.springcloud.msvc.cursos.request.SaveCurso;
import org.dangabito.springcloud.msvc.cursos.response.GetCurso;

public class CursoMapper {

    public static GetCurso toGetDtoCurso(Curso curso){
        if(curso==null) return null;
       return new GetCurso(curso.getId(), curso.getNombre(), curso.getCursoUsuarios(),curso.getUsuarios());
    }

    public static Curso toEntity(SaveCurso saveCurso){
        if (saveCurso==null) return  null;
         Curso nuevoCurso= new Curso();
         nuevoCurso.setId(saveCurso.id());
         nuevoCurso.setNombre(saveCurso.nombre());
        return nuevoCurso;
    };

    public static void updateEntity(Curso oldCurso, SaveCurso saveCurso) {
        if (oldCurso==null) return;
        oldCurso.setNombre(saveCurso.nombre());
    }
}
