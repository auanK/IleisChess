package board;

import game.Player;
import logic.ChessMove;
import logic.InvalidMoveException;
import pieces.Piece;

public class ChessUI {
    static String ANSI_RESET = "\u001B[0m";
    static String ANSI_RED = "\u001B[31m";
    static String ANSI_YELLOW = "\u001B[33m";
    static String ANSI_BLUE = "\u001B[36m";

    public static void printBoard(Piece[][] board, Player playerWhite, Player playerBlack) {
        printCapturedPieces(playerBlack);

        for (int row = 7; row >= 0; row--) {
            System.out.print((row + 1) + " | ");
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == null) {
                    System.out.print(". ");
                } else {
                    String colorCode = (board[row][col].getColor() == 'W') ? "" : ANSI_YELLOW;
                    System.out.print(colorCode + board[row][col].getLabel() + ANSI_RESET + " ");
                }
            }
            System.out.println();
        }

        System.out.println("    - - - - - - - - ");
        System.out.println("    a b c d e f g h");

        printCapturedPieces(playerWhite);
    }

    public static void printValidMoves(Piece[][] board, int[] coordinatesSource, Player currentPlayer,
            Player opponent, Player playerWhite, Player playerBlack) {
        printCapturedPieces(playerBlack);

        for (int i = 7; i >= 0; i--) {
            System.out.print((i + 1) + " | ");
            for (int j = 0; j < 8; j++) {
                int coordinatesT[] = new int[4];
                coordinatesT[0] = coordinatesSource[0];
                coordinatesT[1] = coordinatesSource[1];
                coordinatesT[2] = i;
                coordinatesT[3] = j;
                try {
                    ChessMove.validateMove(board, coordinatesT, currentPlayer, opponent);
                    if (board[i][j] == null) {
                        System.out.print(ANSI_BLUE + ". " + ANSI_RESET);
                    } else {
                        System.out.print(ANSI_RED + board[i][j].getLabel() + ANSI_RESET + " ");
                    }
                } catch (InvalidMoveException e) {
                    if (board[i][j] == null) {
                        System.out.print(". ");
                    } else {
                        String colorCode = (board[i][j].getColor() == 'W') ? "" : ANSI_YELLOW;
                        System.out.print(colorCode + board[i][j].getLabel() + ANSI_RESET + " ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println("    - - - - - - - - ");
        System.out.println("    a b c d e f g h");

        printCapturedPieces(playerWhite);
    }

    public static void printCapturedPieces(Player player) {
        System.out.println();
        System.err.print("  [ ");

        for (Piece piece : player.getCapturedPieces()) {
            String color = piece.getColor() == 'W' ? ANSI_RESET : ANSI_YELLOW;
            System.out.print(color + piece.getLabel() + ANSI_RESET + " ");
        }
        System.out.println("]");
        System.out.println();
    }

}
