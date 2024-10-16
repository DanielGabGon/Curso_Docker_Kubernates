package org.dangabito.springcloud.msvc.cursos.services;

import org.dangabito.springcloud.msvc.cursos.clients.UsuarioClientRest;
import org.dangabito.springcloud.msvc.cursos.exception.GlobalExceptionHandler;
import org.dangabito.springcloud.msvc.cursos.exception.ObjectNotFoundException;
import org.dangabito.springcloud.msvc.cursos.mapper.CursoMapper;
import org.dangabito.springcloud.msvc.cursos.mapper.UsuarioMsvcMapper;
import org.dangabito.springcloud.msvc.cursos.models.Usuario;
import org.dangabito.springcloud.msvc.cursos.models.entity.Curso;
import org.dangabito.springcloud.msvc.cursos.models.entity.CursoUsuario;
import org.dangabito.springcloud.msvc.cursos.repositories.CursoRepository;
import org.dangabito.springcloud.msvc.cursos.request.SaveCurso;
import org.dangabito.springcloud.msvc.cursos.request.SaveUsuario;
import org.dangabito.springcloud.msvc.cursos.response.GetCurso;
import org.dangabito.springcloud.msvc.cursos.response.GetUsuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements  CursoService {
    private static Logger logger = LoggerFactory.getLogger(CursoServiceImpl.class);

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioClientRest usuarioClientRest;

    @Override
    @Transactional(readOnly = true)
    public Page<GetCurso> listar(Pageable pageable) {
        Page<Curso> cursos = cursoRepository.findAll(pageable);
        return cursos.map(CursoMapper::toGetDtoCurso);
    }

    @Override
    public Curso findOneEntityById(Long id) {
        return cursoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("[Id Curso:" + id + "]"));
    }

    @Override
    public GetCurso updateCurso(SaveCurso saveCurso, Long id) {
        Curso oldCurso = findOneEntityById(id);
        CursoMapper.updateEntity(oldCurso, saveCurso);
        return CursoMapper.toGetDtoCurso(cursoRepository.save(oldCurso));
    }

    @Override
    @Transactional(readOnly = true)
    public GetCurso porId(Long id) {
        Curso curso = findOneEntityById(id);
        return CursoMapper.toGetDtoCurso(curso);
    }

    @Override
    @Transactional(readOnly = true)
    public GetCurso porIdConUsuarios(Long id) {
               Curso curso=findOneEntityById(id);
               if(!curso.getCursoUsuarios().isEmpty()){
                    List<Long> ids=curso.getCursoUsuarios().stream().map(CursoUsuario::getUsuarioId)
                            .collect(Collectors.toList());
                    logger.info("ENTRO A VER");
                   Page<Usuario> usuarios=usuarioClientRest.obtenerAlumnosPorCurso(ids);
                    usuarios.forEach(lis->logger.info("Nombre:"+lis.getNombre()+"-"+lis.getId()));
                    curso.setUsuarios(usuarios.getContent());
               }
        return CursoMapper.toGetDtoCurso(curso);
    }

    @Override
    @Transactional
    public GetCurso guardar(SaveCurso saveCurso) {
        logger.info("ENTRO A GUARDAR:"+ saveCurso.nombre());
        Curso curso = CursoMapper.toEntity(saveCurso);
        return CursoMapper.toGetDtoCurso(cursoRepository.save(curso));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        //    cursoRepository.findById(id).ifPresentOrElse(
        //          curso->cursoRepository.delete(curso),
        //        ()->{throw new ObjectNotFoundException("[Curso: "+id+ "] no encontrado");
        //      }
        //);
        Curso curso=findOneEntityById(id);
        cursoRepository.delete(curso);
    }

    @Override
    @Transactional
    public void eliminarCursoUsuarioPorId(Long id) {
        logger.info("LLEGO A CURSO ELIMINANDO EL USUARIO:");
      cursoRepository.eliminarCursoUsuarioPorId(id);
    }

    @Override
    @Transactional
    public GetUsuario asignarUsuario(SaveUsuario saveUsuario, Long cursoId) {
        logger.info("LLEGANDO SERVICIO");

        Curso curso  =findOneEntityById(cursoId);
        logger.info("LLEGO AL USUARIO ID:"+ saveUsuario.id());

        Usuario usuarioMsvc= usuarioClientRest.detalle(saveUsuario.id());

        logger.info("LLEGO USUARIO SERVICIO");
        CursoUsuario cursoUsuario= new CursoUsuario();
        cursoUsuario.setUsuarioId(usuarioMsvc.getId());

        curso.addCursoUsuario(cursoUsuario);
        cursoRepository.save(curso);

        return UsuarioMsvcMapper.toGetDto(usuarioMsvc);
    }

    @Override
    @Transactional
    public GetUsuario crearUsuario(SaveUsuario saveUsuario, Long cursoId) {
        Curso curso  =findOneEntityById(cursoId);

        Usuario usuarioNuevoMsvc= usuarioClientRest.crear(saveUsuario);

        CursoUsuario cursoUsuario= new CursoUsuario();
        cursoUsuario.setUsuarioId(usuarioNuevoMsvc.getId());

        curso.addCursoUsuario(cursoUsuario);
        cursoRepository.save(curso);
        return UsuarioMsvcMapper.toGetDto(usuarioNuevoMsvc);
    }

    @Override
    @Transactional
    public GetUsuario eliminarUsuario(SaveUsuario saveUsuario, Long cursoId) {
        Curso curso  =findOneEntityById(cursoId);
        Usuario usuarioMsvc= usuarioClientRest.detalle(saveUsuario.id());

        CursoUsuario cursoUsuario= new CursoUsuario();
        cursoUsuario.setUsuarioId(usuarioMsvc.getId());
        logger.info("CURSO USUARIO ID:"+ cursoUsuario.getUsuarioId());
        curso.removeCursoUsuario(cursoUsuario);
        logger.info("CURSO :"+ curso.toString());
        cursoRepository.save(curso);

        return UsuarioMsvcMapper.toGetDto(usuarioMsvc);
    }
}
