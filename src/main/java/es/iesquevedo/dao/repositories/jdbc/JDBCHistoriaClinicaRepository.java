package es.iesquevedo.dao.repositories.jdbc;

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

    @Override
    public List<HistoriaClinica> findByPacienteId(Long pacienteId) {
        List<HistoriaClinica> lista = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQueries.FIND_HISTORIA_BY_PACIENTE)) {

            ps.setLong(1, pacienteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Date fecha = rs.getDate("fecha_admision");
                    LocalDate fechaAdmision = (fecha != null) ? fecha.toLocalDate() : null;

                    HistoriaClinica historia = HistoriaClinica.builder()
                            .historiaId(rs.getLong("historia_id"))
                            .pacienteId(rs.getLong("paciente_id"))
                            .medicoId(rs.getLong("medico_id"))
                            .diagnostico(rs.getString("diagnostico"))
                            .fechaAdmision(fechaAdmision)
                            .build();
                    lista.add(historia);
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
                    Date fecha = rs.getDate("fecha_admision");
                    LocalDate fechaAdmision = (fecha != null) ? fecha.toLocalDate() : null;

                    HistoriaClinica historia = HistoriaClinica.builder()
                            .historiaId(rs.getLong("historia_id"))
                            .pacienteId(rs.getLong("paciente_id"))
                            .medicoId(rs.getLong("medico_id"))
                            .diagnostico(rs.getString("diagnostico"))
                            .fechaAdmision(fechaAdmision)
                            .build();
                    return Optional.of(historia);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar historia por ID: " + historiaId, e);
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public boolean save(HistoriaClinica historia) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQueries.INSERT_HISTORIA)) {

            ps.setLong(1, historia.getPacienteId());
            ps.setLong(2, historia.getMedicoId());
            ps.setString(3, historia.getDiagnostico());
            ps.setDate(4, historia.getFechaAdmision() != null ? Date.valueOf(historia.getFechaAdmision()) : null);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al guardar la historia clínica", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(HistoriaClinica historia) {
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
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQueries.DELETE_HISTORIA)) {

            ps.setLong(1, historiaId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al borrar la historia clínica ID: " + historiaId, e);
            throw new RuntimeException(e);
        }
    }
}