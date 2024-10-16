package org.dangabito.springcloud.msvc.usuarios.services;

import org.dangabito.springcloud.msvc.usuarios.clients.CursoClienteRest;
import org.dangabito.springcloud.msvc.usuarios.mapper.UsuarioMapper;
import org.dangabito.springcloud.msvc.usuarios.exception.ObjectNotFoundException;
import org.dangabito.springcloud.msvc.usuarios.models.entity.Usuario;
import org.dangabito.springcloud.msvc.usuarios.repositories.UsuarioRepository;
import org.dangabito.springcloud.msvc.usuarios.request.SaveUser;
import org.dangabito.springcloud.msvc.usuarios.response.GetUsuario;
import org.dangabito.springcloud.msvc.usuarios.validator.EmailValidator;
import org.dangabito.springcloud.msvc.usuarios.validator.PasswordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService{
    private static Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    @Autowired
    private CursoClienteRest cursoClienteRest;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    @Transactional(readOnly = true)
    public Page<GetUsuario> listar(Pageable pageable) {
        Page<Usuario> usuarios= (Page<Usuario>) usuarioRepository.findAll(pageable);
        return usuarios.map(UsuarioMapper::toGetDtoUsuario);
    }


    @Override
    public Usuario findOneEntityById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(()->new ObjectNotFoundException("[Id Usuario:"+id+"]"));
    }

    @Override
    public GetUsuario updateUser(SaveUser saveUser, Long id) {
        PasswordValidator.validatePassword(saveUser.password(), saveUser.password());
        Optional <Usuario> usuarioEmail=usuarioRepository.findByEmail(saveUser.email());
        usuarioEmail.ifPresent(usuario -> EmailValidator.validateEmail(saveUser.email(), usuario));
        Usuario oldUsuario= findOneEntityById(id);
        UsuarioMapper.updateEntity(oldUsuario, saveUser);
        return UsuarioMapper.toGetDtoUsuario(usuarioRepository.save(oldUsuario));
    }

    @Override
    public boolean existePorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public Page<Usuario> listarPorIds(Iterable<Long> ids, Pageable pageable) {
        return usuarioRepository.findByIdIn(ids,pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public GetUsuario porId(Long id) {
        logger.info("LLEGO AL SERVICIO ESPERANDO :"+ id);
        Usuario usuario=findOneEntityById(id);
        return UsuarioMapper.toGetDtoUsuario(usuario);
    }

    @Override
    @Transactional
    public GetUsuario guardar(SaveUser saveUser) {
        PasswordValidator.validatePassword(saveUser.password(),saveUser.password());
        Optional <Usuario> usuarioEmail=usuarioRepository.findByEmail(saveUser.email());
        usuarioEmail.ifPresent(usuario -> EmailValidator.validateEmail(saveUser.email(), usuario));
        Usuario usuario=UsuarioMapper.toEntity(saveUser);
        return UsuarioMapper.toGetDtoUsuario(usuarioRepository.save(usuario));
    }



    @Override
    @Transactional
    public void eliminar(Long id) {
    //    usuarioRepository.findById(id).ifPresentOrElse(
      //          usuario->usuarioRepository.delete(usuario),
        //        ()->{throw new ObjectNotFoundException("[User: "+id+ "] no encontrado");
          //      }
        //);
        Usuario usuario= findOneEntityById(id);
        logger.info("USUARIO ELIMI:"+usuario.getId());
        usuarioRepository.delete(usuario);
        logger.info("PRIMER PASANDO");
        cursoClienteRest.eliminarCursoUsuarioPorId(usuario.getId());
    }


}
