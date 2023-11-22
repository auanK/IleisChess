package game;

import java.io.*;

import draw.DrawType;
import pieces.Piece;

// Classe responsável por salvar e carregar o jogo.
public class SaveGame {
    public static void saveGame(Piece[][] board, Player currentPlayer,
            Player opponent, ChessLog log, DrawType draw) {
        String fileName = "";
        String path = "saves/";

        // Lê o nome do arquivo.
        System.out.print("Nome do arquivo: ");
        fileName = Input.readString() + ".obj";

        // Cria o arquivo.
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(path + fileName))) {
            // Salva os jogadores e o tabuleiro.
            outputStream.writeObject(board);
            outputStream.writeObject(currentPlayer);
            outputStream.writeObject(opponent);
            outputStream.writeObject(log);
            outputStream.writeObject(draw);

            System.out.println("Salvando jogo...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Jogo salvo com sucesso!");

        } catch (IOException e) {
            System.out.println("Erro ao salvar o jogo: " + e.getMessage());
        }

    }

    public static void loadGame(String filename) throws IOException, ClassNotFoundException {
        Piece[][] board;
        Player currentPlayer;
        Player opponent;
        Player playerWhite;
        Player playerBlack;
        ChessLog log;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("saves/" + filename))) {
            // Carregar os jogadores e o tabuleiro
            board = (Piece[][]) inputStream.readObject();
            currentPlayer = (Player) inputStream.readObject();
            opponent = (Player) inputStream.readObject();
            log = (ChessLog) inputStream.readObject();

            playerWhite = currentPlayer;
            playerBlack = opponent;
            if (currentPlayer.getColor() != 'W') {
                playerWhite = opponent;
                playerBlack = currentPlayer;
            }

        } catch (IOException | ClassNotFoundException e) {
            throw e;
        }

        PlayChess.setAttributes(board, playerWhite, playerBlack, currentPlayer, opponent, log);
    }
}