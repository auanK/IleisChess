package ui;

import game.Input;

public class HelpUI {
    private static String reset = "\u001B[0m";
    private static String cyan = "\u001B[36m";
    private static String yellow = "\u001B[33m";
    // dprivate static String green = "\u001B[32m";
    // private static String red = "\u001B[31m";

    // Falta implementar.
    public static void helpUI() {
        System.out.print("\033[H\033[2J");
        System.out.println(cyan + "Bem-vindo ao " + yellow + "IleisChess" + cyan + "!" + reset);
        System.out.println();
        System.out.println("Como jogar:");
        System.out.println("1 - Após iniciar o jogo, você deve digitar o movimento que deseja fazer.");
        System.out.println();
        System.out.println("2 - O movimento deve ser digitado em dois formatos: ");
        System.out.println();
        System.out.println("Coordenadas completas, origem e destino, por exemplo: a2a4");
        System.out.println("Você selecionou a peça na casa a2 e tentará movimentá-la para a casa a4.");
        System.out.println();
        System.out.println("Coordenadas parciais, origem e destino, por exemplo: a4");
        System.out.println("Você selecionou a peça na casa a4, e o jogo irá mostrar as casas para onde você pode movimentá-la");
        System.out.println("logo após, você deve digitar a casa para onde deseja movimentar a peça.");

        System.out.println();
        System.out.println("Pressione qualquer tecla para voltar ao menu principal.");
        Input.readString();
        return;
    }
}
