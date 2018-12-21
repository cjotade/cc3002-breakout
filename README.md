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
Tomando como base el codigo implementado en tarea 2 se procede a realizar la interfaz grafica con fxgl.

## Patrones de diseño utilizados
### Null Pattern
Se utilizó para crear un NullLevel que es el nivel que inicializa el juego y que permite saber cuando termina.

### Observer
El juego observa al nivel y los bricks. El nivel informa cuando alcanza el puntaje necesario para cambiar de nivel, mientras que los bricks notifican que fueron destruidos para modificar el puntaje o número de bolas según corresponda.

### Visitor+Observer
El juego Observa a los LogicElements (Levels y Bricks) los cuales envían el aumento de puntos, cambio de numero de bolas o cambio de nivel.

### Factory
La interfaz grafica del juego llama a la Factory para crear una Entity.

## Features
### Mayores
1. Estado distinto
2. Nuevo nivel configurable: para acceder al panel apretar la tecla TAB donde además se muestra el estado del juego.

### Menores
1. Sonido al golpe
2. Chispas/estrellas al golpear: se modificó por un FireEmitter por estetica.

## Clases importantes interfaz grafica
Todas las clases se encontrarán en el paquete gui dentro de la carpeta java.
### BreakOutGameApp
Tiene todas las funcionalidades generales de la interfaz grafica permitiendo crear el juego (run).
### BreakOutFactory
Permite crear las Entidades que posteriormente serán añadidas al juego a partir de los componentes definidos en el paquete control.
### Components
Contiene 3 componentes generales ubicadas en el paquete control: Ball, Bar y Brick que describen el comportamiento de cada una de ellas.


## Cómo correr el programa
Clonar el repositorio.

Abrir Intellij IDEA.

Presionar Import Project, seleccionar el proyecto.

Para correr los test hacer click derecho en la carpeta java que está dentro de test. Seleccionar "BrickTest", "GameTest", "LevelsTest".

Para jugar dirigirse a la carpeta gui dentro de java y poner run a "BreakOutGameApp".

### Teclas
Las teclas son las dadas por enunciado salvo la tecla TAB que permite abrir un panel desplegable para añadir un Custom Level o visualizar el estado actual del juego.

### Notas
Se utilizó codigo propio de tarea 2.
