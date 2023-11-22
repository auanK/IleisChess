package game;

import java.util.Scanner;

import draw.DrawType;
import draw.Draws;
import logic.Exceptions;
import logic.MoveValidator;

import pieces.Piece;
import ui.BoardUI;

// Classe que implementa a entrada do usuário.
public class Input {
    private static Scanner sc = new Scanner(System.in);

    // Lê as coordenadas digitadas pelo usuário.
    public static int[] inputCoordinates(Piece[][] board, Player currentPlayer, Player opponent, ChessLog log)
            throws Exceptions {
        String source = sc.nextLine();

        // Verifica se o jogador quer solicitar um empate.
        if (source.equals("draw")) {
            // Verifica se é um empate por tripla repetição.
            if (Draws.isThreefoldRepetition(log.getPositions())) {
                throw new Exceptions("Draw Threefold Repetition!");
            }
            // Lida com a solicitação de empate.
            handleDrawRequest(currentPlayer);
        }

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

    // Lida com solicitação de empate.
    // Lida com a seleção de peças.
    private static void handlePieceSelection(Piece[][] board, Player currentPlayer, Player opponent, int[] coordinates,
            String source) throws Exceptions {
        // Verifica se a entrada é válida e imprime os movimentos possíveis.
        int[] coordinatesSource = parseChessNotation(source);
        MoveValidator.isValidSource(board, coordinatesSource, currentPlayer, opponent);
        BoardUI.printValidMoves(board, coordinatesSource, currentPlayer, opponent);

        // Lê a posição de destino.
        String destination = sc.nextLine();

        // Verifica se o jogador quer selecionar outra peça.
        if (destination.equals("cancel")) {
            throw new Exceptions("Seleção cancelada.");
        } else {
            // Verifica se a string de destino é válida.
            if (destination.length() != 2) {
                throw new Exceptions("Movimento inválido, formato de destino incorreto!");
            }

            // Converte a notação de xadrez para coordenadas da matriz.
            int[] coordinatesDestination = parseChessNotation(destination);

            coordinates[0] = coordinatesSource[0];
            coordinates[1] = coordinatesSource[1];
            coordinates[2] = coordinatesDestination[0];
            coordinates[3] = coordinatesDestination[1];
        }
    }

    private static void handleDrawRequest(Player currentPlayer) throws Exceptions {
        System.out.println("O(a) jogador(a) " + currentPlayer.getName() + " pediu empate, aceita? (y/n)");
        String draw = sc.nextLine();

        while (!isValidChoice(draw)) {
            System.out.println("Opção inválida! Digite novamente: ");
            draw = sc.nextLine();
        }

        if (draw.equals("Y") || draw.equals("y")) {
            throw new Exceptions("Draw!");
        } else {
            throw new Exceptions("Empate recusado!");
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

    // Lê a escolha de salvar o log da partida.
    public static void inputSaveLog(String playerWhite, String playerBlack, ChessLog log,
            DrawType draw, Player currentPlayer) {
        System.out.println("Deseja salvar o log da partida? (y/n)");

        // Lê a escolha do usuário e enquanto não for válida, lê novamente.
        String option = Input.readString();
        while (!isValidChoice(option)) {
            System.out.println("Opção inválida! Digite novamente: ");
            option = Input.readString();
        }

        // Se o usuário escolheu salvar o log, tenta salvar.
        if (option.equals("Y") || option.equals("y")) {
            // Enquanto não conseguir salvar, lê novamente.
            boolean saved = false;
            while (!saved) {
                // Obtem a string que representa o fim da partida.
                String end = "";
                if (draw.isDraw()) {
                    end = draw.getDrawTypeString();
                    System.out.println(end);
                } else {
                    end = " xeque-mate para " + currentPlayer.getName() + "!";
                }

                // Obtem a hora atual.
                String hour = java.time.LocalTime.now().toString().substring(0, 8).replace(':', '-');

                // Obtem o nome do arquivo.
                String filename = "log/" + hour + "_" + playerWhite + "vs" + playerBlack + ".txt";

                // Tenta salvar o log.
                try {
                    log.saveLog(filename, playerWhite, playerBlack, end);
                    saved = true;
                } catch (Exceptions e) {
                    System.out.println(e.getMessage());
                }

            }
        }
    }

    // Verifica se uma escolha é válida.
    private static boolean isValidChoice(String option) {
        return option.equals("Y") || option.equals("y") || option.equals("N") || option.equals("n");
    }

    // Lê uma string.
    public static String readString() {
        return sc.nextLine();
    }
}
