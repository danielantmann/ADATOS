package es.iesquevedo.common;

import jakarta.inject.Singleton;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Singleton
public class Configuration {

  private final Properties p;

  public Configuration() {
    p = new Properties();
    try {
      InputStream propertiesStream =
          getClass().getClassLoader().getResourceAsStream(Constantes.MYSQL_PROPERTIES);
      p.loadFromXML(propertiesStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getProperty(String clave) {
    return p.getProperty(clave);
  }

}