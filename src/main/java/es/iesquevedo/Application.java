package es.iesquevedo;

import es.iesquevedo.ui.MainMenu;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class Application {
  static void main() {
    try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
      MainMenu mainMenu = container.select(MainMenu.class).get();
      mainMenu.run();
    }
  }
}
