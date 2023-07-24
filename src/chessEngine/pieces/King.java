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
	}
	
	public void KingChecked() {
		final int row = this.piecePosition / 8 ;
		final int column = this.piecePosition % 8 ;
		this.isChecked = this.cb.boardMatrix[row][column].isAttacked ;
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

		// make sure future position is not checked
		if (this.cb.boardMatrix[7-(move.getTargetSquare()/8)][(move.getTargetSquare()%8)].isAttacked){
			return false ;
		}

		if (cb.boardMatrix[7-(target/8)][target%8].piece != null){
			if (cb.boardMatrix[7-(target/8)][target%8].piece.pieceColor == this.pieceColor){
				return false ;
			}
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
			if (this.isMoveLegal(new Move(tg, this)) == true){
				this.cb.boardMatrix[7-(tg / 8)][tg % 8].isAttacked = true ; 
				listAttackingSquares.add(new Move(tg, this));
				cb.boardMatrix[7-(tg/8)][tg%8].isAttacked = true ;
				if (this.pieceColor == 'w'){
					cb.boardMatrix[7-tg/8][tg%8].piecesAttackingW.add(this) ;
				} else {
					cb.boardMatrix[7-tg/8][tg%8].piecesAttackingB.add(this) ;
				}
			}
		}		
		
		return listAttackingSquares ;
	}

	@Override
    public King copy(ChessBoard board) {
        King newPiece = new King(this.piecePosition, this.pieceColor, this.pieceLetter, board);
        return newPiece;
    }

}
