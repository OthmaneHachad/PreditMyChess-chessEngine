package chessEngine.pieces;

import java.util.ArrayList;
import java.util.List;

import chessEngine.ChessBoard;
import chessEngine.Move;
import chessEngine.Piece;
import chessEngine.Square;

public class Knight extends Piece{

	public Knight(int position, char color, char letter, ChessBoard board) {
		super(position, color, letter, board);

		final int row = this.piecePosition / 8 ;
		final int column = this.piecePosition % 8 ;

		if (this.pieceColor == 'b') {
			this.pieceLetter = 'n' ;
			chessBoard[row][column].piecesAttackingB = setAttackingSquares() ;
		} else {
			this.pieceLetter = 'N' ;
			chessBoard[row][column].piecesAttackingW = setAttackingSquares() ;
		}
	}

	

	@Override
	public boolean isMoveLegal(Move move) {
		int startRow = this.piecePosition / 8;
		int startColumn = this.piecePosition % 8;

		int target = move.getTargetSquare() ;
		int targetRow = target / 8 ;
		int targetColumn = target % 8 ;

		// Ensure the target square is on the board.
	    if (target < 0 || target > 63) {
	    	System.out.println("OUT OF RANGE");
	        return false;
	    }

		if (target == this.piecePosition){
			return false ;
		}

		if ((Math.abs(targetRow - startRow) == 2) &&
			(Math.abs(targetColumn - startColumn) == 1)){
			return true ;
		} else if ((Math.abs(targetRow - startRow) == 1) &&
			(Math.abs(targetColumn - startColumn) == 2)){
			return true ;
		}

		return false;
	}

	@Override
	public void move(int targetPosition) {
		if (isMoveLegal(new Move(targetPosition)) == true) {
			piecePosition = targetPosition ;
		} else {
			System.out.println("Illegal Move");
		}
		
	}

	@Override
	public List<Integer> setAttackingSquares() {
		List<Integer> listAttackingSquares = new ArrayList<>();

		int row = this.piecePosition / 8 ;
		int column = this.piecePosition % 8 ;
		
		int[] moveList = new int[]{((row+2)*8 + column+ 1), ((row+2)*8 + column- 1), ((row-2)*8 + column+1), ((row-2)*8 + column-1), ((row+1)*8 + column+ 2), ((row+1)*8 + column- 2), ((row-1)*8 + column+2), ((row-1)*8 + column-2) };

		for (int target: moveList){
			if (this.isMoveLegal(new Move(target)) == true){
				//chessBoard[7-target/8][target%8].representation = 'o' ;
				listAttackingSquares.add(target);
			}
		}
		return listAttackingSquares;
	}

}
