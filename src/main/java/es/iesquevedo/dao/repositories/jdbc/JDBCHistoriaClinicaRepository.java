package es.iesquevedo.dao.repositories.jdbc;

import es.iesquevedo.dao.common.RowMapper;
import es.iesquevedo.dao.common.SQLQueries;
import es.iesquevedo.dao.model.HistoriaClinica;
import es.iesquevedo.dao.repositories.HistoriaClinicaRepository;
import es.iesquevedo.dao.utils.DBConnection;
import jakarta.inject.Inject;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCHistoriaClinicaRepository implements HistoriaClinicaRepository {
    private final Logger logger = Logger.getLogger(JDBCHistoriaClinicaRepository.class.getName());
    private final DBConnection dbConnection;

    @Inject
    public JDBCHistoriaClinicaRepository(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    private final RowMapper<HistoriaClinica> historiaRowMapper = rs -> {
        Date fecha = rs.getDate("fecha_admision");
        LocalDate fechaAdmision = (fecha != null) ? fecha.toLocalDate() : null;

        return HistoriaClinica.builder()
                .historiaId(rs.getLong("historia_id"))
                .pacienteId(rs.getLong("paciente_id"))
                .medicoId(rs.getLong("medico_id"))
                .diagnostico(rs.getString("diagnostico"))
                .fechaAdmision(fechaAdmision)
                .build();
    };

    @Override
    public List<HistoriaClinica> findByPacienteId(Long pacienteId) {
        List<HistoriaClinica> lista = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQueries.FIND_HISTORIA_BY_PACIENTE)) {

            ps.setLong(1, pacienteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(historiaRowMapper.mapRow(rs));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar historias por paciente ID: " + pacienteId, e);
            throw new RuntimeException(e);
        }
        return lista;
    }

    @Override
    public Optional<HistoriaClinica> findById(Long historiaId) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQueries.FIND_HISTORIA_BY_ID)) {

            ps.setLong(1, historiaId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(historiaRowMapper.mapRow(rs));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar historia por ID: " + historiaId, e);
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Long save(HistoriaClinica historia) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQueries.INSERT_HISTORIA, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, historia.getPacienteId());
            ps.setLong(2, historia.getMedicoId());
            ps.setString(3, historia.getDiagnostico());
            ps.setDate(4, historia.getFechaAdmision() != null ? Date.valueOf(historia.getFechaAdmision()) : null);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getLong(1); // Devuelve el ID generado
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al guardar la historia clínica", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean update(HistoriaClinica historia) {
        if (historia.getHistoriaId() == null || findById(historia.getHistoriaId()).isEmpty()) {
            logger.log(Level.WARNING, "Intento de actualizar historia inexistente ID: " + historia.getHistoriaId());
            return false;
        }

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQueries.UPDATE_HISTORIA)) {

            ps.setLong(1, historia.getPacienteId());
            ps.setLong(2, historia.getMedicoId());
            ps.setString(3, historia.getDiagnostico());
            ps.setDate(4, historia.getFechaAdmision() != null ? Date.valueOf(historia.getFechaAdmision()) : null);
            ps.setLong(5, historia.getHistoriaId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al actualizar la historia clínica ID: " + historia.getHistoriaId(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long historiaId) {
        if (findById(historiaId).isEmpty()) {
            logger.log(Level.WARNING, "Intento de borrar historia que no existe ID: " + historiaId);
            return false;
        }

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQueries.DELETE_HISTORIA)) {

            ps.setLong(1, historiaId);
            return ps.executeUpdate() > 0;

        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            logger.log(Level.WARNING, "No se puede borrar la historia clínica ID " + historiaId + " porque tiene registros asociados.", e);
            return false;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al borrar la historia clínica ID: " + historiaId, e);
            throw new RuntimeException(e);
        }
    }
}