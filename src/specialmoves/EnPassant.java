package specialmoves;

import game.ChessLog;
import game.PlayChess;
import game.Player;
import logic.CheckValidation;
import logic.Exceptions;
import pieces.Piece;
import pieces.Pawn;

public class EnPassant {
    public static void validateEnPassant(Piece[][] board, int[] coordinates, Player currentPlayer, Player opponent)
            throws Exceptions {
        int sourceRow = coordinates[0];
        int sourceColumn = coordinates[1];
        int destinationRow = coordinates[2];
        int destinationColumn = coordinates[3];

        ChessLog log = PlayChess.getLog();
        // Salva a peça de origem e verifica se é um peão.
        Piece sourcePiece = board[sourceRow][sourceColumn];
        if (!(sourcePiece instanceof Pawn)) {
            return;
        }

        // Verifica se o movimento é diagonal.
        int rowDiff = Math.abs(sourceRow - destinationRow);
        int colDiff = Math.abs(sourceColumn - destinationColumn);
        if (rowDiff > 1 || colDiff > 1) {
            return;
        }

        int direction = sourcePiece.getColor() == 'W' ? 1 : -1;

        // Verifica se o jogador selecionou a peça do adversário e corrige o destino.
        if (sourceRow == destinationRow) {
            Piece destinationPiece = board[destinationRow][destinationColumn];
            if (destinationPiece != null) {
                destinationRow += direction;
            } else {
                return;
            }
        }

        // Garante que o destino é uma posição válida.
        if (destinationRow == 0 || destinationRow == 7) {
            return;
        }

        // Verifica se a peça de destino é um peão do adversário.
        Piece capturedPawn = board[destinationRow - direction][destinationColumn];

        if (capturedPawn == null || !(capturedPawn instanceof Pawn)
                || currentPlayer.getPieces().contains(capturedPawn)) {
            return;
        }

        // Se a notação algébrica estiver vazia, não há movimentos anteriores.
        if (log.getLog().size() == 0) {
            return;
        }

        // Pega o último movimento e verifica se foi um movimento de peão.
        String lastMove = log.getLog().get(log.getLog().size() - 1);
        if (lastMove.charAt(0) != 'P') {
            return;
        }

        int lastSourceRow = -1;
        int lastSourceColumn = -1;

        int size = lastMove.length();

        // Movimento normal ou movimento com xeque.
        // Exemplo: Pe4e5 ou Pe4e5+
        if (size == 5 || size == 6) {
            lastSourceRow = lastMove.charAt(4) - '1';
            lastSourceColumn = lastMove.charAt(3) - 'a';
        } else {
            return;
        }

        // Verifica se o peão a ser capturado se moveu apenas uma vez e foi um movimento
        // duplo.
        if (lastSourceRow != capturedPawn.getPositionRow() || lastSourceColumn != capturedPawn.getPositionColumn()) {
            return;
        }

        // Verifica se o peão a ser capturado se moveu apenas uma vez e foi um movimento
        // duplo.
        if (capturedPawn.getColor() == 'W') {
            if (capturedPawn.getPositionRow() != 3 || capturedPawn.getCountMoves() != 1) {
                return;
            }
        } else {
            if (capturedPawn.getPositionRow() != 4 || capturedPawn.getCountMoves() != 1) {
                return;
            }
        }

        // Atualiza a posição de destino.
        coordinates[2] = destinationRow;

        // Verifica se o movimento deixaria o rei em xeque.
        boolean isCheckAfterMove = enPassantIsCheck(board, coordinates, currentPlayer, opponent);
        if (isCheckAfterMove) {
            throw new Exceptions("Movimento inválido, o rei fica em xeque ao se mover!");
        }

        throw new Exceptions("En Passant!");
    }

    // Faz o en passant.
    public static void enPassant(Piece[][] board, int[] coordinates, Player currentPlayer, Player opponent)
            throws Exceptions {
        int sourceRow = coordinates[0];
        int sourceColumn = coordinates[1];
        int destinationRow = coordinates[2];
        int destinationColumn = coordinates[3];

        // Recebe o log da partida.
        ChessLog log = PlayChess.getLog();

        // Salva o peão de origem.
        Piece sourcePiece = board[sourceRow][sourceColumn];

        // Cria a notação do movimento.
        String notation = sourcePiece.getLabel() + log.parseChessNotation(sourceRow, sourceColumn);

        // Salva o peão do adversário.
        int direction = sourcePiece.getColor() == 'W' ? 1 : -1;
        Piece destinationPiece = board[destinationRow - direction][destinationColumn];

        // Remove o peão do adversário.
        board[destinationRow - direction][destinationColumn] = null;
        opponent.removePiece(destinationPiece);
        currentPlayer.addCapturedPiece(destinationPiece);
        destinationPiece.setPosition(-1, -1); // Posição inválida

        // Move a peça no tabuleiro
        board[destinationRow][destinationColumn] = sourcePiece;
        board[sourceRow][sourceColumn] = null;
        sourcePiece.setPosition(destinationRow, destinationColumn);
        sourcePiece.addMove();

        // Termina a notação do movimento e adiciona ao log.
        notation += log.parseChessNotation(destinationRow, destinationColumn) + "e.p.";
        log.addMove(notation);
    }

    // Verifica se o movimento de en passant deixaria o rei em xeque.
    public static boolean enPassantIsCheck(Piece[][] board, int[] coordinates, Player currentPlayer, Player opponent) {
        int sourceRow = coordinates[0];
        int sourceColumn = coordinates[1];
        int destinationRow = coordinates[2];
        int destinationColumn = coordinates[3];

        // Salva o peão de origem e o peão do adversário.
        Piece sourcePiece = board[sourceRow][sourceColumn];

        int direction = sourcePiece.getColor() == 'W' ? 1 : -1;
        Piece capturedPawn = board[destinationRow - direction][destinationColumn];

        // Move a peça no tabuleiro
        board[destinationRow][destinationColumn] = sourcePiece;
        board[sourceRow][sourceColumn] = null;
        sourcePiece.setPosition(destinationRow, destinationColumn);

        // Remove o peão do adversário.
        board[destinationRow - direction][destinationColumn] = null;
        opponent.removePiece(capturedPawn);

        // Verifica se o rei fica em xeque.
        boolean isCheck = CheckValidation.isCheck(board, currentPlayer, opponent);

        // Desfaz o movimento.
        board[sourceRow][sourceColumn] = sourcePiece;
        board[destinationRow][destinationColumn] = null;
        sourcePiece.setPosition(sourceRow, sourceColumn);

        // Adiciona o peão do adversário.
        board[destinationRow - direction][destinationColumn] = capturedPawn;
        opponent.addPiece(capturedPawn);

        return isCheck;
    }
}
