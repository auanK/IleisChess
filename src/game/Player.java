package game;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import pieces.Piece;

// Classe que implementa o jogador.
public class Player implements Serializable {
    private String name;                // Nome do jogador
    private char color;                 // Cor do jogador
    private boolean isCheck;            // Flag que indica se o jogador está em check
    private List<Piece> pieces;         // Lista de peças do jogador
    private ArrayList<Piece> capturedPieces; // Lista de peças capturadas


    // Construtor da classe.
    public Player(String name, char color) {
        this.name = name;
        this.color = color;
        this.pieces = new ArrayList<>();
        this.capturedPieces = new ArrayList<>();
    }

    public Player(char color) {
        this.color = color;
        this.pieces = new ArrayList<>();
        this.capturedPieces = new ArrayList<>();
    }

    // Retorna o nome do jogador.
    public String getName() {
        return name;
    }

    // Define o nome do jogador.
    public void setName(String name) {
        this.name = name;
    }

    // Retorna a cor do jogador.
    public char getColor() {
        return color;
    }

    // Define a cor do jogador.
    public void setColor(char color) {
        this.color = color;
    }

    // Retorna a lista de peças.
    public List<Piece> getPieces() {
        return pieces;
    }

    // Retorna a lista de peças capturadas.
    public List<Piece> getCapturedPieces() {
        return capturedPieces;
    }

    // Adiciona uma peça à lista de peças.
    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    // Remove uma peça da lista de peças.
    public void removePiece(Piece piece) {
        pieces.remove(piece);
    }

    // Adiciona uma peça à lista de peças capturadas.
    public void addCapturedPiece(Piece piece) {
        capturedPieces.add(piece);
    }

    // Remove uma peça da lista de peças capturadas.
    public boolean isCheck() {
        return isCheck;
    }

    // Define se o jogador está em check.
    public void setCheck(boolean check) {
        isCheck = check;
    }
}
