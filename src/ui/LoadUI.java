package ui;

import java.io.File;
import game.Input;

// Classe que implementa a interface do menu de carregar jogo.
public class LoadUI {
    private static final String reset = "\u001B[0m";
    private static final String cyan = "\u001B[36m";
    private static final String green = "\u001B[32m";
    private static final String yellow = "\u001B[33m";
    private static final String red = "\u001B[31m";

    // Menu de carregar jogo.
    public static void loadUI() {
        System.out.print("\033[H\033[2J");

        System.out.println(cyan + "=== Carregar Jogo ===" + reset);
        System.out.println();

        System.out.println("Escolha um arquivo para carregar:");

        File folder = new File("saves/");
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles.length == 0) {
            System.out.println(red + "Nenhum arquivo encontrado!" + reset);
            System.out.println("Pressione ENTER para voltar ao menu principal.");
            Input.readString();
            return;
        }

        displayFileOptions(listOfFiles);
        System.out.println(red + (listOfFiles.length + 1) + " - Deletar arquivo" + reset);
        System.out.println();
        System.out.println(red + (listOfFiles.length + 2) + " - Voltar ao menu principal" + reset);

        String filename = getSelectedFileName(listOfFiles);
        if (filename == null) {
            return;
        }

        // Carrega o jogo.
        System.out.println(green + "Carregando jogo..." + reset);
        sleep(500);
        System.out.println(green + "Jogo carregado com sucesso!" + reset);

        game.PlayChess.playChessGame(null, null, null, 0, filename, null);
    }

    private static void displayFileOptions(File[] listOfFiles) {
        int i = 1;
        for (File file : listOfFiles) {
            System.out.println(green + i + " - " + reset + file.getName());
            i++;
        }
    }

    private static String getSelectedFileName(File[] listOfFiles) {
        while (true) {
            System.out.println();
            System.out.print("Opção: ");
            String option = Input.readString();

            try {
                int optionInt = Integer.parseInt(option);
                if (optionInt > 0 && optionInt <= listOfFiles.length) {
                    return listOfFiles[optionInt - 1].getName();
                } else if (optionInt == listOfFiles.length + 1) {
                    handleDeleteOption(listOfFiles);
                    return null;
                } else if (optionInt == listOfFiles.length + 2) {
                    return null;
                } else {
                    System.out.println(red + "Opção inválida! Tente novamente." + reset);
                }
            } catch (NumberFormatException e) {
                System.out.println(red + "Por favor, insira um número válido." + reset);
            }
        }
    }

    private static void handleDeleteOption(File[] listOfFiles) {
        System.out.println();
        System.out.println(red + "Digite o numero do arquivo que deseja deletar " + yellow + "(0 para cancelar):" + reset);
        System.out.print("Opção: ");
        String optionDelete = Input.readString();

        boolean delete = false;
        while (!delete) {
            try {
                int optionDeleteInt = Integer.parseInt(optionDelete);
                if (optionDeleteInt == 0) {
                    return;
                }
                if (optionDeleteInt > 0 && optionDeleteInt <= listOfFiles.length) {
                    listOfFiles[optionDeleteInt - 1].delete();
                    delete = true;
                    System.out.println(green + "Arquivo deletado com sucesso!" + reset);
                    sleep(500);
                } else {
                    System.out.print("\033[H\033[2J");
                    System.out.println(red + "Opção inválida! Tente novamente." + reset);
                    System.out.print(red + "Digite o numero do arquivo que deseja deletar: " + reset);
                    displayFileOptions(listOfFiles);
                    optionDelete = Input.readString();
                }
            } catch (NumberFormatException e) {
                System.out.println(red + "Por favor, insira um número válido." + reset);
                System.out.println(red + "Digite o numero do arquivo que deseja deletar " + yellow + "(0 para cancelar):" + reset);
                System.out.println("Opção: ");
                optionDelete = Input.readString();
            }
        }
    }

    private static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println("Erro ao dormir o programa.");
        }
    }
}
