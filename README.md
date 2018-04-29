# DownFall

A game based on Java and LibGDX for the LPOO course

## Architecture Design

###### Package Diagram

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/intermediate_delivery/architecture/package_diagram/package_diagram.png)

###### Class Diagrams

Controller

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/intermediate_delivery/architecture/class_diagram/images/controller.png)

Model

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/intermediate_delivery/architecture/class_diagram/images/model.png)

View

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/intermediate_delivery/architecture/class_diagram/images/view.png)

###### Design Patterns

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/intermediate_delivery/architecture/design_patterns/design_patterns.png)

## GUI Design

###### Main Functionalities
- Selecionar modo 'Single Player' ou 'Multiplayer'
- Visualizar instruções do jogo (como superar o jogo e comandos a usar)
- Visualizar o jogo (que acompanha o movimento de um ou de dois jogadores), bem como indicadores do seu estado (terminado com ou sem sucesso, nível do jogo, entre outros)


###### GUI mock-ups

![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/intermediate_delivery/gui/mockups/%231_MainMenu.png)
![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/intermediate_delivery/gui/mockups/%232_Instructions.png)
![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/intermediate_delivery/gui/mockups/%233_Gameplay.png)
![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/intermediate_delivery/gui/mockups/%234_Lost.png)
![alt text](https://github.com/SofiaCardosoMartins/DownFall/blob/master/intermediate_delivery/gui/mockups/%235_Won.png)

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
