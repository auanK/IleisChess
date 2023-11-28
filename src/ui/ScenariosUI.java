package ui;

import board.EnPassantMate;
import board.ThreeMates;
import game.ChessLog;
import game.Input;
import game.PlayChess;
import game.Player;
import pieces.Piece;

// Classe que implementa o menu de cenários personalizados.
public class ScenariosUI {
    private static String reset = "\u001B[0m";
    private static String cyan = "\u001B[36m";
    private static String green = "\u001B[32m";
    private static String red = "\u001B[31m";

    // Menu de cenários personalizados.
    public static void customScenarios() {
        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.println(cyan + "=== Menu de cenários personalizados ===" + reset);
            System.out.println("Escolha um cenário:");
            System.out.println(green + "1 - Mate com en passant");
            System.out.println("2 - Mate com roque");
            System.out.println("3 - Mate com promoção");
            System.out.println("4 - Empate por afogamento");
            System.out.println("5 - Três Mates");
            System.out.println();
            System.out.println(red + "6 - Sair" + reset);
            System.out.println();
            System.out.print("Opção: ");
            String option = Input.readString();
            switch (option) {
                case "1":
                    executeEnPassantMateScenario();
                    return;
                case "2":
                    executeCastlingMateScenario();
                    return;
                case "3":
                    executePromotionMateScenario();
                    return;
                case "4":
                    executeStalemateScenario();
                    return;
                case "5":
                    executeThreeMatesScenario();
                    return;
                case "6":
                    System.out.println("Sair");
                    return;
                default:
                    System.out.println(red + "Opção inválida!" + reset);
                    sleep(500);
            }
        }
    }

    // Inicia o cenário de mate com en passant.
    private static void executeEnPassantMateScenario() {
        EnPassantMate enPassantMate = new EnPassantMate();
        Piece[][] board = enPassantMate.getBoard();

        Player playerWhite = new Player('W');
        Player playerBlack = new Player('B');

        enPassantMate.assignPiecesToPlayers(playerWhite, playerBlack);
        PlayChess.playChessGame(board, playerWhite, playerBlack, 1,  null, null);
    }

    // Inicia o cenário de mate com roque.
    private static void executeCastlingMateScenario() {
        System.out.println("Mate com roque");
        System.out.println("Em desenvolvimento...");
        sleep(1000);   
    }

    // Inicia o cenário de mate com promoção.
    private static void executePromotionMateScenario() {
        System.out.println("Mate com promoção");
        System.out.println("Em desenvolvimento...");
        sleep(1000);

    }

    // Inicia o cenário de empate por afogamento.
    private static void executeStalemateScenario() {
        System.out.println("Empate por afogamento");
        System.out.println("Em desenvolvimento...");
        sleep(1000);
        
    }

    // Inicia o cenário de três mates.
    private static void executeThreeMatesScenario() {
        System.out.println("Três Mates");
        ThreeMates board = new ThreeMates();
        Piece[][] boardChoice = board.getBoard();

        Player playerWhite = new Player('W');
        Player playerBlack = new Player('B');

        board.assignPiecesToPlayers(playerWhite, playerBlack);
        ChessLog log = new ChessLog();
        log.addMove("InitialW");
        log.addMove("Pc6c7");
        log.addMove("Pf7f5");
        PlayChess.playChessGame(boardChoice, playerWhite, playerBlack, 0,  null, log);
    }

    // Pausa o programa por um tempo.
    private static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println("Erro ao dormir o programa.");
        }
    }
}
