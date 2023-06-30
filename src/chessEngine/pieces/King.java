package chessEngine.pieces;
import chessEngine.Piece;
import chessEngine.Square;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import chessEngine.ChessBoard;
import chessEngine.Move;

public class King extends Piece{

	public boolean isChecked ;
	
	public King(int position, char color, char letter, ChessBoard board) {
		super(position, color, letter, board);
		this.isChecked = KingChecked();
	}
	
	private boolean KingChecked() {
		final int row = this.piecePosition / 8 ;
		final int column = this.piecePosition % 8 ;
		return chessBoard[7-row][column].isAttacked ;
	}
	
	@Override
	public void move(Move move) {
		if (isMoveLegal(move) == true) {
			this.piecePosition = move.getTargetSquare() ;
		} else {
			System.out.println("Illegal Move");
		}
		
	}
	
	@Override
	public boolean isMoveLegal(Move move) {
		final int startRow = this.piecePosition / 8 ;
		final int startColumn = this.piecePosition % 8 ;

		final int target = move.getTargetSquare();

		final int targetRow = target / 8 ;
		final int targetColumn = target % 8 ;

		if (target < 0 || target > 63) {
	        return false;
	    }

		if (target == this.piecePosition){
			return false ;
		}
		
		return true ;
	}

	@Override
	public List<Move> setAttackingSquares() {
		
		List<Move> listAttackingSquares = new ArrayList<>();

		int[] targets = {this.piecePosition+7, this.piecePosition+9, this.piecePosition+8, this.piecePosition+1, this.piecePosition-1, this.piecePosition-8, this.piecePosition-9, this.piecePosition-7};
		
		for (int tg: targets){
			if (this.isMoveLegal(new Move(tg)) == true){
				this.chessBoard[7-(tg / 8)][tg % 8].isAttacked = true ; 
				listAttackingSquares.add(new Move(tg));
			}
		}		
		
		return listAttackingSquares ;
	}

}
