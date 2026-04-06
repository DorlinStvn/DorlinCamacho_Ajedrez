package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application 
{

    @Override
    public void start(Stage stage) 
    {

        try 
        {
            FXMLLoader carga = new FXMLLoader(
                getClass().getResource("/view/Menu.fxml") 
            );

            // crea la escena con el contenido del fxml //
            Scene scene = new Scene(carga.load());

            // maximiza la ventana al iniciar //
            stage.setMaximized(true);

            stage.setTitle("Ajedrez");

            stage.setScene(scene);

            // evita que el usuario cambie el tamaño // (para mantener el diseño del tablero)
            stage.setResizable(false);

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