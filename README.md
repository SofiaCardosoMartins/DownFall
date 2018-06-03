# DownFall

A game based on Java and LibGDX for the LPOO course

## Architecture Design

###### Package Diagram

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/intermediate_delivery/architecture/package_diagram/package_diagram.png)


![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/intermediate_delivery/architecture/package_diagram/packages_relations.png)

###### Class Diagrams

Controller

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/final_delivery/uml/class_diagram/controller/picture.png)

Model

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/final_delivery/uml/class_diagram/model/picture.png)

View

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/final_delivery/uml/class_diagram/view/picture.png)

###### Design Patterns

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/intermediate_delivery/architecture/design_patterns/design_patterns.png)

###### Dynamic UML diagram 

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/intermediate_delivery/architecture/dynamic_diagrams/PlayerStateDiagram.jpg)

## GUI Design

###### Main Functionalities
- Selecionar modo 'Single Player' ou 'Multiplayer'
- Visualizar instruções do jogo (como superar o jogo e comandos a usar)
- Visualizar o jogo (que acompanha o movimento de um ou de dois jogadores), bem como indicadores do seu estado (terminado com ou sem sucesso, nível do jogo, entre outros)

## Test Design

- Desktop com controlo no PC
  - Verificar se perante input das teclas ‘A’ e ‘D’, o player se move corretamente (na direção e distância certas)
  - Verificar se perante input da tecla ‘W’, o player salta
- Desktop com controlo em Android
  - Verificar se com a inclinação para a esquerda/direita do telemóvel o player se move na direção certa e distância apropriada
  - Verificar se carregando no botão destinado a jump, o player salta
  - Verificar se ao colecionar um boost, é recebido um aviso no ecrã do telemóvel com o tipo do respetido boost, enquanto este durar
- Elementos do jogo
  - Verificar a criação de objetos no jogo
  - Verificar o carregamento de sprites
- Lógica de jogo
  - Verificar se ultrapassada uma plataforma, o player cai
  - Verificar se, depois de começar a cair, se encontrar uma plataforma ao longo do percurso da queda, não morre, e permanece nessa plataforma
  - Verificar se, depois de começar a cair, não encontrar nenhuma outra plataforma onde pousar, perde e volta ao início do nível
  - Verificar se a recolha de boosts é feita corretamente
  - Verificar se passado o tempo suficiente, o efeito do boost desaparece
  - Verificar se a recolha de um boost for feita tendo um outro boost previamente, o último boost a ser recolhido é o que fica
  - Verificar se o comportamento do player se altera perante a recolha de cada boost, de acordo com o seu tipo
  - Verificar se a colisão entre o player e obstacles move-o, podendo inclusive fazê-lo cair da plataforma
  - Verificar o movimento adequado das plataformas móveis
  - Verificar o movimento adequado dos obstáculos
  - Verificar a atualização do valor no termómetro (contagem até ao fim) a cada subida/descida do player
  - Verificar a mudança de aspeto do jogo com a mudança de nível, bem como algum tipo de notificação indicativa
  - Verificar se são dadas notificações aquando da mudança de nível
  
###### User Manual

## Main menu
You may choose to play this game in either single or multiple mode.

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/final_delivery/screenshots/MainMenu.png)

## Game
In single mode desktop you control the player with the -> and <- keys to move to the right and left accordingly. In order to jump, you need to press the up arrow.
In single mode android you need to turn the mobile phone rightwards and leftwards for the right and left movement, and touch for jumps.
In multiplayer mode, you use the same android controls in each phone. If you prefer, you may also use the desktop controls in addition to A, W, D keys for the according movements.

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/final_delivery/screenshots/GameMenu.png)

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

