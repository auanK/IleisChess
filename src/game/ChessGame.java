package game;

import board.ChessBoard;
import pieces.Piece;
import board.ChessUI;
import draw.Draw;
import draw.Draw.DrawType;
import logic.CheckValidation;
import logic.MoveExecutor;
import logic.Exceptions;
import specialmoves.Promotion;

public class ChessGame {

    private static final String WHITE_PLAYER_NAME = "White";
    private static final String BLACK_PLAYER_NAME = "Black";

    private static Player playerWhite = new Player(WHITE_PLAYER_NAME);
    private static Player playerBlack = new Player(BLACK_PLAYER_NAME);

    private static Player currentPlayer = playerWhite;
    private static Player opponent = playerBlack;

    // Classe principal do jogo.
    public static void main(String[] args) {
        playChessGame();
    }

    // Inicia o jogo.
    private static void playChessGame() {
        // Cria o tabuleiro e associa as peças aos jogadores.
        ChessBoard chessBoard = new ChessBoard();

        Piece[][] board = chessBoard.getBoard();
        chessBoard.assignPiecesToPlayers(playerWhite, playerBlack);

        // Cria as flags de empate e xeque-mate.
        boolean checkMate = false;
        DrawType drawType = new DrawType();

        // Loop principal do jogo.
        while (true) {
            // Imprime o tabuleiro.
            ChessUI.printBoard(board, playerWhite, playerBlack);

            // Verifica se o jogador está em xeque-mate.
            if (CheckValidation.isCheckMate(board, currentPlayer, opponent)) {
                checkMate = true;
                break;
            }

            // Verifica se o jogador está em xeque.
            handleCheck(board);

            // Verifica se ocoreu afogamento.
            if (Draw.isStalemate(board, currentPlayer, opponent)) {
                drawType.setDrawType(Draw.DrawTypes.STALEMATE);
                break;
            }

            // Verifica se ocorreu empate por insuficiência de material.
            if (Draw.insufficientMaterial(currentPlayer, opponent)) {
                drawType.setDrawType(Draw.DrawTypes.INSUFFICIENT_MATERIAL);
                break;
            }

            // Realiza um movimento, se possível.
            try {
                makeMove(board, drawType);
            } catch (Exceptions e) {
                // Se a exceção não for de empate, continua o jogo.
                if (!(e.getMessage().equals("Draw!"))) {
                    continue;
                }

                drawType.setDrawType(Draw.DrawTypes.AGREEMENT);
                break;
            }

            // Troca o jogador atual.
            switchPlayers();
        }

        handleGameResult(drawType, checkMate);
    }

    // Verifica se o jogador está em xeque.
    private static void handleCheck(Piece[][] board) {
        if (CheckValidation.isCheck(board, currentPlayer, opponent)) {
            currentPlayer.setCheck(true);
            System.out.println(currentPlayer.getName() + " em xeque!");
        } else {
            currentPlayer.setCheck(false);
        }
    }

    // Realiza um movimento.
    private static void makeMove(Piece[][] board, DrawType drawType) throws Exceptions {
        System.out.println(currentPlayer.getName() + ", é sua vez. Digite o movimento: ");

        // Lê a entrada do jogador.
        int[] moveCoordinates = UserInput.inputCoordinates(board, currentPlayer, opponent, playerWhite, playerBlack);

        try {
            MoveExecutor.movePiece(board, moveCoordinates, currentPlayer, opponent);
            int destinationRow = moveCoordinates[2];
            int destinationColumn = moveCoordinates[3];

            Promotion.promotion(board, board[destinationRow][destinationColumn], currentPlayer);
        } catch (Exceptions e) {
            System.out.println(e.getMessage());
        }
    }

    private static void handleGameResult(DrawType drawType, boolean checkMate) {
        if (drawType.isDraw()) {
            drawType.print();
        } else if (checkMate) {
            System.out.println("Xeque-mate! " + opponent.getName() + " venceu!");
        }
    }

    private static void switchPlayers() {
        Player aux = currentPlayer;
        currentPlayer = opponent;
        opponent = aux;
    }
}
