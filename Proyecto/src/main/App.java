package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        try {
            // carga el archivo fxml //
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/Tablero.fxml")
            );

            // crea la escena con el contenido del fxml //
            Scene scene = new Scene(loader.load());

            // titulo de la ventana //
            stage.setTitle("Ajedrez");

            // asigna la escena al stage //
            stage.setScene(scene);

            // evita que el usuario cambie el tamaño // (para mantener el diseño del tablero)
            stage.setResizable(false);

            // muestra la ventana // 
            stage.show();

        } catch (Exception e) {
            // imprime error si algo falla (fxml, rutas, etc)
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}