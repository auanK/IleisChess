package specialmoves;

import java.util.Scanner;

import game.ChessLog;
import game.Player;
import pieces.Bishop;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

public class Promotion {
    private static final Scanner sc = new Scanner(System.in);
    private static final String QUEEN = "Q";
    private static final String ROOK = "R";
    private static final String BISHOP = "B";
    private static final String KNIGHT = "K";

    // Promove um peão.
    public static void promotion(Piece[][] board, Piece piece, Player currentPlayer, ChessLog log) {
        // Verifica se o peão está na posição de promoção.
        if (!isPromotion(piece)) {
            return;
        }

        // Imprime as peças disponíveis para promoção e lê a escolha do usuário.
        System.out.println("Escolha a peça para promoção (Q, R, B, N): ");
        String choice = inputPromotion(board, piece);

        // Remove o peão da lista de peças do jogador atual e adiciona a peça escolhida.
        currentPlayer.removePiece(piece);
        Piece promotedPiece = board[piece.getPositionRow()][piece.getPositionColumn()];
        currentPlayer.addPiece(promotedPiece);

        // Imprime a mensagem de promoção.
        System.out.println();
        System.out.println("Peão promovido para " + choice + "!");
        System.out.println();

        // Adiciona a promoção ao log.
        log.addChar(promotedPiece.getLabel());
    }

    // Lê a escolha do usuário e cria a peça escolhida.
    public static String inputPromotion(Piece[][] board, Piece piece) {
        // Lê a escolha do usuário.
        String choice = sc.nextLine();

        // Salva as coordenadas da peça e sua cor.
        int destinationRow = piece.getPositionRow();
        int destinationColumn = piece.getPositionColumn();
        char color = piece.getColor();
        char colorSquare = piece.getColorSquare();

        // Verifica se a escolha é válida e caso seja, cria a peça escolhida.
        String promotionFor = "";
        do {
            switch (choice) {
                case QUEEN:
                    board[destinationRow][destinationColumn] = new Queen(color, colorSquare, destinationRow, destinationColumn);
                    promotionFor = "Rainha";
                    break;
                case ROOK:
                    board[destinationRow][destinationColumn] = new Rook(color, colorSquare, destinationRow, destinationColumn);
                    promotionFor = "Torre";
                    break;
                case BISHOP:
                    board[destinationRow][destinationColumn] = new Bishop(color, colorSquare, destinationRow, destinationColumn);
                    promotionFor = "Bispo";
                    break;
                case KNIGHT:
                    board[destinationRow][destinationColumn] = new Knight(color, colorSquare, destinationRow, destinationColumn);
                    promotionFor = "Cavalo";
                    break;
                default:
                    System.out.println("Escolha inválida, escolha novamente: ");
                    choice = sc.nextLine();
                    break;
            }
        } while (!isValidChoice(choice));

        return promotionFor;
    }

    // Verifica se a escolha do usuário é válida.
    private static boolean isValidChoice(String choice) {
        return QUEEN.equals(choice) || ROOK.equals(choice) || BISHOP.equals(choice) || KNIGHT.equals(choice);
    }

    // Verifica se o peão está na posição de promoção.
    public static boolean isPromotion(Piece piece) {
        if (piece == null || !(piece instanceof Pawn)) {
            return false;
        }

        if (piece.getColor() == 'W' && piece.getPositionRow() == 7) {
            return true;
        } else if (piece.getColor() == 'B' && piece.getPositionRow() == 0) {
            return true;
        }
        return false;
    }
}
