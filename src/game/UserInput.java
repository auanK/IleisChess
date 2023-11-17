package game;

import java.util.Scanner;

import board.ChessUI;
import logic.Exceptions;
import logic.MoveValidator;
import pieces.Piece;

// Classe que implementa a entrada do usuário.
public class UserInput {
    private static Scanner sc = new Scanner(System.in);

    // Lê as coordenadas digitadas pelo usuário.
    public static int[] inputCoordinates(Piece[][] board, Player currentPlayer, Player opponent, Player playerWhite,
            Player playerBlack) throws Exceptions {
        String source = sc.nextLine();

        // Verifica comandos especiais
        handleSpecialCommands(source, currentPlayer);

        // Coordenadas de origem e destino.
        int[] coordinates = new int[4];

        // Tenta converter a notação de xadrez para coordenadas da matriz.
        try {
            // Verifica se a entrada seleciona uma peça ou um movimento.
            if (source.length() == 2) {
                handlePieceSelection(board, currentPlayer, opponent, coordinates, source);
            } else if (source.length() == 4) {
                // Converte a notação de xadrez para coordenadas da matriz.
                coordinates = parseChessNotation(source);
            } else {
                throw new Exceptions("Movimento inválido, notação inválida.");
            }
        } catch (Exceptions e) {
            throw e;
        }

        return coordinates;
    }

    // Lida com comandos especiais como 'exit' e 'draw'.
    private static void handleSpecialCommands(String command, Player currentPlayer) throws Exceptions {
        if (command.equals("exit")) {
            System.exit(0);
        } else if (command.equals("draw")) {
            handleDrawRequest(currentPlayer);
        }
    }

    // Lida com solicitação de empate.
    private static void handleDrawRequest(Player currentPlayer) throws Exceptions {
        System.out.println("O(a) jogador(a) " + currentPlayer.getName() + " pediu empate, aceita? (y/n)");
        String draw = sc.nextLine();
        if (draw.equals("y")) {
            throw new Exceptions("Draw!");
        } else {
            throw new Exceptions("Empate recusado!");
        }
    }

    // Lida com a seleção de peças.
    private static void handlePieceSelection(Piece[][] board, Player currentPlayer, Player opponent, int[] coordinates,
            String source) throws Exceptions {
        // Verifica se a entrada é válida e imprime os movimentos possíveis.
        int[] coordinatesSource = parseChessNotation(source);
        MoveValidator.isValidSource(board, coordinatesSource, currentPlayer, opponent);
        ChessUI.printValidMoves(board, coordinatesSource, currentPlayer, opponent);

        // Lê a posição de destino.
        String destination = sc.nextLine();

        // Verifica se o jogador quer selecionar outra peça.
        if (destination.equals("cancel")) {
            coordinates = null;
        } else {
            // Verifica se a string de destino é válida.
            if (destination.length() != 2) {
                throw new Exceptions("Movimento inválido, formato de destino incorreto");
            }

            // Converte a notação de xadrez para coordenadas da matriz.
            int[] coordinatesDestination = parseChessNotation(destination);

            coordinates[0] = coordinatesSource[0];
            coordinates[1] = coordinatesSource[1];
            coordinates[2] = coordinatesDestination[0];
            coordinates[3] = coordinatesDestination[1];
        }
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

    // Lê o nome dos jogadores.
    public static void inputName(Player playerWhite, Player playerBlack) {
        System.out.println("Digite o nome do(a) jogador(a) das peças brancas: ");
        playerWhite.setName(sc.nextLine());

        System.out.println("Digite o nome do(a) jogador(a) das peças pretas: ");
        playerBlack.setName(sc.nextLine());
    }
}
