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

// Classe que recebe a entrada do usuário.
public class UserInput {
    static Scanner sc = new Scanner(System.in);

    // Lê as coordenadas digitadas pelo usuário.
    public static int[] inputCoordinates(Piece[][] board, Player currentPlayer, Player opponent, Player playerWhite,
            Player playerBlack) {

        String source = sc.nextLine();

        // Verifica se o jogador quer sair do jogo.
        if (source.equals("exit")) {
            System.exit(0);
        }

        // Verifica se o jogador quer pedir empate.
        if (source.equals("draw")) {
            System.out.println("O jogador " + currentPlayer.getName() + " pediu empate, aceita? (y/n)");
            String draw = sc.nextLine();
            if (draw.equals("y")) {
                System.out.println("Empate!");
                System.exit(0);
            }
        }

        // Coordenadas de origem e destino.
        int[] coordinates = new int[4];

        try {
            // Verifica se a entrada seleciona uma peça ou um movimento.
            if (source.length() == 2) {
                // Verifica se a entrada é válida e imprime os movimentos possíveis.
                int[] coordinatesSource = parseChessNotation(source);
                ChessMove.isValidSource(board, coordinatesSource, currentPlayer, opponent);
                ChessUI.printValidMoves(board, coordinatesSource, currentPlayer, opponent, playerWhite, playerBlack);

                // Lê a posição de destino.
                String destination = sc.nextLine();

                // Verifica se o jogador quer selecionar outra peça.
                if (destination.equals("cancel")) {
                    return null;
                }

                // Verifica se a o a string de destino é válida.
                if (destination.length() != 2) {
                    throw new InvalidMoveException("Movimento inválido, formato de destino incorreto");
                }

                // Converte a notação de xadrez para coordenadas da matriz.
                int[] coordinatesDestination = parseChessNotation(destination);

                coordinates[0] = coordinatesSource[0];
                coordinates[1] = coordinatesSource[1];
                coordinates[2] = coordinatesDestination[0];
                coordinates[3] = coordinatesDestination[1];
            } else if (source.length() == 4) {
                // Converte a notação de xadrez para coordenadas da matriz.
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

    // Converte a notação de xadrez para coordenadas da matriz.
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

    // Lê a escolha de promoção do usuário.
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
}
