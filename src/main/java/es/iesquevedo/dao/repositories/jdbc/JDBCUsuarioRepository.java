package es.iesquevedo.dao.repositories.jdbc;

import es.iesquevedo.dao.common.SQLQueries;
import es.iesquevedo.dao.model.Usuario;
import es.iesquevedo.dao.repositories.UsuarioRepository;
import es.iesquevedo.dao.utils.DBConnection;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCUsuarioRepository implements UsuarioRepository {
  private static final Logger logger = Logger.getLogger(JDBCUsuarioRepository.class.getName());

  private final DBConnection dbConnection;

  @Inject
  public JDBCUsuarioRepository(DBConnection dbConnection) {
    this.dbConnection = dbConnection;
  }

  @Override
  public Optional<Usuario> findByUsername(String username) {


    Usuario usuario = Usuario.builder().username(username).build();
    try (Connection connection= dbConnection.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.FIND_USUARIO_BY_USERNAME)) {

      logger.log(Level.INFO, SQLQueries.FIND_USUARIO_BY_USERNAME );
      preparedStatement.setString(1, username);
      try (ResultSet rs = preparedStatement.executeQuery()) {
        if (rs.next()) {
          usuario.setPassword(rs.getString("password"));
          logger.log(Level.INFO, "usuario encontrado: " + username);
          return Optional.of(usuario);
        }
        logger.log(Level.INFO, "usuario no encontrado: " + username);
        return Optional.empty();
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}
