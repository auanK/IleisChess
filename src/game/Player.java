package game;

import java.util.ArrayList;
import java.util.List;
import pieces.Piece;

public class Player {
    private String name;
    private List<Piece> pieces;
    private List<Piece> capturedPieces;

    public Player(String name) {
        this.name = name;
        this.pieces = new ArrayList<>();
        this.capturedPieces = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public List<Piece> getCapturedPieces() {
        return capturedPieces;
    }

    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    public void removePiece(Piece piece) {
        pieces.remove(piece);
    }

    public void addCapturedPiece(Piece piece) {
        capturedPieces.add(piece);
    }
}
