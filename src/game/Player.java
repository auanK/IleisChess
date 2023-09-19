package game;

import java.util.ArrayList;
import java.util.List;
import pieces.Piece;

/**
 * Classe que representa um jogador em um jogo de xadrez.
 */
public class Player {

    private String name; // Nome do jogador
    private List<Piece> pieces; // Lista de peças do jogador
    private List<Piece> capturedPieces; // Lista de peças capturadas pelo jogador

    /**
     * Construtor da classe Player.
     * 
     * @param name O nome do jogador.
     */
    public Player(String name) {
        this.name = name;
        this.pieces = new ArrayList<>(); // Inicializa a lista de peças do jogador
        this.capturedPieces = new ArrayList<>(); // Inicializa a lista de peças capturadas pelo jogador
    }

    /**
     * Obtém o nome do jogador.
     * 
     * @return O nome do jogador.
     */
    public String getName() {
        return name;
    }

    /**
     * Obtém a lista de peças do jogador.
     * 
     * @return A lista de peças do jogador.
     */
    public List<Piece> getPieces() {
        return pieces;
    }

    /**
     * Obtém a lista de peças capturadas pelo jogador.
     * 
     * @return A lista de peças capturadas pelo jogador.
     */
    public List<Piece> getCapturedPieces() {
        return capturedPieces;
    }

    /**
     * Adiciona uma peça à lista de peças do jogador.
     * 
     * @param piece A peça a ser adicionada.
     */
    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    /**
     * Remove uma peça da lista de peças do jogador.
     * 
     * @param piece A peça a ser removida.
     */
    public void removePiece(Piece piece) {
        pieces.remove(piece);
    }

    /**
     * Adiciona uma peça capturada à lista de peças capturadas pelo jogador.
     * 
     * @param piece A peça capturada a ser adicionada.
     */
    public void addCapturedPiece(Piece piece) {
        capturedPieces.add(piece);
    }
}
