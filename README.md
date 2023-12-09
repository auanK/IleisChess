# IleisChess

## Sobre o Projeto

O IleisChess é um jogo de xadrez desenvolvido em Java, com interface gráfica simples, que visa a implementação de todas as regras do xadrez padrão para que seja possível jogar uma partida completa. O projeto foi desenvolvido como um trabalho para a disciplina de Programação Orientada a Objetos, do curso de Ciência da Computação da Universidade Federal do Ceará - Campus Quixadá, lecionada pelo professor Rubens.

## Funcionalidades (Em ordem de implementação)

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
- Tabuleiros com situações especiais: Xeque com en passant, xeque com promoção...W

## Como compilar

Para compilar o projeto, com o JDK instalado na máquina, basta executar o seguinte comando no terminal dentro da pasta raiz do projeto: 

```bash
javac -d bin src/board/*.java src/draw/*.java src/game/*.java src/logic/*.java src/pieces/*.java src/specialmoves/*.java src/ui/*.java
```

Em seguida, para executar o projeto, basta executar o seguinte comando no terminal:

```bash
java -cp bin game.Main
```

## Autor
- Kauan Pablo
- Email: kauanpablo089@gmail.com 
- GitHub: [github.com/auanK](https://github.com/auanK)