package ui;

import game.Input;

public class HelpUI {
    private static String reset = "\u001B[0m";
    private static String cyan = "\u001B[36m";
    private static String yellow = "\u001B[33m";

    // Menu de ajuda.
    public static void helpUI() {
        System.out.print("\033[H\033[2J");
        System.out.println(cyan + "Bem-vindo ao " + yellow + "IleisChess" + cyan + "!" + reset);
        System.out.println();
        System.out.println("Comandos:");
        System.out.println();
        System.out.println("Movimentar uma peça: ");
        System.out.println(yellow + "<posição inicial> <posição final>" + reset);
        System.out.println("Exemplo: " + yellow + "e2e4" + reset + " tenta mover a peça de e2 para e4.");

        System.out.println();

        System.out.println("Selecionar uma peça: " + yellow + "<posição inicial>" + reset);
        System.out.println("Seleciona a peça na posição especificada e mostra os movimentos possíveis.");
        System.out.println();
        System.out.println("Seleciona destino: " + yellow + "<posição final>" + reset);
        System.out.println("Seleciona o destino da peça selecionada.");
        System.out.println();
        System.out.println("Exemplo: ");
        System.out.println(yellow + "e2" + reset + " seleciona a peça na posição e2.");
        System.out.println(yellow + "e4" + reset + " seleciona o destino e4.");

        System.out.println();
        System.out.println(yellow + "draw" + reset + "- Solicita empate.");
        System.out.println(yellow + "resign" + reset + "- Desiste da partida.");
        System.out.println(yellow + "save" + reset + " - Salva o jog em qualquer momento da partida.");
        System.out.println(yellow + "exit" + reset + " - Sai do jogo.");

        System.out.println();
        System.out.println("- O log da partida é um arquivo txt que diz o nome dos jogadores, o resultado da partida e os movimentos que foram feitos. ");
        System.out.println();
        System.out.println("- Os logs da partida ficam salvos na pasta " + yellow + "logs" + reset + ".");

        System.out.println();
        System.out.println("- A cada dois movimentos, o jogo é salvo automaticamente no arquivo " + yellow
                + "recovery.obj" + reset + ".");

        Input.enter();

        return;
    }
}
