package game;

import java.util.Scanner;

import board.ChessUI;
import logic.ChessMove;
import logic.InvalidMoveException;
import pieces.Bishop;
import pieces.Knight;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

public class UserInput {
    static Scanner sc = new Scanner(System.in);

    public static int[] inputCoordinates(Piece[][] board, Player currentPlayer, Player opponent, Player playerWhite,
            Player playerBlack) {
        int[] coordinates = new int[4];

        String source = sc.nextLine();

        if (source.equals("exit")) {
            System.exit(0);
        }

        try {
            if (source.length() == 2) {
                int[] coordinatesSource = parseChessNotation(source);
                ChessMove.isValidSource(board, coordinatesSource, currentPlayer, opponent);
                ChessUI.printValidMoves(board, coordinatesSource, currentPlayer, opponent, playerWhite, playerBlack);

                String destination = sc.nextLine();
                if (destination.equals("cancel")) {
                    return null;
                }

                if (destination.length() != 2) {
                    throw new InvalidMoveException("Movimento inválido, formato de destino incorreto");
                }

                int[] coordinatesDestination = parseChessNotation(destination);

                coordinates[0] = coordinatesSource[0];
                coordinates[1] = coordinatesSource[1];
                coordinates[2] = coordinatesDestination[0];
                coordinates[3] = coordinatesDestination[1];
            } else if (source.length() == 4) {
                coordinates = parseChessNotation(source);
            } else {
                throw new InvalidMoveException("Movimento inválido, notação inválida.");
            }
        } catch (InvalidMoveException e) {
            System.out.println(e.getMessage());
            return null;
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

    public static String inputPromotion(Piece[][] board, Piece piece, Player currentPlayer) {
        String escolha = sc.nextLine();
        int destinationRow = piece.getPositionRow();
        int destinationColumn = piece.getPositionColumn();
        char color = piece.getColor();

        currentPlayer.removePiece(board[destinationRow][destinationColumn]);
        
        if (escolha.equals("Q")) {
            board[destinationRow][destinationColumn] = new Queen(color, destinationRow, destinationColumn);
            currentPlayer.addPiece(board[destinationRow][destinationColumn]);
            return "Rainha";
        } else if (escolha.equals("R")) {
            board[destinationRow][destinationColumn] = new Rook(color, destinationRow, destinationColumn);
            currentPlayer.addPiece(board[destinationRow][destinationColumn]);
            return "Torre";
        } else if (escolha.equals("B")) {
            board[destinationRow][destinationColumn] = new Bishop(color, destinationRow, destinationColumn);
            currentPlayer.addPiece(board[destinationRow][destinationColumn]);
            return "Bispo";
        } else if (escolha.equals("N")) {
            board[destinationRow][destinationColumn] = new Knight(color, destinationRow, destinationColumn);
            currentPlayer.addPiece(board[destinationRow][destinationColumn]);
            return "Cavalo";
        } else {
            System.out.println("Escolha inválida, escolha novamente: ");
            inputPromotion(board, piece, currentPlayer);
        }

        return escolha;

    }
}
