package game;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import logic.Exceptions;

// Classe que implementa o log da partida.
public class ChessLog implements Serializable {
    private ArrayList<String> logMoves; // Array de movimentos.
    private ArrayList<String> logPositions; // Array de posições.

    // Construtor da classe.
    public ChessLog() {
        this.logMoves = new ArrayList<>();
        this.logPositions = new ArrayList<>();
    }

    // Retorna o array de movimentos.
    public ArrayList<String> getLog() {
        return logMoves;
    }

    // Retorna o array de posições.
    public ArrayList<String> getPositions() {
        return logPositions;
    }

    // Adiciona um movimento ao array de movimentos.
    public void addMove(String move) {
        logMoves.add(move);
    }

    // Adiciona uma posição ao array de posições.
    public void addPosition(String position) {
        logPositions.add(position);
    }

    // Adiciona um caractere ao último movimento.
    public void addChar(char c) {
        String lastMove = logMoves.get(logMoves.size() - 1);
        lastMove += c;
        logMoves.set(logMoves.size() - 1, lastMove);
    }

    // Retorna os últimos n movimentos.
    public String[] getLastMoves(int n) {
        int size = logMoves.size();

        if (n < 0 || n > size) {
            return null;
        }

        String[] lastMoves = new String[n];

        for (int i = 0; i < n; i++) {
            lastMoves[i] = logMoves.get(size - n + i);
        }

        return lastMoves;
    }

    // Salva o log da partida em um arquivo de texto.
    public void saveLog(String filename, String playerWhite, String playerBlack, String end) throws Exceptions {
        try {
            FileWriter fileWriter = new FileWriter(filename);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println("Resumo da partida: ");
            printWriter.println("Jogador das peças brancas: " + playerWhite);
            printWriter.println("Jogador das peças pretas: " + playerBlack);
            printWriter.println("A partida terminou em " + end);
            printWriter.println();

            printWriter.println("Log de movimentos: ");

            for (String move : logMoves) {
                printWriter.print(move + " ");
            }
            printWriter.println();

            printWriter.close();
        } catch (IOException e) {
            throw new Exceptions("Erro ao salvar o log da partida!");
        }
    }

    // Retorna o tamanho do array de movimentos.
    public int sizeLogMoves() {
        return logMoves.size();
    }

    // Retorna o tamanho do array de posições.
    public int sizeLogPositions() {
        return logPositions.size();
    }

    // Retorna uma String com a notação algébrica do movimento.
    public String parseChessNotation(int row, int column) {
        return (char) (column + 97) + String.valueOf(row + 1);
    }
}
