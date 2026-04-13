### PROYECTO FINAL

### JUEGO DE AJEDREZ

**1. Descripción del proyecto**  

Este proyecto consiste en un juego de ajedrez isnpirado en God Of War 4/Ragnarok. El Programa muestra un tablero de 8x8 con las piezas en su posición inicial, permite seleccionar y mover piezas según sus reglas principales.

Tales como:   
 1. Alternacion de turnos (Entre blancas y negras).
 2. Movimientos especificos para cada pieza.
 3. No se pueden mover piezas fuera de turno.
 4. Jaque mate: fin del juego.

El sistema detecta situaciones de jaque y jaque mate, muestra el estado de la partida y permite reiniciarla.

**2. Funcionalidades implementadas**  

**Funcionalidades:**
- Mostrar el tablero de 8×8 con las piezas en su posición inicial. (implementado).

- Permitir seleccionar una pieza con clic y resaltar sus movimientos válidos. (implementado)

- Validar los movimientos de cada tipo de pieza (peón, torre, caballo, alfil, reina, rey). (implementado).

- Alternar el turno entre jugadores (blancas y negras). (implementado).

- Detectar jaque e indicarlo visualmente al jugador en turno. (implementado).

- Detectar jaque mate o tablas y mostrar el resultado final:
Este requisito no esta implementado por completo, ya que en ciertos casos especificos el sistema no detecta correctamente el jaque mate debido a errores en ciertas validaciones de movimiento.

- Permitir la promoción del peón al llegar al extremo del tablero. (implementado).

- Incluir un botón para reiniciar la partida. (implementado).

**3. Requisitos previos**  

Java JDK (version 21).
JavaFX SDK (version 21).
IDE de desarrollo (Vs Code recomendado).
Configuracion del module-path para JavaFX.

**4. Cómo ejecutar el proyecto**  

1. Descargar o clonar el proyecto en la computadora.
2. Abrir la carpeta del proyecto en VS Code.
3. Verificar que Java JDK 21 y JavaFX SDK 21 esten instalados y configurados.
4. Asegurarse de que el proyecto tenga configurado el module-path de JavaFX.
4. Ejecutar la clase principal App.java.
5. Al iniciar, aparecera el menu principal del juego.
  5.1 Seleccionar “Empezar nueva partida” para abrir el tablero.

**5. Estructura del proyecto**  
1. Controller : Contiene 2 clases MainController y MainMenu que se esncargan de mover toda la logica del proyecto.

2. Main: Contiene la clase Principal App.java ( La cabeza de proyecto y donde esta su ejecucion).

3. Model: Contieine 1 clase Padre pz y sus clases hijos(Cada pieza del ajedrez) aqui se encuentran las validaciones y Movimientos de todas las piezas.

4. Resource: Dentro de esta encontramos varias sub-carpetas, tales como Img,Letra y sound, como indica sus nombres, estas contienen los recursos utilizados en todo el proyecto.

5. View: en view tenemos 3 archivos FXML que contienen las 3 ventanas del proyecto (Menu inicial, Tablero y Menu final) y un CSS que contiene toda la estetica.

**6. Decisiones de diseño** 

Separe el proyecto en model, view y controller para que cada parte tenga su funcion y el codigo no este todo mezclado. (Imitando estructura Mvc)

Hice una clase para cada tipo de pieza, asi cada una maneja sus propios movimientos y es mas facil de entender y modificar.

Use un GridPane para representar el tablero visualmente y una matriz para controlar la lógica del juego, lo que permite validar movimientos, detectar jaque y manejar las piezas de forma ordenada.

**7. Autor**  
- Dorlin Steven Camacho Castro #1000-4372
- Fecha de entrega: 13/4/2026