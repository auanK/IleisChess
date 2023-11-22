package ui;

import game.PlayChess;
import game.ChessLog;
import game.Player;
import logic.Exceptions;
import logic.MoveValidator;
import pieces.Piece;

// Classe responsável pela interface do tabuleiro.
public class BoardUI {
    private static String reset = "\u001B[0m";
    private static String red = "\u001B[31m";
    private static String yellow = "\u001B[33m";
    private static String blue = "\u001B[36m";

    // Imprime o tabuleiro.
    public static void printBoard(Piece[][] board, Player playerWhite, Player playerBlack) {
        System.out.print("\033[H\033[2J");
        printCapturedPieces(playerBlack);

        for (int row = 7; row >= 0; row--) {
            System.out.print((row + 1) + " | ");
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == null) {
                    System.out.print(". ");
                } else {
                    String colorCode = (board[row][col].getColor() == 'W') ? "" : yellow;
                    System.out.print(colorCode + board[row][col].getLabel() + reset + " ");
                }
            }
            System.out.println();
        }

        System.out.println("    - - - - - - - - ");
        System.out.println("    a b c d e f g h");

        printCapturedPieces(playerWhite);
    }

    // Imprime o tabuleiro com as posições válidas para a peça selecionada.
    public static void printValidMoves(Piece[][] board, int[] coordinatesSource, Player currentPlayer,
            Player opponent) {
        System.out.print("\033[H\033[2J");

        // Salva quem é o jogador branco e quem é o jogador preto.
        Player playerWhite = currentPlayer;
        Player playerBlack = opponent;
        if (currentPlayer.getColor() == 'B') {
            playerWhite = opponent;
            playerBlack = currentPlayer;
        }

        ChessLog log = PlayChess.getLog();

        printCapturedPieces(playerBlack);

        for (int i = 7; i >= 0; i--) {
            System.out.print((i + 1) + " | ");
            for (int j = 0; j < 8; j++) {
                int coordinatesT[] = new int[4];
                coordinatesT[0] = coordinatesSource[0];
                coordinatesT[1] = coordinatesSource[1];
                coordinatesT[2] = i;
                coordinatesT[3] = j;

                if (i == coordinatesSource[0] && j == coordinatesSource[1] && board[i][j] != null) {
                    System.out.print(blue + board[i][j].getLabel() + reset + " ");
                    continue;
                }

                try {
                    MoveValidator.validateMove(board, coordinatesT, currentPlayer, opponent, log);
                    if (board[i][j] == null) {
                        System.out.print(blue + "_ " + reset);
                    } else {
                        System.out.print(red + board[i][j].getLabel() + reset + " ");
                    }
                } catch (Exceptions e) {
                    if (e.getMessage().equals("Rock!")) {
                        if (board[i][j] == null) {
                            System.out.print(blue + "_ " + reset);
                        } else if (currentPlayer.getPieces().contains(board[i][j])) {
                            System.out.print(blue + board[i][j].getLabel() + reset + " ");
                        } else {
                            System.out.print(red + board[i][j].getLabel() + reset + " ");
                        }
                    } else if (e.getMessage().equals("En Passant!")) {
                        if (board[i][j] == null) {
                            System.out.print(blue + "_ " + reset);
                        } else if (currentPlayer.getPieces().contains(board[i][j])) {
                            System.out.print(blue + board[i][j].getLabel() + reset + " ");
                        } else {
                            System.out.print(red + board[i][j].getLabel() + reset + " ");
                        }
                    } else {
                        if (board[i][j] == null) {
                            System.out.print(". ");
                        } else {
                            String colorCode = (board[i][j].getColor() == 'W') ? "" : yellow;
                            System.out.print(colorCode + board[i][j].getLabel() + reset + " ");
                        }

                    }
                }
            }
            System.out.println();
        }
        System.out.println("    - - - - - - - - ");
        System.out.println("    a b c d e f g h");

        printCapturedPieces(playerWhite);
    }

    // Imprime as peças capturadas de um jogador.
    public static void printCapturedPieces(Player player) {
        System.out.println();
        System.err.print(" Peças capturadas: [ ");

        for (Piece piece : player.getCapturedPieces()) {
            String color = piece.getColor() == 'W' ? reset : yellow;
            System.out.print(color + piece.getLabel() + reset + " ");
        }
        System.out.println("]");
        System.out.println();
    }
}
