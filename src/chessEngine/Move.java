package chessEngine;

public class Move {
	private int targetSquare ;
	private Piece pieceMoving ;
	
	public Move(int target, Piece piece) {
		this.targetSquare = target ;
		this.pieceMoving = piece ;
	}
	
	public int getTargetSquare() {
		return targetSquare ;
	}

	public Piece getPieceMoving(){
		return pieceMoving ;
	}
}
