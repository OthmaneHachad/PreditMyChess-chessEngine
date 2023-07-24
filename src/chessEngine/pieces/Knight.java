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
	        return false;
	    }

		// ensure the king is not checked
		int kingPosition = (this.pieceColor == 'w') ? this.cb.getWhiteKing().piecePosition : this.cb.getBlackKing().piecePosition ;
		Square kingSquare = this.cb.boardMatrix[7-(kingPosition/8)][kingPosition%8];
		if (kingSquare.isAttacked){
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

		char currentplayer = this.pieceColor ;

		if (cb.isKingInCheck(currentplayer) == true){
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
	public void move(Move move) {
		if (isMoveLegal(move) == true) {
			this.piecePosition = move.getTargetSquare() ;
		} else {
			System.out.println("Illegal Move");
		}
		
	}

	@Override
	public List<Move> setAttackingSquares() {
		List<Move> listAttackingSquares = new ArrayList<>();

		int row = this.piecePosition / 8 ;
		int column = this.piecePosition % 8 ;
		
		int[] moveList = new int[]{((row+2)*8 + column+ 1), ((row+2)*8 + column- 1), ((row-2)*8 + column+1), ((row-2)*8 + column-1), ((row+1)*8 + column+ 2), ((row+1)*8 + column- 2), ((row-1)*8 + column+2), ((row-1)*8 + column-2) };

		for (int target: moveList){
			if (this.isMoveLegal(new Move(target, this)) == true){
				this.cb.boardMatrix[7-target/8][target%8].isAttacked = true ;
				listAttackingSquares.add(new Move(target, this));
				this.cb.boardMatrix[7-(target/8)][target%8].isAttacked = true ;
				if (this.pieceColor == 'w'){
					this.cb.boardMatrix[7-target/8][target%8].piecesAttackingW.add(this) ;
				} else {
					this.cb.boardMatrix[7-target/8][target%8].piecesAttackingB.add(this) ;
				}
			}
		}
		return listAttackingSquares;
	}

	@Override
    public Knight copy(ChessBoard board) {
        Knight newPiece = new Knight(this.piecePosition, this.pieceColor, this.pieceLetter, board);
        return newPiece;
    }

}
