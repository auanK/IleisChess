package ui;

import game.Input;

public class HelpUI {
    private static String reset = "\u001B[0m";
    private static String cyan = "\u001B[36m";
    private static String yellow = "\u001B[33m";

    // Falta implementar.
    public static void helpUI() {
        UtilTools.clearConsole();
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
        Input.enter();
        
        return;
    }
}
