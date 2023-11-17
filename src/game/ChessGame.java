package game;

import board.ChessBoard;
import board.ChessUI;
import draw.Draw;
import draw.Draw.DrawType;
import logic.CheckValidation;
import logic.Exceptions;
import logic.MoveExecutor;
import pieces.Piece;
import specialmoves.Promotion;

// Classe principal do jogo.
public class ChessGame {
    private static Player playerWhite = new Player("White");
    private static Player playerBlack = new Player("Black");

    private static Player currentPlayer = playerWhite;
    private static Player opponent = playerBlack;

    // Método main.
    public static void main(String[] args) {
        playChessGame();
    }

    // Inicia o jogo.
    private static void playChessGame() {
        // Cria o tabuleiro e o preenche com as peças.
        ChessBoard chessBoard = new ChessBoard();
        Piece[][] board = chessBoard.getBoard();
        chessBoard.assignPiecesToPlayers(playerWhite, playerBlack);

        boolean checkMate = false;
        DrawType drawType = new DrawType();

        UserInput.inputName(playerWhite, playerBlack);

        while (true) {
            ChessUI.printBoard(board);

            if (CheckValidation.isCheckMate(board, currentPlayer, opponent)) {
                checkMate = true;
                break;
            }

            handleCheck(board);

            if (Draw.isStalemate(board, currentPlayer, opponent)) {
                drawType.setDrawType(Draw.DrawTypes.STALEMATE);
                break;
            }

            if (Draw.insufficientMaterial(currentPlayer, opponent)) {
                drawType.setDrawType(Draw.DrawTypes.INSUFFICIENT_MATERIAL);
                break;
            }

            try {
                makeMove(board, drawType);
            } catch (Exceptions e) {
                if (e.getMessage().equals("Draw!")) {
                    drawType.setDrawType(Draw.DrawTypes.AGREEMENT);
                    break;
                }
                continue;
            }

            switchPlayers();
        }

        handleGameResult(drawType, checkMate);
    }

    private static void handleCheck(Piece[][] board) {
        if (CheckValidation.isCheck(board, currentPlayer, opponent)) {
            currentPlayer.setCheck(true);
            System.out.println(currentPlayer.getName() + " em xeque!");
        } else {
            currentPlayer.setCheck(false);
        }
    }

    private static void makeMove(Piece[][] board, DrawType drawType) throws Exceptions {
        System.out.println(currentPlayer.getName() + ", é sua vez. Digite o movimento: ");

        int[] moveCoordinates;
        try {
            moveCoordinates = UserInput.inputCoordinates(board, currentPlayer, opponent, playerWhite, playerBlack);
        } catch (Exceptions e) {
            String message = e.getMessage();
            System.out.println(message);
            throw new Exceptions(message);
        }

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

    public static Player getPlayerWhite() {
        return playerWhite;
    }

    public static Player getPlayerBlack() {
        return playerBlack;
    }
}
