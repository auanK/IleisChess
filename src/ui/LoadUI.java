package ui;

import java.io.File;
import game.Input;

public class LoadUI {
    public static void loadUI() {
        System.out.println("Escolha um arquivo para carregar:");
        // Percorre a pasta saves e lista os arquivos.
        File folder = new File("saves/");
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles.length == 0) {
            System.out.println("Nenhum arquivo encontrado!");
            System.out.println("Pressione ENTER para voltar ao menu principal.");
            Input.readString();
        }

        int i = 1;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                System.out.println(i + " - " + file.getName());
                i++;
            }
        }
        System.out.println(i + " - Voltar");

        String filename = "";
        while (true) {
            System.out.print("Opção: ");
            String option = Input.readString();
            int optionInt = Integer.parseInt(option);
            if (optionInt > 0 && optionInt <= listOfFiles.length) {
                filename = listOfFiles[optionInt - 1].getName();
                break;
            } else if (optionInt == listOfFiles.length + 1) {
                return;
            } else {
                System.out.println("Opção inválida!");
            }
        }   

        // Carrega o jogo.
        System.out.println("Carregando jogo...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Jogo carregado com sucesso!");

        game.PlayChess.playChessGame(null, null, null, 0, true, filename);

    }
}
