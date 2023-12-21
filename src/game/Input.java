package game;

import java.util.Scanner;

import draw.DrawType;
import draw.Draws;
import logic.Exceptions;
import logic.MoveValidator;

import pieces.Piece;
import ui.BoardUI;

// Classe que implementa a interação do usuário com o jogo.
public class Input {
    private static Scanner sc = new Scanner(System.in);

    private static String reset = "\u001B[0m";
    private static String cyan = "\u001B[36m";
    private static String red = "\u001B[31m";
    private static String yellow = "\u001B[33m";
    private static String green = "\u001B[32m";

    // Lê as coordenadas de origem e destino do movimento.
    public static int[] inputCoordinates(Piece[][] board, Player currentPlayer, Player opponent, ChessLog log)
            throws Exceptions {
        String source = inputString();

        // Verifica se o jogador quer solicitar um empate.
        if (source.equals("draw")) {
            // Se for empate por triplo movimento, não é necessário pedir confirmação.
            if (Draws.isThreefoldRepetition(log.getPositions())) {
                throw new Exceptions("Draw Threefold Repetition!");
            }

            // Caso contrário, pede confirmação.
            inputDraw(currentPlayer);
        }

        // Verifica se o jogador quer desistir.
        if (source.equals("resign")) {
            throw new Exceptions("Withdraw!");
        }

        // Verifica se o jogador quer salvar o jogo.
        if (source.equals("save")) {
            throw new Exceptions("Save!");
        }

        // Verifica se o jogador quer sair do jogo.
        if (source.equals("exit")) {
            throw new Exceptions("Exit!");
        }

        // Coordenadas de origem e destino.
        int[] coordinates = new int[4];

        // Tenta converter a notação de xadrez para coordenadas da matriz.
        try {
            // Se for uma seleção de peça, trata a seleção.
            if (source.length() == 2) {
                handlePieceSelection(board, currentPlayer, opponent, coordinates, source);

                // Se for uma seleção de peça e movimento, converte a notação de xadrez para
                // coordenadas da matriz.
            } else if (source.length() == 4) {
                coordinates = parseChessNotation(source);

                // Se não for nenhuma das opções acima, é uma notação inválida.
            } else {
                throw new Exceptions("Movimento inválido, notação inválida.");
            }
        } catch (Exceptions e) {
            throw e;
        }

        // Retorna as coordenadas.
        return coordinates;
    }

