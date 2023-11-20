package game;

import java.util.ArrayList;

public class ChessLog {
    private ArrayList<String> log;

    public ChessLog() {
        this.log = new ArrayList<String>();
    }

    public void add(String move) {
        this.log.add(move);
    }

    // Adiciona um char ao último movimento.
    public void addChar(char c) {
        String lastMove = this.log.get(this.log.size() - 1);
        lastMove += c;
        this.log.set(this.log.size() - 1, lastMove);
    }

    public void print() {
        for (String move : this.log) {
            System.out.print(move + ' ');
        }
        System.out.println();
    }

    public String parseChessNotation(int row, int column) {
        String chessNotation = "";
    
        chessNotation += (char) (column + 97);
        chessNotation += row + 1;
    
        return chessNotation;
    }
    


}
