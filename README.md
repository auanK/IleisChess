# IleisChess

## Sobre o Projeto

O IleisChess é um jogo de xadrez desenvolvido em Java como parte do trabalho para a disciplina de Programação Orientada a Objetos da Universidade Federal do Ceará - Campus Quixadá, ministrada pelo professor Rubens.

## Funcionalidades (A maioria em ordem de implementação)

- Representação de todas as peças de xadrez padrão.
- Representação do tabuleiro de xadrez padrão.
- Interface gráfica simples para interação do jogador na partida.
- Movimentação de peças de acordo com as regras do xadrez.
- Representação de jogadas válidas de acordo com as regras do xadrez.
- Regras de xeque e xeque-mate.
- Regras de promoção de peões.
- Regras de roque.
- Salvar log da partida em arquivo .txt.
- Regras de empate: Empate por acordo, empate por afogamento, empate por falta de material, empate por repetição de jogadas e empate por 50 jogadas sem captura ou movimentação de peões.
- Regras de en passant.
- Salvar e carregar partidas.
- Menu de opções antes de iniciar a partida.
- Tabuleiros com situações especiais: Xeque com en passant, xeque com promoção...

## Pacotes

De forma geral, o projeto está dividido em 6 pacotes, sendo eles:

- board: Contém as classes que representam o tabuleiro de xadrez, incluindo variações implementadas.
- game: Classes relacionadas à execução da partida, interação dos jogadores, salvamento/carregamento de partidas e registros, além do Exception implementado para o projeto.
- logic: Funcionalidades lógicas do jogo, incluindo verificação de xeque, xeque-mate, empates, validação e execução de movimentos.
- pieces: Representação das peças de xadrez.
- specialmoves: Implementação de movimentos especiais, como roque, en passant e promoção de peões.
- ui: Classes relacionadas à interface gráfica do jogo, como menu, carregamento de partidas, ajuda, impressão do tabuleiro e etc..

## Como compilar

Para compilar o projeto, com o JDK instalado na máquina, basta executar o seguinte comando no terminal dentro da pasta raiz do projeto: 

```bash
javac -d bin src/Main.java src/board/*.java src/game/*.java src/logic/*.java src/pieces/*.java src/specialmoves/*.java src/ui/*.java
```

Em seguida, para executar o projeto, execute o seguinte comando:

```bash
java -cp bin Main
```