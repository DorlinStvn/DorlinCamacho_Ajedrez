package controller;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuController 
{

    // metodo que se ejecuta al presionar "Empezar nueva partida" //
    public void jugar(ActionEvent evento) 
    {
        try 
        {
            // obtiene la escena actual del menu //
            Scene escenaActual = ((javafx.scene.Node) evento.getSource()).getScene();

            // obtiene la raiz actual del menu //
            Parent raizActual = escenaActual.getRoot();

            // capa negra (me molestaba el desvanecimiento blanco profe) //
            Rectangle capaNegra = new Rectangle(
                escenaActual.getWidth(),
                escenaActual.getHeight(),
                Color.BLACK
            );

            // inicializa transparente //
            capaNegra.setOpacity(0.0);

            // Se crea un contenedor temporal con el menu y la capa negra encima //
            StackPane contenedor = new StackPane();
            contenedor.getChildren().addAll(raizActual, capaNegra);

            // Se reemplaza temporalmente la raiz de la escena actual //
            escenaActual.setRoot(contenedor);

            // Se crea la animacion para oscurecer el menu //
            FadeTransition desvanecerANegro = new FadeTransition(Duration.millis(500), capaNegra);
            desvanecerANegro.setFromValue(0.0);
            desvanecerANegro.setToValue(1.0);

            // cuando termina de ponerse negro, cambia al tablero //
            desvanecerANegro.setOnFinished(e -> {
                try 
                {
                    // carga el archivo fxml del tablero //
                    FXMLLoader carga = new FXMLLoader(
                        getClass().getResource("/view/Tablero.fxml")
                    );

                    Parent raizNueva = carga.load();

                    // crea la nueva escena con el tablero //
                    Scene escenaNueva = new Scene(raizNueva);

                    // asegura fondo negro en la nueva escena //
                    escenaNueva.setFill(Color.BLACK);
                    raizNueva.setOpacity(0.0);

                    // obtiene el escenario actual //
                    Stage escenario = (Stage) escenaActual.getWindow();

                    // cambia la escena actual por la del tablero //
                    escenario.setScene(escenaNueva);

                    // centra la ventana en la pantalla //
                    escenario.centerOnScreen();

                    escenario.show();

                    // animacion de entrada del tablero //
                    FadeTransition desvanecerEntrada = new FadeTransition(Duration.millis(500), raizNueva);
                    desvanecerEntrada.setFromValue(0.0);
                    desvanecerEntrada.setToValue(1.0);
                    desvanecerEntrada.play();
                } 
                catch (Exception ex) 
                {
                    ex.printStackTrace();
                }
            });

            // ejecuta la animacion //
            desvanecerANegro.play();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    // metodo que se ejecuta al presionar "Salir" //
    public void salir(ActionEvent evento) 
    {
        // obtiene el escenario actual //
        Stage escenario = (Stage) ((javafx.scene.Node) evento.getSource()).getScene().getWindow();
        escenario.close();
    }
}