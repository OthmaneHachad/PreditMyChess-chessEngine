package chessEngine;

import java.util.List;

public abstract class Piece {
	
    public int piecePosition;
    public char pieceColor;
    public Square[][] chessBoard;
    
    

    public Piece(int position, char color, Square[][] board) {
        this.chessBoard = board;
        this.piecePosition = position;
        this.pieceColor = color;
    }

    public abstract boolean isMoveLegal(Move move);
    public abstract void move(int targetPosition);
    public abstract List<Integer> setAttackingSquares();

    private static void myFunction(){
        
    }
}



