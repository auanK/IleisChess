package ui;

import board.EnPassantMate;
import game.PlayChess;
import game.Player;
import game.Input;
import pieces.Piece;

public class CustomScenarios {
    // Códigos de escape ANSI para cores no console
    static String reset = "\u001B[0m";
    static String cyan = "\u001B[36m";
    static String yellow = "\u001B[33m";
    static String green = "\u001B[32m";
    static String red = "\u001B[31m";

    public static void customScenarios() {
        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.println(cyan + "Menu de cenários personalizados" + reset);
            System.out.println("Escolha um cenário:");
            System.out.println(green + "1 - Mate com en passant");
            System.out.println("2 - Mate com roque");
            System.out.println("3 - Mate com promoção");
            System.out.println("4 - Empate por afogamento" + reset);
            System.out.println(red + "5 - Sair" + reset);
            System.out.print("Opção: ");
            String option = Input.readString();
            switch (option) {
                case "1":
                    EnPassantMate enPassantMate = new EnPassantMate();
                    Piece[][] board = enPassantMate.getBoard();

                    Player playerWhite = new Player('W');
                    Player playerBlack = new Player('B');

                    enPassantMate.assignPiecesToPlayers(playerWhite, playerBlack);
                    PlayChess.playChessGame(board, playerWhite, playerBlack, 1, false, null);
                    return;
                case "2":
                    System.out.println("Mate com roque");
                    return;
                case "3":
                    System.out.println("Mate com promoção");
                    return;
                case "4":
                    System.out.println("Empate por afogamento");
                    return;
                case "5":
                    System.out.println("Sair");
                    return;
                default:
                    System.out.println(red + "Opção inválida!" + reset);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }

        }

    }
}