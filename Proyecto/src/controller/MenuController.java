package controller;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MenuController 
{
    
    private MediaPlayer reproductor;

    // se ejecuta cuando carga el menu //
    @FXML
    public void initialize()
    {
        reproducirMusica();
    }

    // metodo que se ejecuta al presionar "Empezar nueva partida" //
    public void jugar(ActionEvent evento) 
    {
        try 
        {
            // detiene la musica antes de cambiar //
            if (reproductor != null)
            {
                reproductor.stop();
            }

            // obtiene la escena actual del menu //
            Scene escenaActual = ((javafx.scene.Node) evento.getSource()).getScene();

            // obtiene la raiz actual del menu //
            Parent raizActual = escenaActual.getRoot();

            // capa negra (me molestaba el desvanecimiento blanco profe) //
            Rectangle capaNegra = new Rectangle
            (
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
        // detiene la musica antes de cerrar //
        if (reproductor != null)
        {
            reproductor.stop();
        }

        // obtiene el escenario actual //
        Stage escenario = (Stage) ((javafx.scene.Node) evento.getSource()).getScene().getWindow();
        escenario.close();
    }

     // metodo para reproducir musica //
    private void reproducirMusica()
    {
        try
        {
            String ruta = getClass().getResource("/resources/Sound/menu.mp3").toExternalForm();

            Media musica = new Media(ruta);
            reproductor = new MediaPlayer(musica);

            // volumen //
            reproductor.setVolume(0.4);

            // Modo Gojo(Infinito) //
            reproductor.setCycleCount(MediaPlayer.INDEFINITE);

            reproductor.play();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}