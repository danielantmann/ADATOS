package es.iesquevedo.dao.repositories.jdbc;

import es.iesquevedo.dao.common.SQLQueries;
import es.iesquevedo.dao.model.Medico;
import es.iesquevedo.dao.repositories.MedicoRepository;
import es.iesquevedo.dao.utils.DBConnection;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JBCMedicoRepository implements MedicoRepository {
    private final Logger logger = Logger.getLogger(JBCMedicoRepository.class.getName());
    private final DBConnection dbConnection;

    @Inject
    public JBCMedicoRepository(DBConnection dbConnection){this.dbConnection = dbConnection;}

    @Override
    public List<Medico> findAll() {
        List<Medico> lista = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.FIND_ALL_DOCTORS);
             ResultSet rs = preparedStatement.executeQuery()){

            logger.log(Level.INFO, SQLQueries.FIND_ALL_DOCTORS);

            while (rs.next()){

                Medico medico = Medico.builder()
                        .medicoId(rs.getLong("medico_id"))
                        .nombre(rs.getString("nombre"))
                        .especialidad(rs.getString("especialidad"))
                        .telefono(rs.getString("telefono"))
                        .build();
                lista.add(medico);
            }
            logger.log(Level.INFO, "Medicos encontrados: " + lista.size());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar todos los medicos", e);
            throw new RuntimeException(e);
        }
        return lista;
    }

    @Override
    public Optional<Medico> findById(Long id) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQueries.FIND_DOCTOR_BY_ID);){

            ps.setLong(1,id);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){



                    Medico medico = Medico.builder()
                            .medicoId(rs.getLong("medico_id"))
                            .nombre(rs.getString("nombre"))
                            .especialidad(rs.getString("especialidad"))
                            .telefono(rs.getString("telefono"))
                            .build();

                    return Optional.of(medico);
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar medico por ID: " + id, e);
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
