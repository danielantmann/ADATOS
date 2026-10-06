package es.iesquevedo.dao.repositories.jdbc;

import es.iesquevedo.common.Constantes;
import es.iesquevedo.dao.common.SQLQueries;
import es.iesquevedo.dao.model.Paciente;
import es.iesquevedo.dao.repositories.PacienteRepository;
import es.iesquevedo.dao.utils.DBConnection;
import es.iesquevedo.domain.error.DatabaseError;
import jakarta.inject.Inject;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCPacienteRepository implements PacienteRepository {
    private static final Logger logger = Logger.getLogger(JDBCPacienteRepository.class.getName());
    private final DBConnection dbConnection;

    @Inject
    public JDBCPacienteRepository(DBConnection dbConnection ){
        this.dbConnection = dbConnection;
    }
    @Override
    public List<Paciente> findAll() {
        List<Paciente> lista = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.FIND_ALL_PACIENTES);
             ResultSet rs = preparedStatement.executeQuery()){

            logger.log(Level.INFO, SQLQueries.FIND_ALL_PACIENTES);
            while (rs.next()){
                Date fecha = rs.getDate("fecha_nacimiento");
                LocalDate fnac = (fecha != null) ? fecha.toLocalDate() : null;

                Paciente paciente = Paciente.builder()
                        .pacienteId(rs.getLong("paciente_id"))
                        .nombre(rs.getString("nombre"))
                        .fechaNacimiento(fnac)
                        .telefono(rs.getString("telefono"))
                        .build();
                lista.add(paciente);
            }
            logger.log(Level.INFO, "Pacientes encontrados: " + lista.size());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar todos los pacientes", e);
            throw new DatabaseError(e.getMessage());
        }
        return lista;
    }

    @Override
    public Optional<Paciente> findById(Long id) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.FIND_PACIENTE_BY_ID)){
             logger.log(Level.INFO, SQLQueries.FIND_PACIENTE_BY_ID);
             preparedStatement.setLong(1,id);

             try (ResultSet rs = preparedStatement.executeQuery()){
                 if (rs.next()){
                     Date fecha = rs.getDate("fecha_nacimiento");
                     LocalDate fnac = (fecha != null) ? fecha.toLocalDate() : null;

                     Paciente paciente = Paciente.builder()
                             .pacienteId(rs.getLong("paciente_id"))
                             .nombre(rs.getString("nombre"))
                             .fechaNacimiento(fnac)
                             .telefono(rs.getString("telefono"))
                             .build();
                     return Optional.of(paciente);
                 }
             }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar paciente por ID: " + id, e);
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public boolean save(Paciente paciente) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.INSERT_PACIENTE)) {

            logger.log(Level.INFO, SQLQueries.INSERT_PACIENTE);
            preparedStatement.setString(1, paciente.getNombre());
            preparedStatement.setDate(2, paciente.getFechaNacimiento() != null ? Date.valueOf(paciente.getFechaNacimiento()) : null);
            preparedStatement.setString(3, paciente.getTelefono());

            int filas = preparedStatement.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al guardar paciente", e);
            throw new DatabaseError(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public boolean update(Paciente paciente) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.UPDATE_PACIENTE)) {

            logger.log(Level.INFO, SQLQueries.UPDATE_PACIENTE);
            preparedStatement.setString(1, paciente.getNombre());
            preparedStatement.setDate(2, paciente.getFechaNacimiento() != null ? Date.valueOf(paciente.getFechaNacimiento()) : null);
            preparedStatement.setString(3, paciente.getTelefono());
            preparedStatement.setLong(4, paciente.getPacienteId());

            int filas = preparedStatement.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al actualizar paciente", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.DELETE_PACIENTE)) {

            logger.log(Level.INFO, SQLQueries.DELETE_PACIENTE);
            preparedStatement.setLong(1, id);

            int filas = preparedStatement.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al borrar paciente con ID: " + id, e);
            throw new RuntimeException(e);
        }
    }
}
