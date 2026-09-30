package es.iesquevedo.domain.services;

import es.iesquevedo.dao.repositories.UsuarioRepository;
import es.iesquevedo.domain.dto.UsuarioDTO;
import jakarta.inject.Inject;


public class UsuarioService {
  private final UsuarioRepository usuarioRepository;

  @Inject
  public UsuarioService(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  public boolean login(UsuarioDTO usuario) {

    return usuarioRepository.findByUsername(usuario.getUsername())
        .map(u -> u.getUsername().equals(usuario.getUsername())
            && u.getPassword().equals(usuario.getPassword()))
        .orElse(false);

    /* equivale a...:
    Optional<Usuario> usuarioEncontrado = usuarioRepository.findByUsername(usuario.getUsername());
    if  (usuarioEncontrado.isPresent()) {
      return usuarioEncontrado.get().getPassword().equals(usuario.getPassword())
          && usuarioEncontrado.get().getUsername().equals(usuario.getUsername());
    }
    return false;
    */
  }

}
