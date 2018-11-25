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

Dado que dependiendo de lo que reciba la mesa como notificación debe hacer cosas diferentes, se implementó un visitor. Para ello, se creó una interfaz visitor que es implementada por AbstractTable y una interfaz Notification que es implementada por AbstractHittable y AbstractBonus.

Si a la mesa se le manda un hittable es para que se aumente el puntaje de Game, con lo que se manda en una notificación a Game con en cuanto debe aumentar su puntaje.

Si a la mesa se le mandan un ExtraBallBonus o un JackPotBonus, esta se encarga de llamar al método trigger de estos.

Si a la mesa se le manda un DropTargetBonus, se aumenta la cantidad de droptTargets que se han botado, se revisa si se cumple la condición para hacer trigger a este Bonus, y en caso de que se cumpla llama al método trigger de este.

## Cómo correr el programa
Clonar el repositorio.

Abrir Intellij IDEA.

Presionar Import Project, seleccionar el proyecto.

Para correr los test hacer click derecho en la carpeta java que está dentro de test. Seleccionar "Run All Tests"