    // Lida com a seleção de peças.
    private static void handlePieceSelection(Piece[][] board, Player currentPlayer, Player opponent, int[] coordinates,
            String source) throws Exceptions {
        // Converte a notação de xadrez para coordenadas da matriz.
        int[] coordinatesSource = parseChessNotation(source);

        // Verifica se é uma seleção válida.
        MoveValidator.isValidSource(board, coordinatesSource, currentPlayer, opponent);

        // Caso seja, imprime os movimentos válidos.
        BoardUI.printValidMoves(board, coordinatesSource, currentPlayer, opponent);

        // Lê a posição de destino.
        String destination = sc.nextLine();

        // Verifica se o jogador quer cancelar a seleção ou mover a peça.
        if (destination.equals("cancel")) {
            throw new Exceptions("Seleção cancelada.");
        } else {
            // Se o destino for diferente de 2, é um destino inválido.
            if (destination.length() != 2) {
                throw new Exceptions("Movimento inválido, formato de destino incorreto!");
            }

            // Converte a notação de xadrez para coordenadas da matriz.
            int[] coordinatesDestination = parseChessNotation(destination);

            // Seta as coordenadas.
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

    // Lê a escolha de empate.
    private static void inputDraw(Player currentPlayer) throws Exceptions {
        System.out.println();
        System.out.print("O(a) jogador(a) " + cyan + currentPlayer.getName() + reset + " pediu empate, aceita? " + yellow + "(y/n)" + reset + " ");

        String choice = readChoice();

        if (choice.equals("Y") || choice.equals("y")) {
            throw new Exceptions("Draw!");
        } else {
            throw new Exceptions("Empate recusado!");
        }
    }

    // Lê e trata a escolha de desistir.
    public static void inputResign() {
        System.out.println();
        System.out.print("Deseja mesmo desistir?" + yellow + " (y/n) " + reset);

        String option = readChoice();

        if (option.equals("Y") || option.equals("y")) {
            PlayChess.setResign(true);
        }
    }

    // Lê e trata a escolha de salvar o log da partida.
    public static void inputSaveLog(String playerWhite, String playerBlack, ChessLog log,
            DrawType draw, boolean resign, Player loser) {
        System.out.println();
        System.out.print(cyan + "Deseja salvar o log da partida? " + yellow + "(y/n)" + reset + " ");

        // Lê a escolha do usuário.
        String option = readChoice();

        boolean saved = false;

        // Se o usuário escolheu salvar o log, tenta salvar.
        if (option.equals("Y") || option.equals("y")) {
            // Obtem a string que representa o fim da partida.
            String end = "";
            if (resign) {
                end = " o(a) jogador(a) " + loser.getName() + " desistindo!";
            } else if (draw.isDraw()) {
                end = draw.getDrawTypeString();
            } else {
                end = "xeque-mate contra " + loser.getName() + "!";
            }

            // Obtem a hora atual.
            String hour = java.time.LocalTime.now().toString().substring(0, 8).replace(':', '-');

            // Obtem o nome do arquivo.
            String filename = "log/" + hour + "_" + playerWhite + "vs" + playerBlack + ".txt";

            System.out.println("Salvando o log...");
            sleep(500);

            // Tenta salvar o log.
            try {
                log.saveLog(filename, playerWhite, playerBlack, end);
                saved = true;
            } catch (Exceptions e) {
                System.out.println(e.getMessage());
            }

        } else {
            return;
        }

        // Se o log foi salvo, imprime a mensagem de sucesso.
        if (saved) {
            System.out.println(green + "Log salvo com sucesso!" + reset);
        } else {
            System.out.println(red + "Erro ao salvar o log!" + reset);
        }
    }

    // Lê e trata a escolha de salvar o jogo.
    public static void inputSaveGame(Piece[][] board, Player currentPlayer, Player opponent, ChessLog log,
            DrawType draw, boolean resign) {
        System.out.println();
        System.out.print(cyan + "Deseja salvar o jogo? " + yellow + "(y/n)" + reset + " ");

        // Lê a escolha do usuário.
        String option = readChoice();

        boolean saved = false;

        // Se o usuário escolheu salvar o jogo, tenta salvar.
        System.out.println();
        System.out.print("Digite o nome do arquivo: ");
        if (option.equals("Y") || option.equals("y")) {
            String file = inputString();

            System.out.println("Salvando o jogo...");
            sleep(750);

            try {
                SaveGame.saveGame(board, currentPlayer, opponent, log, draw, resign, file);
                saved = true;
            } catch (Exceptions e) {
                sleep(750);
                System.out.println(e.getMessage());
            }
        } else {
            return;
        }

        // Se o jogo foi salvo, imprime a mensagem de sucesso.
        if (saved) {
            System.out.println(green + "Jogo salvo com sucesso!" + reset);
            sleep(750);
        } else {
            System.out.println(red + "Erro ao salvar o jogo!" + reset);
            sleep(750);
        }

    }

    // Lê a escolha de sair do jogo.
    public static boolean inputExit() {
        System.out.println();
        System.out.print(red + "Deseja mesmo sair? (O progresso não salvo pode ser encontrado no recovery.obj)" + yellow + " (y/n)" + reset + " ");

        String option = readChoice();

        if (option.equals("Y") || option.equals("y")) {
            return true;
        } else {
            return false;
        }
    }

    // Lê o nome dos jogadores.
    public static void inputName(Player playerWhite, Player playerBlack) {
        System.out.println();
        System.out.print( "Digite o nome do(a)" + cyan + " jogador(a)" + reset + " das peças" + cyan + " brancas" + reset + ": ");
        playerWhite.setName(inputString());
        System.out.println();

        System.out.println();
        System.out.print("Digite o nome do(a)" + yellow + " jogador(a)" + reset + " das peças" + yellow + " pretas" + reset + ": ");
        playerBlack.setName(inputString());
        System.out.println();
    }

    // Lê uma string.
    public static String inputString() {
        return sc.nextLine();
    }

    // Verifica se uma escolha é válida (y/n).
    private static boolean isValidChoice(String option) {
        return option.equals("Y") || option.equals("y") || option.equals("N") || option.equals("n");
    }

    // Imprime o erro de escolha inválida.
    public static void invalidChoice() {
        System.out.println(red + "Opção inválida! Tente novamente." + reset);
    }

    // Lê uma escolha (y/n).
    public static String readChoice() {
        String choice = sc.nextLine();
        while (!isValidChoice(choice)) {
            invalidChoice();
            choice = sc.nextLine();
        }
        return choice;
    }

    // Enter para continuar.
    public static void enter() {
        System.out.println();
        System.out.print("Pressione enter para continuar...");
        sc.nextLine();
    }

    // Pausa o jogo por um tempo.
    private static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println("Erro ao pausar o jogo: " + e.getMessage());
        }
    }
}
