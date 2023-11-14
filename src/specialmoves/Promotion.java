package specialmoves;

import pieces.Bishop;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;
import java.util.Scanner;

public class Promotion {
    public static Scanner sc = new Scanner(System.in);

    public static String inputPromotion(Piece[][] board, Piece piece) {
        String choice = sc.nextLine();
        int destinationRow = piece.getPositionRow();
        int destinationColumn = piece.getPositionColumn();
        char color = piece.getColor();

        if (choice.equals("Q")) {
            board[destinationRow][destinationColumn] = new Queen(color, destinationRow, destinationColumn);
            choice = "Rainha";
        } else if (choice.equals("R")) {
            board[destinationRow][destinationColumn] = new Rook(color, destinationRow, destinationColumn);
            choice = "Torre";
        } else if (choice.equals("B")) {
            board[destinationRow][destinationColumn] = new Bishop(color, destinationRow, destinationColumn);
            choice = "Bispo";
        } else if (choice.equals("N")) {
            board[destinationRow][destinationColumn] = new Knight(color, destinationRow, destinationColumn);
            choice = "Cavalo";
        } else {
            System.out.println("Escolha inválida, escolha novamente: ");
            inputPromotion(board, piece);
        }
        return choice;
    }

    public static boolean isPromotionSquare(Piece piece) {
        if (piece == null) {
            return false;
        }

        if (piece.getColor() == 'W' && piece.getPositionRow() == 7 && piece instanceof Pawn) {
            return true;
        } else if (piece.getColor() == 'B' && piece.getPositionRow() == 0 && piece instanceof Pawn) {
            return true;
        }
        return false;
    }

}
