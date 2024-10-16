package org.dangabito.springcloud.msvc.usuarios.repositories;

import org.dangabito.springcloud.msvc.usuarios.models.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long>, JpaSpecificationExecutor<Usuario> {


    Optional<Usuario> findByEmail(String email);

    @Query("select u from Usuario u where u.email=?1")
    Optional<Usuario> porEmail(String email);


    boolean existsByEmail(String email);

    @Query("select e from Usuario e where e.id IN :ids")
    Page<Usuario> findByIdIn(Iterable<Long> ids, Pageable pageable);

}
