package game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import draw.DrawType;
import logic.Exceptions;
import pieces.Piece;

// Classe responsável por salvar e carregar o jogo.
public class SaveGame {
    public static void saveGame(Piece[][] board, Player currentPlayer,
            Player opponent, ChessLog log, DrawType draw, boolean resign, String file) throws Exceptions {
        String fileName = "";
        String path = "saves/";

        // Lê o nome do arquivo.
        fileName = file + ".obj";

        // Cria o arquivo.
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(path + fileName))) {
            // Salva os jogadores e o tabuleiro.
            outputStream.writeObject(board);
            outputStream.writeObject(currentPlayer);
            outputStream.writeObject(opponent);
            outputStream.writeObject(log);
            outputStream.writeObject(draw);
            outputStream.writeBoolean(resign);
            

        } catch (IOException e) {
            throw new Exceptions("Erro ao salvar o jogo.");
        }

    }

    public static void loadGame(String filename) throws IOException, ClassNotFoundException {
        Piece[][] board;
        Player currentPlayer;
        Player opponent;
        Player playerWhite;
        Player playerBlack;
        ChessLog log;
        DrawType draw;
        boolean resign = false;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("saves/" + filename))) {
            // Carregar os jogadores e o tabuleiro
            board = (Piece[][]) inputStream.readObject();
            currentPlayer = (Player) inputStream.readObject();
            opponent = (Player) inputStream.readObject();
            log = (ChessLog) inputStream.readObject();
            draw = (DrawType) inputStream.readObject();

            resign = inputStream.readBoolean();

            playerWhite = currentPlayer;
            playerBlack = opponent;
            if (currentPlayer.getColor() != 'W') {
                playerWhite = opponent;
                playerBlack = currentPlayer;
            }

        } catch (IOException | ClassNotFoundException e) {
            throw e;
        }

        // Setar os atributos do PlayChess para começar o jogo.
        PlayChess.setAttributes(board, playerWhite, playerBlack, currentPlayer, opponent, log, draw, resign);

    }
}