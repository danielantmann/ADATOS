package es.iesquevedo.dao.repositories;

import es.iesquevedo.dao.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository {
  Optional<Usuario> findByUsername(String username);
}
