package ui;

import java.io.File;
import game.Input;

public class LoadUI {
    private static String reset = "\u001B[0m";
    private static String cyan = "\u001B[36m";
    private static String green = "\u001B[32m";
    private static String red = "\u001B[31m";

    public static void loadUI() {
        System.out.print("\033[H\033[2J");

        System.out.println(cyan + "=== Carregar Jogo ===" + reset);
        System.out.println();

        System.out.println("Escolha um arquivo para carregar:");

        // Percorre a pasta saves e lista os arquivos.
        File folder = new File("saves/");
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles.length == 0) {
            System.out.println(red + "Nenhum arquivo encontrado!" + reset);
            System.out.println("Pressione ENTER para voltar ao menu principal.");
            Input.readString();
            return;
        }

        int i = 1;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                System.out.println(green + i + " - " + reset + file.getName());
                i++;
            }
        }
        System.out.println(red + i + " - Voltar" + reset);

        String filename = "";
        while (true) {
            System.out.println();
            System.out.print("Opção: ");
            String option = Input.readString();
            try {
                int optionInt = Integer.parseInt(option);
                if (optionInt > 0 && optionInt <= listOfFiles.length) {
                    filename = listOfFiles[optionInt - 1].getName();
                    break;
                } else if (optionInt == listOfFiles.length + 1) {
                    return;
                } else {
                    System.out.println(red + "Opção inválida! Tente novamente." + reset);
                }
            } catch (NumberFormatException e) {
                System.out.println(red + "Por favor, insira um número válido." + reset);
            }
        }

        // Carrega o jogo.
        System.out.println(green + "Carregando jogo..." + reset);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(green + "Jogo carregado com sucesso!" + reset);

        game.PlayChess.playChessGame(null, null, null, 0,  filename, null);
    }
}
