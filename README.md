# cc3002-breakout
## Descripción del programa
Este programa simula la mecanica del juego Breakout.

Breakout es un juego de arcade que consiste en utilizar una barra horizontal y una bola que rebota en las paredes de la pantalla, para golpear ladrillos posicionados en la parte superior de ésta.

El objetivo básico del juego es usar la bola para golpear Bricks y ganar puntos para pasar al siguiente nivel.
Los Bricks tienen la característica de que cuando la bola choca con ellos, esta rebota
y, dependiendo del tipo de Brick, este puede ser destruido, y el jugador ganar puntos de ello. Una
vez se hayan destruido todos los Bricks de una nivel, el juego continúa al nivel siguiente de forma
automática. Cuando un jugador pierde todas las bolitas, este pierde el juego.

## Cómo se hizo
Tomando como base las interfaces entregadas en la tarea, se implementaron clases abstractas que implementan gran parte de los métodos que estas poseían para entregar una funcionalidad general a estos.

## Patrones de diseño utilizados
### Null Pattern
Se utilizó para crear un NullLevel que es el nivel que inicializa el juego y que permite saber cuando termina.

### Observer
El juego observa al nivel y los bricks. El nivel informa cuando alcanza el puntaje necesario para cambiar de nivel, mientras que los bricks notifican que fueron destruidos para modificar el puntaje o número de bolas según corresponda.

### Visitor+Observer
El juego Observa a los LogicElements (Levels y Bricks) los cuales envían el aumento de puntos, cambio de numero de bolas o cambio de nivel.

## Cómo correr el programa
Clonar el repositorio.

Abrir Intellij IDEA.

Presionar Import Project, seleccionar el proyecto.

Para correr los test hacer click derecho en la carpeta java que está dentro de test. Seleccionar "BrickTest", "GameTest", "LevelsTest".
