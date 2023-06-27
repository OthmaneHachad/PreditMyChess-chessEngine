package chessEngine;

public class Move {
	private int targetSquare ;
	
	public Move(int target) {
		this.targetSquare = target ;
	}
	
	public int getTargetSquare() {
		return targetSquare ;
	}
}
