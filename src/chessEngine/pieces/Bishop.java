package chessEngine.pieces;

import java.util.ArrayList;
import java.util.List;

import chessEngine.Move;
import chessEngine.Piece;
import chessEngine.Square;
import chessEngine.ChessBoard;

public class Bishop extends Piece{
	
	
	public Bishop(int position, char color, char letter, ChessBoard board) {
		super(position, color, letter, board);
	}

	@Override
	public boolean isMoveLegal(Move move) {
		
		
	    int target = move.getTargetSquare();
	    int startRow = this.piecePosition / 8;
	    int startColumn = this.piecePosition % 8;
	    int targetRow = target / 8 ;
	    int targetColumn = target % 8;
	    
	

	    // Ensure the target square is on the board.
	    if (target < 0 || target > 63) {
	        return false;
	    }

		// ensure the king is not checked
		int kingPosition = (this.pieceColor == 'w') ? this.cb.getWhiteKing().piecePosition : this.cb.getBlackKing().piecePosition ;
		Square kingSquare = ChessBoard.boardMatrix[7-(kingPosition/8)][kingPosition%8];
		if ((kingSquare.isAttacked)==true){
			return false ;
		}

		if (ChessBoard.boardMatrix[7-(target/8)][target%8].piece != null){
			if (ChessBoard.boardMatrix[7-(target/8)][target%8].piece.pieceColor == this.pieceColor){
				return false ;
			}
		}

		if (target == this.piecePosition){
			return false ;
		}

		char currentplayer = this.pieceColor ;

		if (this.cb.isKingInCheck(currentplayer) == true){
			return false ;
		}

	 // Check if the move is diagonal
	    if (Math.abs(targetRow - startRow) == Math.abs(targetColumn - startColumn)) {
	        // Check if the path to the target square is clear
	        int rowStep = (targetRow > startRow) ? 1 : -1;
	        int columnStep = (targetColumn > startColumn) ? 1 : -1;
	        
	        int tempRow = 7 - startRow - rowStep;
	        int tempColumn = startColumn + columnStep;

	        while (tempRow != targetRow && tempColumn != targetColumn) {
	    	    
	            if (ChessBoard.boardMatrix[tempRow][tempColumn].piece != null) {
	            	return false; // There's a piece blocking the path
	            }
	            tempRow -= rowStep;
	            tempColumn += columnStep;
	        }
	        return true;
	    }

	    return false ;
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
		
		// loop over all four directions at the same time
		for (int i = 1; i < 8; i ++) {
			int NW = this.piecePosition + (i*8) - i;
			int NE = this.piecePosition + (i*8) + i;
			int SW = this.piecePosition - (i*8) - i;
			int SE = this.piecePosition - (i*8) + i;
			
			if (this.isMoveLegal(new Move(NW, this)) == true) {
				ChessBoard.boardMatrix[7-NW/8][NW%8].isAttacked = true;
				listAttackingSquares.add(new Move(NW, this)) ;
				if (this.pieceColor == 'w'){
					ChessBoard.boardMatrix[7-NW/8][NW%8].piecesAttackingW.add(this) ;
				} else {
					ChessBoard.boardMatrix[7-NW/8][NW%8].piecesAttackingB.add(this) ;
				}
			}
			
			if (this.isMoveLegal(new Move(NE, this)) == true) {
				ChessBoard.boardMatrix[7-NE/8][NE%8].isAttacked = true;
				listAttackingSquares.add(new Move(NE, this)) ;
				if (this.pieceColor == 'w'){
					ChessBoard.boardMatrix[7-NE/8][NE%8].piecesAttackingW.add(this) ;
				} else {
					ChessBoard.boardMatrix[7-NE/8][NE%8].piecesAttackingB.add(this) ;
				}
			}

			if (this.isMoveLegal(new Move(SW, this)) == true) {
				ChessBoard.boardMatrix[7-SW/8][SW%8].isAttacked = true;
				listAttackingSquares.add(new Move(SW, this)) ;
				if (this.pieceColor == 'w'){
					ChessBoard.boardMatrix[7-SW/8][SW%8].piecesAttackingW.add(this) ;
				} else {
					ChessBoard.boardMatrix[7-SW/8][SW%8].piecesAttackingB.add(this) ;
				}
			}

			if (this.isMoveLegal(new Move(SE, this)) == true) {
				ChessBoard.boardMatrix[7-(SE/8)][(SE%8)].isAttacked = true;
				listAttackingSquares.add(new Move(SE, this)) ;
				if (this.pieceColor == 'w'){
					ChessBoard.boardMatrix[7-SE/8][SE%8].piecesAttackingW.add(this) ;
				} else {
					ChessBoard.boardMatrix[7-SE/8][SE%8].piecesAttackingB.add(this) ;
				}
			}

		}
		return listAttackingSquares;
	
	}

	@Override
    public Bishop copy(ChessBoard board) {
        Bishop newPiece = new Bishop(this.piecePosition, this.pieceColor, this.pieceLetter, board);
        return newPiece;
    }

}
