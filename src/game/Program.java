package game;

import pieces.Piece;
import java.util.Scanner;
import board.ChessBoard;

public class Program {
    static Scanner sc = new Scanner(System.in);
    static String ANSI_RESET = "\u001B[0m";
    static String ANSI_RED = "\u001B[31m";
    static String ANSI_YELLOW = "\u001B[33m";
    static String ANSI_BLUE = "\u001B[36m";

    static Player playerWhite = new Player("White");
    static Player playerBlack = new Player("Black");

    public static void main(String[] args) {
        ChessBoard chessBoard = new ChessBoard();
        Piece[][] board = chessBoard.getBoard();

        chessBoard.assignPiecesToPlayers(playerWhite, playerBlack);

        Player currentPlayer = playerWhite;
        Player opponent = playerBlack;

        while (true) {
            printBoard(board);

            System.out.println(currentPlayer.getName() + ", é sua vez. Digite o movimento: ");

            int[] coordinates = inputCoordinates(board, currentPlayer, opponent);
            if (coordinates == null) {
                continue;
            }

            if (!ChessMoveValidator.movePiece(board, coordinates, currentPlayer, opponent)) {
                continue;
            }

            Player temp = currentPlayer;
            currentPlayer = opponent;
            opponent = temp;
        }
    }

    public static void printBoard(Piece[][] board) {
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
            Player opponent) {
        String isValidSource = ChessMoveValidator.isValidSource(board, coordinatesSource, currentPlayer,
                opponent);
        if (isValidSource != null) {
            System.out.println(isValidSource);
            return;
        }

        printCapturedPieces(playerBlack);

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
                        System.out.print(ANSI_BLUE + "-" + ANSI_RESET + " ");
                    } else if (board[i][j] != board[coordinatesSource[0]][coordinatesSource[1]]) {
                        Piece piece = board[i][j];
                        System.out.print(ANSI_RED + piece.getLabel() + ANSI_RESET + " ");
                    }
                } else {
                    if (board[i][j] == board[coordinatesSource[0]][coordinatesSource[1]]) {
                        System.out.print(ANSI_BLUE + board[i][j].getLabel() + ANSI_RESET + " ");
                    } else {
                        if (board[i][j] == null) {
                            System.out.print(". ");
                        } else {
                            Piece piece = board[i][j];
                            if (piece.getColor() == 'W') {
                                System.out.print(piece.getLabel() + " ");
                            } else if (piece.getColor() == 'B') {
                                System.out.print(ANSI_YELLOW + piece.getLabel() + ANSI_RESET + " ");
                            }
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

    public static int[] inputCoordinates(Piece[][] board, Player currentPlayer, Player opponent) {
        int[] coordinates = new int[4];

        String source = sc.nextLine();

        if (source.equals("exit")) {
            System.exit(0);
        }

        if (source.length() == 2) {
            int[] coordinatesSource = parseChessNotation(source);
            String isValidSource = ChessMoveValidator.isValidSource(board, coordinatesSource, currentPlayer,
                    opponent);
            if (isValidSource != null) {
                System.out.println(isValidSource);
                return null;
            }

            printValidMoves(board, coordinatesSource, currentPlayer, opponent);

            String destination = sc.nextLine();
            if (destination.equals("cancel")) {
                return null;
            }
            if (destination.length() != 2) {
                System.out.println("Movimento inválido");
                return null;
            }
            int[] coordinatesDestination = parseChessNotation(destination);

            coordinates[0] = coordinatesSource[0];
            coordinates[1] = coordinatesSource[1];
            coordinates[2] = coordinatesDestination[0];
            coordinates[3] = coordinatesDestination[1];
        } else {
            if (source.length() != 4) {
                System.out.println("Movimento inválido");
                return null;
            }
            coordinates = parseChessNotation(source);
        }
        return coordinates;
    }

    public static int[] parseChessNotation(String chessNotation) {
        if (chessNotation.length() < 4) {
            int[] coordinates = new int[2];
            coordinates[0] = chessNotation.charAt(1) - '1';
            coordinates[1] = chessNotation.charAt(0) - 'a';
            return coordinates;
        }
        int[] coordinates = new int[4];
        coordinates[0] = chessNotation.charAt(1) - '1';
        coordinates[1] = chessNotation.charAt(0) - 'a';
        coordinates[2] = chessNotation.charAt(3) - '1';
        coordinates[3] = chessNotation.charAt(2) - 'a';
        return coordinates;
    }

}
