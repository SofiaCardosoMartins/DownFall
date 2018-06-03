# DownFall

A game based on Java and LibGDX for the LPOO course

## Architecture Design

###### Package Diagram

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/final_delivery/uml/package_diagram/package_diagram.png)


![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/intermediate_delivery/architecture/package_diagram/packages_relations.png)

###### Class Diagrams

Controller

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/final_delivery/uml/class_diagram/controller/picture.png)

Model

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/final_delivery/uml/class_diagram/model/picture.png)

View

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/final_delivery/uml/class_diagram/view/picture.png)

Network

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/final_delivery/uml/class_diagram/network/picture.png)

Test

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/final_delivery/uml/class_diagram/test/picture.png)

###### Design Patterns

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/intermediate_delivery/architecture/design_patterns/design_patterns.png)

###### Dynamic UML diagram 

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/intermediate_delivery/architecture/dynamic_diagrams/PlayerStateDiagram.jpg)

## GUI Design

###### Main Functionalities
- Selecionar modo 'Single Player' ou 'Multiplayer'
- Visualizar o jogo (que acompanha o movimento de um ou de dois jogadores), bem como indicadores do seu estado (terminado com ou sem sucesso, nível do jogo, entre outros)

###### User Manual

## Main menu
You may choose to play this game in either single or multiple mode.

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/final_delivery/screenshots/MainMenu.png)

## Game
In single mode desktop you control the player with the -> and <- keys to move to the right and left accordingly. In order to jump, you need to press the up arrow.
In single mode android you need to turn the mobile phone rightwards and leftwards for the right and left movement, and touch for jumps.
In multiplayer mode, you use the same android controls in each phone. If you prefer, you may also use the desktop controls in addition to A, W, D keys for the according movements.

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/final_delivery/screenshots/Game.png)

## Server Menu
In case you want to play multiplayer mode (desktop, at the beggining), you will be faced with this menu. It contains the IP to be inserted on both mobile phones.

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/final_delivery/screenshots/ServerMenu.png)

## Client Menu
In case you want to play multiplayer mode (mobile), you will be faced with this menu. Both players must insert the IP they see on the desktop's screen so that the game begins.

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/final_delivery/screenshots/ClientMenu.png)

## Lost Game
When you lose the game, you will be faced with this menu in order to return to the main menu.

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/final_delivery/screenshots/LostMenu.png)

## Won Game
When you lose the game, you will be faced with this menu in order to return to the main menu.

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/final_delivery/screenshots/WonMenu.png)

## Test Design

- Tests to the controllers
  - Test the initialization of the GameController instance
  - Test the transition between the player's states
  - Test the addition of a new controller to the game
  - Test the deletion of a model and the consequent effects in the GameController (a controller should be eliminated)
  - Test the collision between the player and the world's bounds
  
- Tests to the models
  - 

## Development of the Final Project

###### Major difficulties along the way

- This year's school calendar was an obstacle given the close distribution of tests and deliveries of various projects
- Handling the JUnit tests in the LibGDX environment (more precisely using Box2D)
- Understanding and implementing multiple design patterns
- Problems developing and testing the network connection 
- Difficult gameplay

###### Lessons learned

- How to apply design patterns to improve code flexibility and development
- How to use LibGDX and Box2D in the context of game development
- Manage time and resources throughout project development

###### Time Spent Developing

- Approximately between 1 and 2 hours a day during 3 weeks (for each of us)

###### Work Distribution Amongst Team Members

- Sofia Martins: 50 %
- Margarida Silva: 50 %
