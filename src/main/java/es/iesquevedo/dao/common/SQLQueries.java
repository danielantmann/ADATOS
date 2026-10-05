package es.iesquevedo.dao.common;

public class SQLQueries {
  public static final String FIND_USUARIO_BY_USERNAME = "SELECT * FROM usuarios WHERE username = ?";

  public static final String FIND_ALL_PACIENTES = "SELECT * FROM pacientes";
  public static final String FIND_PACIENTE_BY_ID = "SELECT * FROM pacientes WHERE paciente_id = ?";
  public static final String INSERT_PACIENTE = "INSERT INTO pacientes (nombre, fecha_nacimiento, telefono) VALUES (?, ?, ?)";
  public static final String UPDATE_PACIENTE = "UPDATE pacientes SET nombre = ?, fecha_nacimiento = ?, telefono = ? WHERE paciente_id = ?";
  public static final String DELETE_PACIENTE = "DELETE FROM pacientes WHERE paciente_id = ?";

  public static final String  FIND_ALL_DOCTORS = "SELECT * FROM medicos";
  public static final String FIND_DOCTOR_BY_ID = "SELECT * FROM medicos WHERE medico_id = ?";

  public static final String FIND_HISTORIA_BY_PACIENTE = "SELECT * FROM historia_clinica WHERE paciente_id = ?";
  public static final String FIND_HISTORIA_BY_ID = "SELECT * FROM historia_clinica WHERE historia_id = ?";
  public static final String INSERT_HISTORIA = "INSERT INTO historia_clinica (paciente_id, medico_id, diagnostico, fecha_admision) VALUES (?, ?, ?, ?)";
  public static final String UPDATE_HISTORIA = "UPDATE historia_clinica SET paciente_id = ?, medico_id = ?, diagnostico = ?, fecha_admision = ? WHERE historia_id = ?";
  public static final String DELETE_HISTORIA = "DELETE FROM historia_clinica WHERE historia_id = ?";
}
