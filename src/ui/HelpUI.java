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

        System.out.println(cyan + "Como movimentar as peças:" + reset);
        System.out.println();
        System.out.println("Para movimentar as peças, digite a posição inicial e a posição final da peça que deseja mover.");
        System.out.println("Por exemplo, para mover o peão da posição a2 para a posição a4, digite " + yellow + "a2a4" + reset + ".");
        System.out.println("Se o movimento for válido, a peça será movida.");
        System.out.println();

        System.out.println("Outra forma de movimentar as peças é digitando a posição inicial da peça que deseja mover.");
        System.out.println("Por exemplo, para mover o peão da posição a2, digite " + yellow + "a2" + reset + ".");
        System.out.println("Se houver uma peça sua nessa posição, o jogo irá mostrar as posições para onde a peça pode se mover.");
        System.out.println("Para mover a peça para uma dessas posições, digite a posição final da peça que deseja mover.");
        System.out.println("Logo, para mover a peça selecionada para a posição a4, digite " + yellow + "a4" + reset + ".");
        System.out.println("Se o movimento for válido, a peça será movida.");
        System.out.println();


        System.out.println(cyan + "Comandos do jogo:" + reset);
        System.out.println();
        System.out.println(yellow + "draw" + reset + " - Solicita empate.");
        System.out.println(yellow + "resign" + reset + " - Desiste da partida.");
        System.out.println(yellow + "save" + reset + " - Salva o jog em qualquer momento da partida.");
        System.out.println(yellow + "exit" + reset + " - Sai do jogo.");
        System.out.println();

        System.out.println("Informações adicionais:");
        System.out.println();
        System.out.println("- O log da partida é um arquivo txt que diz o nome dos jogadores, o resultado da partida e os movimentos que foram feitos. ");
        System.out.println("- Os logs da partida ficam salvos na pasta " + yellow + "logs" + reset + ".");
        System.out.println("- A cada dois movimentos, o jogo é salvo automaticamente no arquivo " + yellow + "recovery.obj" + reset + ".");

        Input.enter();

        return;
    }
}
