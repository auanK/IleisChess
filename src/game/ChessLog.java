package game;

import java.util.ArrayList;

public class ChessLog {
    private ArrayList<String> log;
    private ArrayList<String> positions;

    public ChessLog() {
        this.log = new ArrayList<>();
        this.positions = new ArrayList<>();
    }

    // Getters e Setters para log
    public ArrayList<String> getLog() {
        return log;
    }

    public void setLog(ArrayList<String> log) {
        this.log = log;
    }

    // Getters e Setters para positions
    public ArrayList<String> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<String> positions) {
        this.positions = positions;
    }

    // Método para adicionar um movimento ao log
    public void add(String move) {
        log.add(move);
    }

    // Método para adicionar um caractere ao último movimento
    public void addChar(char c) {
        String lastMove = log.get(log.size() - 1);
        lastMove += c;
        log.set(log.size() - 1, lastMove);
    }

    // Método para retornar os últimos n movimentos
    public String[] getLastMoves(int n) {
        int size = log.size();

        if (n < 0 || n > size) {
            return null;
        }

        String[] lastMoves = new String[n];

        for (int i = 0; i < n; i++) {
            lastMoves[i] = log.get(size - n + i);
        }

        return lastMoves;
    }

    // Método para adicionar uma posição ao array de posições
    public void addPosition(String position) {
        positions.add(position);
    }

    // Método para obter o tamanho do log
    public int size() {
        return log.size();
    }

    // Método para imprimir os movimentos
    public void print() {
        for (String move : log) {
            System.out.print(move + ' ');
        }
        System.out.println();
    }

    // Método para analisar a notação de xadrez com base na linha e coluna
    public String parseChessNotation(int row, int column) {
        return (char) (column + 97) + String.valueOf(row + 1);
    }
}
