package es.iesquevedo.dao.utils;


import es.iesquevedo.common.Configuration;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;



public class DBConnection {

	private static final Logger logger = Logger.getLogger(DBConnection.class.getName());
	private final Configuration config;

	/**
	 * Opens Database connection
	 */
	@Inject
	public DBConnection(Configuration config) {
		this.config = config;
	}

	public Connection getConnection() throws SQLException {

		Connection conn = DriverManager.getConnection(config.getProperty("urlDB"),
						config.getProperty("user_name"),
						config.getProperty("password"));
		System.out.println("Connected to DB");
		logger.log(Level.INFO, "Conectado a la BD");
		return conn;
	}


}
