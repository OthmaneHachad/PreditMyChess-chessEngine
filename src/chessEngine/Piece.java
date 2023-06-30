package chessEngine;

import java.util.List;

public abstract class Piece {
	
    public int piecePosition;
    public char pieceColor;
    public char pieceLetter ;
    protected Square[][] chessBoard;
    protected ChessBoard cb ;
    public List<Move> targetMoves ;

    
    

    public Piece(int position, char color, char letter, ChessBoard board) {
        this.cb = board ;
        this.chessBoard = board.boardMatrix;
        
        this.pieceLetter = letter ;
        this.piecePosition = position;
        this.pieceColor = color;
    }

    public abstract boolean isMoveLegal(Move move);
    public abstract void move(Move move);
    public abstract List<Move> setAttackingSquares();

    private static void myFunction(){
        
    }
}



