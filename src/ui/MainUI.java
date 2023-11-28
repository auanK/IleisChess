package ui;

import board.ChessBoard;
import game.PlayChess;
import game.Player;
import game.Input;
import pieces.Piece;

// Classe que implementa a interface do menu principal.
public class MainUI {
    private static String reset = "\u001B[0m";
    private static String cyan = "\u001B[36m";
    private static String yellow = "\u001B[33m";
    private static String green = "\u001B[32m";
    private static String red = "\u001B[31m";

    // Menu principal.
    public static void ui(String[] args) {
        while (true) {
            System.out.print("\033[H\033[2J");

            System.out.println(cyan + "=== Bem-vindo ao " + yellow + "IleisChess" + cyan + "! ===" + reset);
            System.out.println();

            System.out.println("Digite o que deseja fazer:");
            System.out.println();

            System.out.println(green + "1 - Novo jogo");
            System.out.println("2 - Carregar jogo");
            System.out.println("3 - Cenarios personalizados");
            System.out.println("4 - Ajuda");
            System.out.println();
            System.out.println(red + "5 - Sair" + reset);

            System.out.println();
            System.out.print("Opção: ");
            String option = Input.readString();

            switch (option) {
                case "1":
                    ChessBoard chessBoard = new ChessBoard();
                    Piece[][] board = chessBoard.getBoard();

                    Player playerWhite = new Player('W');
                    Player playerBlack = new Player('B');
                    chessBoard.assignPiecesToPlayers(playerWhite, playerBlack);

                    PlayChess.playChessGame(board, playerWhite, playerBlack, 0, null, null);
                    break;
                case "2":
                    LoadUI.loadUI();
                    break;
                case "3":

                    ScenariosUI.customScenarios();
                    break;
                case "4":
                    HelpUI.helpUI();
                    break;
                case "5":
                    System.out.println("Fechando o jogo...");

                    return;
                default:
                    System.out.println(red + "Opção inválida!" + reset);
                    sleep(750);
            }

        }
    }

    // Pausa o programa por um tempo.
    private static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println("Erro ao pausar o jogo: " + e.getMessage());
        }
    }
}
