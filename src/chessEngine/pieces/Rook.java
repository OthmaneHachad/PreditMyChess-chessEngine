package chessEngine.pieces;

import java.util.ArrayList;
import java.util.List;

import chessEngine.Move;
import chessEngine.Piece;
import chessEngine.Square;
import chessEngine.ChessBoard;

public class Rook extends Piece{
	
	public Rook(int position, char color, char letter, ChessBoard board) {
		super(position, color, letter, board);
	}

	@Override

	public boolean isMoveLegal(Move move){
		final int startRow = this.piecePosition / 8 ;
		final int startColumn = this.piecePosition % 8 ;

		final int target  = move.getTargetSquare() ;

		final int targetRow = target / 8 ;
		final int targetColumn = target % 8 ;

		// make sure target not off grid
		if (target < 0 || target > 63){
			return false ;
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

		if (cb.isKingInCheck(this.pieceColor) == true){
			return false ;
		}

		// Move horizontal
		if (startRow == targetRow){
			return pathClear(startColumn, targetColumn, startRow, true, this.cb.boardMatrix);
		} else if (startColumn == targetColumn){
			return pathClear(startRow, targetRow, startColumn, false, this.cb.boardMatrix);
		}

		return false ;
	}

	private boolean pathClear(int start, int end, int fixed, boolean isHorizontal, Square[][] board) {
		
		// Ensure start is less than end
		if (start > end) {
			int temp = start;
			start = end;
			end = temp;
		}

		for (int i = start +1; i < end; i++){
			if (isHorizontal){
				if (board[7-fixed][i].piece != null){
					return false ;
				}
			} else {
				if (board[7-i][fixed].piece != null){
					return false ;
				}
			}
		}

		return true ;
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
	public List<Move> setAttackingSquares(){
		List<Move> listAttackingSquares = new ArrayList<>() ;

		int start = this.piecePosition ;
		int column = start % 8;
		int row = start / 8 ;

		for (int i = column; i < 64; i = i + 8){
			boolean verticalMove = isMoveLegal(new Move(i, this));
			if (verticalMove == true){
				listAttackingSquares.add(new Move(i, this));
				this.cb.boardMatrix[7-(i/8)][i%8].isAttacked = true ;
				if (this.pieceColor == 'w'){
					this.cb.boardMatrix[7-i/8][i%8].piecesAttackingW.add(this) ;
				} else {
					this.cb.boardMatrix[7-i/8][i%8].piecesAttackingB.add(this) ;
				}
			}
		}

		for (int i = row *8; i < (row+1)*8; i++ ){
			boolean verticalMove = isMoveLegal(new Move(i, this));
			if (verticalMove == true){
				listAttackingSquares.add(new Move(i, this));
				this.cb.boardMatrix[7-(i/8)][i%8].isAttacked = true ;
				if (this.pieceColor == 'w'){
					this.cb.boardMatrix[7-i/8][i%8].piecesAttackingW.add(this) ;
				} else {
					this.cb.boardMatrix[7-i/8][i%8].piecesAttackingB.add(this) ;
				}
			}
		}
		
		return listAttackingSquares ;
	}

	@Override
    public Rook copy(ChessBoard board) {
        Rook newPiece = new Rook(this.piecePosition, this.pieceColor, this.pieceLetter, board);
        return newPiece;
    }

}
