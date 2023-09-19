package game;

import pieces.Piece;
import java.util.Scanner;
import board.ChessBoard;

/**
 * Classe que representa o programa principal do jogo de xadrez.
 */
public class Program {
    /**
     * Este método principal (main) é a entrada principal para o programa de xadrez.
     * Inicializa o jogo, configura o tabuleiro, cria jogadores e permite que os
     * jogadores façam movimentos até que o jogo termine.
     *
     * @param args Os argumentos de linha de comando (não são usados neste
     *             programa).
     */
    public static void main(String[] args) {
        // Inicializa o tabuleiro de xadrez
        ChessBoard chessBoard = new ChessBoard();
        Piece[][] board = chessBoard.getBoard();

        // Cria os jogadores
        Player playerWhite = new Player("White");
        Player playerBlack = new Player("Black");
        chessBoard.assignPiecesToPlayers(playerWhite, playerBlack);

        Player currentPlayer = playerWhite; // Começa com o jogador branco
        Player opponent = playerBlack;

        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_YELLOW = "\u001B[33m";

        while (true) {
            printBoard(board, playerWhite, playerBlack, currentPlayer, opponent, ANSI_RESET, ANSI_YELLOW);

            // Solicita a entrada do jogador
            System.out.println(currentPlayer.getName() + ", é sua vez. Digite o movimento: ");

            int[] coordinates = new int[4];
            // Pega as coordenadas de origem
            String source = sc.nextLine();
            if (source.length() == 2) {
                int[] coordinatesSource = parseChessNotation(source);

                printValidMoves(board, coordinatesSource, playerWhite, playerBlack, currentPlayer, opponent,
                        ANSI_RESET, ANSI_RED, ANSI_YELLOW);

                String destination = sc.nextLine();
                int[] coordinatesDestination = parseChessNotation(destination);

                coordinates[0] = coordinatesSource[0];
                coordinates[1] = coordinatesSource[1];
                coordinates[2] = coordinatesDestination[0];
                coordinates[3] = coordinatesDestination[1];
            } else {
                coordinates = parseChessNotation(source);
            }

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
        if (chessNotation.length() < 4) {
            int[] coordinates = new int[2];
            coordinates[0] = chessNotation.charAt(1) - '1'; // Origem - Linha
            coordinates[1] = chessNotation.charAt(0) - 'a'; // Origem - Coluna
            return coordinates;
        }
        int[] coordinates = new int[4];
        coordinates[0] = chessNotation.charAt(1) - '1'; // Origem - Linha
        coordinates[1] = chessNotation.charAt(0) - 'a'; // Origem - Coluna
        coordinates[2] = chessNotation.charAt(3) - '1'; // Destino - Linha
        coordinates[3] = chessNotation.charAt(2) - 'a'; // Destino - Coluna
        return coordinates;
    }

    /**
     * Imprime o tabuleiro de xadrez, incluindo peças.
     *
     * @param board         O tabuleiro de xadrez representado como uma matriz de
     *                      peças.
     * @param playerWhite   O jogador branco.
     * @param playerBlack   O jogador preto.
     * @param currentPlayer O jogador atual que está fazendo o movimento.
     * @param opponent      O jogador adversário.
     * @param ANSI_RESET    Reset de cor ANSI.
     * @param ANSI_YELLOW   Código ANSI para cor amarela.
     */
    public static void printBoard(Piece[][] board, Player playerWhite, Player playerBlack, Player currentPlayer,
            Player opponent, String ANSI_RESET, String ANSI_YELLOW) {
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
                        System.out.print(ANSI_YELLOW + piece.getLabel() + ANSI_RESET + " ");
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
            System.out.print(ANSI_YELLOW + piece.getLabel() + ANSI_RESET + " ");
        }
        System.out.println("]");
        System.out.println();
    }

    /**
     * Imprime os movimentos válidos para a peça na posição de origem especificada.
     *
     * @param board             O tabuleiro de xadrez representado como uma matriz
     *                          de
     *                          peças.
     * @param coordinatesSource As coordenadas de origem [linha de origem, coluna de
     *                          origem].
     * @param playerWhite       O jogador branco.
     * @param playerBlack       O jogador preto.
     * @param currentPlayer     O jogador atual que está fazendo o movimento.
     * @param opponent          O jogador adversário.
     * @param ANSI_RESET        Reset de cor ANSI.
     * @param ANSI_RED          Código ANSI para cor vermelha.
     * @param ANSI_YELLOW       Código ANSI para cor amarela.
     */
    public static void printValidMoves(Piece[][] board, int[] coordinatesSource, Player playerWhite,
            Player playerBlack, Player currentPlayer, Player opponent, String ANSI_RESET, String ANSI_RED,
            String ANSI_YELLOW) {
        if (board[coordinatesSource[0]][coordinatesSource[1]] == null) {
            System.out.println("Não há peça na posição de origem.");
        }

        System.out.println();
        System.err.print("  [ ");
        for (Piece piece : playerBlack.getCapturedPieces()) {
            System.out.print(piece.getLabel() + " ");
        }
        System.out.println("]");
        System.out.println();
        for (int i = 7; i >= 0; i--) {
            System.out.print((i + 1) + " | ");
            for (int j = 0; j < 8; j++) {
                int coordinatesT[] = new int[4];
                coordinatesT[0] = coordinatesSource[0];
                coordinatesT[1] = coordinatesSource[1];
                coordinatesT[2] = i;
                coordinatesT[3] = j;
                if (ChessMoveValidator.validateMove(board, coordinatesT, currentPlayer, opponent) == null) {
                    if (board[i][j] == null) {
                        // Se a posição de destino for vazia, imprime "-" em vermelho para indicar que o
                        // movimento é válido para essa posição|
                        System.out.print(ANSI_RED + "-" + ANSI_RESET + " ");
                    } else {
                        // Se a posição de destino não for vazia, imprime a peça em vermelho para
                        // indicar que ela pode ser capturada
                        Piece piece = board[i][j];
                        if (piece.getColor() == 'W') {
                            System.out.print(ANSI_RED + piece.getLabel() + ANSI_RESET + " ");
                        } else if (piece.getColor() == 'B') {
                            // Imprime a peça preta vermelha
                            System.out.print(ANSI_RED + piece.getLabel() + ANSI_RESET + " ");
                        }
                    }
                } else {
                    if (board[i][j] == null) {
                        // Imprime "." para casas vazias
                        System.out.print(". ");
                    } else {
                        Piece piece = board[i][j];
                        if (piece.getColor() == 'W') {
                            // Imprime a peça branca
                            System.out.print(piece.getLabel() + " ");
                        } else if (piece.getColor() == 'B') {
                            // Imprime a peça preta com cor amarela
                            System.out.print(ANSI_YELLOW + piece.getLabel() + ANSI_RESET + " ");
                        }
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
            System.out.print(ANSI_YELLOW + piece.getLabel() + ANSI_RESET + " ");
        }
        System.out.println("]");
        System.out.println();
    }
}
