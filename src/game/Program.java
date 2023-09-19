package game;

import pieces.Piece;
import java.util.Scanner;

import board.BoardTeste;

/**
 * Classe que representa o programa principal do jogo de xadrez.
 */
public class Program {
    /**
     * Este método principal (main) é a entrada principal para o programa de xadrez.
     * Inicializa o jogo, configura o tabuleiro, cria jogadores e permite que os
     * jogadores façam movimentos até que o jogo termine.
     *
     * @param args Os argumentos de linha de comando (não são usados neste programa).
     */
    public static void main(String[] args) {
        // Inicializa o tabuleiro de xadrez
        BoardTeste chessBoard = new BoardTeste();
        Piece[][] board = chessBoard.getBoard();

        // Cria os jogadores
        Player playerWhite = new Player("White");
        Player playerBlack = new Player("Black");
        chessBoard.assignPiecesToPlayers(playerWhite, playerBlack);

        Player currentPlayer = playerWhite; // Começa com o jogador branco
        Player opponent = playerBlack;

        while (true) {
            // Imprime as peças capturadas do jogador adversário
            System.out.println();
            System.err.print("  [ ");
            for (Piece piece : playerBlack.getCapturedPieces()) {
                System.out.print(piece.getLabel() + " ");
            }
            System.out.println("]");
            System.out.println();

            // Imprime o tabuleiro
            for (int row = 7; row >= 0; row--) {
                System.out.print((row + 1) + " | ");

                for (int col = 0; col < 8; col++) {
                    if (board[row][col] == null) {
                        // Imprime "." para casas vazias
                        System.out.print(". ");
                    } else {
                        Piece piece = board[row][col];
                        if (piece.getColor() == 'W') {
                            // Imprime a peça branca
                            System.out.print(piece.getLabel() + " ");
                        } else if (piece.getColor() == 'B') {
                            // Imprime a peça preta com cor amarela
                            System.out.print("\u001B[33m" + piece.getLabel() + "\u001B[0m" + " ");
                        }
                    }
                }
                System.out.println();
            }

            // Imprime a grade do tabuleiro
            System.out.println("    - - - - - - - - ");
            System.out.println("    a b c d e f g h");

            // Imprime as peças capturadas do jogador atual
            System.out.println();
            System.err.print("  [ ");
            for (Piece piece : playerWhite.getCapturedPieces()) {
                System.out.print("\u001B[33m" + piece.getLabel() + "\u001B[0m" + " ");
            }
            System.out.println("]");
            System.out.println();

            // Solicita a entrada do jogador
            System.out.println(currentPlayer.getName() + ", é sua vez. Digite o movimento: ");
            String chessNotation = sc.nextLine();

            if (chessNotation.equals("exit")) {
                break;
            }

            // Traduz a notação de xadrez para coordenadas da matriz
            int[] coordinates = parseChessNotation(chessNotation);

            // Chama a função que valida o movimento e realiza o movimento se for válido
            if (ChessMoveValidator.movePiece(board, coordinates, currentPlayer, opponent)) {
                // Se o movimento foi bem-sucedido, troca o jogador
                if (currentPlayer == playerWhite) {
                    currentPlayer = playerBlack;
                    opponent = playerWhite;
                } else {
                    currentPlayer = playerWhite;
                    opponent = playerBlack;
                }
            }
        }
    }

    // Inicializa o scanner para entrada
    static Scanner sc = new Scanner(System.in);

    /**
     * Traduz a notação de xadrez para coordenadas da matriz.
     *
     * @param chessNotation A notação de xadrez no formato "a2 a4".
     * 
     * @return Um array de int com as coordenadas [linha de origem, coluna de
     *         origem, linha de destino, coluna de destino].
     */

    public static int[] parseChessNotation(String chessNotation) {
        int[] coordinates = new int[4];

        coordinates[0] = chessNotation.charAt(1) - '1'; // Origem - Linha
        coordinates[1] = chessNotation.charAt(0) - 'a'; // Origem - Coluna
        coordinates[2] = chessNotation.charAt(3) - '1'; // Destino - Linha
        coordinates[3] = chessNotation.charAt(2) - 'a'; // Destino - Coluna

        return coordinates;
    }
}
