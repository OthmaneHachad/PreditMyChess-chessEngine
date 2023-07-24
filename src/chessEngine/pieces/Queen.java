package chessEngine.pieces;

import java.util.ArrayList;
import java.util.List;

import chessEngine.ChessBoard;
import chessEngine.Move;
import chessEngine.Piece;
import chessEngine.Square;

public class Queen extends Piece{
	
	
	public Queen(int position, char color, char letter, ChessBoard board) {
		super(position, color, letter, board);
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
				System.out.println(fixed + "--- (update)" + i);
				System.out.println("");
				//chessBoard[7-fixed][i].representation = 'o' ;
				this.cb.printBoard();
				System.out.println("");
				System.out.println(""); 
				if (board[7-fixed][i].piece != null){
					System.out.println("PIECE THEREE ");
					return false ;
				}
			} else {
				System.out.println(i + "--- (update)" + fixed);
				System.out.println("");
				//chessBoard[7-i][fixed].representation = 'o' ;
				this.cb.printBoard();
				System.out.println("");
				System.out.println(""); 
				if (board[7-i][fixed].piece != null){
					System.out.println("PIECE THEREE ");
					return false ;
				}
			}
		}

		return true ;
	}

	@Override
	public boolean isMoveLegal(Move move) {
		
		
		int target = move.getTargetSquare();
	    int startRow = this.piecePosition / 8;
	    int startColumn = this.piecePosition % 8;
	    int targetRow = target / 8;
	    int targetColumn = target % 8;
	    
	     
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


		if (Math.abs(targetRow - startRow) == Math.abs(targetColumn - startColumn)){
			// Check if the path to the target square is clear
	        int rowStep = (targetRow > startRow) ? 1 : -1;
	        int columnStep = (targetColumn > startColumn) ? 1 : -1;
	        
	        int tempRow = 7 - startRow - rowStep;
	        int tempColumn = startColumn + columnStep;
	        

	        while (tempRow != targetRow && tempColumn != targetColumn) {
	    	    
	            if (this.cb.boardMatrix[tempRow][tempColumn].piece != null) {
	            	
	            	System.out.println("PIECE THEREE " + tempRow + "--" + tempColumn);
	            	System.out.println(this.cb.boardMatrix[tempRow][tempColumn].piece.getClass().getName() + "  " 
	            			+ this.cb.boardMatrix[tempRow][tempColumn].piece.piecePosition);
	            	
	            	return false; // There's a piece blocking the path
	            }
	            tempRow -= rowStep;
	            tempColumn += columnStep;
	        }
	        return true;
		}

		else if ((startRow == targetRow && startColumn != targetColumn) ||
		 	(startRow != targetRow && startColumn == targetColumn)){
				// Move horizontal
				if (startRow == targetRow){
					return pathClear(startColumn, targetColumn, startRow, true, this.cb.boardMatrix);
				} else if (startColumn == targetColumn){
					return pathClear(startRow, targetRow, startColumn, false, this.cb.boardMatrix);
				}
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
		
		int start = this.piecePosition ;
		int column = start % 8;
		int row = start / 8 ;

		// loop over all four directions at the same time
		for (int i = 1; i < 8; i ++) {
			int NW = this.piecePosition + (i*8) - i;
			int NE = this.piecePosition + (i*8) + i;
			int SW = this.piecePosition - (i*8) - i;
			int SE = this.piecePosition - (i*8) + i;
			
			if (this.isMoveLegal(new Move(NW, this)) == true) {
				this.cb.boardMatrix[7-NW/8][NW%8].isAttacked = true;
				listAttackingSquares.add(new Move(NW, this)) ;
				this.cb.boardMatrix[7-(NW/8)][NW%8].isAttacked = true ;
				if (this.pieceColor == 'w'){
					this.cb.boardMatrix[7-NW/8][NW%8].piecesAttackingW.add(this) ;
				} else {
					this.cb.boardMatrix[7-NW/8][NW%8].piecesAttackingB.add(this) ;
				}
			}
			
			if (this.isMoveLegal(new Move(NE, this)) == true) {
				this.cb.boardMatrix[7-NE/8][NE%8].isAttacked = true;
				listAttackingSquares.add(new Move(NE, this)) ;
				this.cb.boardMatrix[7-(NE/8)][NE%8].isAttacked = true ;
				if (this.pieceColor == 'w'){
					this.cb.boardMatrix[7-NE/8][NE%8].piecesAttackingW.add(this) ;
				} else {
					this.cb.boardMatrix[7-NE/8][NE%8].piecesAttackingB.add(this) ;
				}
			}

			if (this.isMoveLegal(new Move(SW, this)) == true) {
				this.cb.boardMatrix[7-SW/8][SW%8].isAttacked = true;
				listAttackingSquares.add(new Move(SW, this)) ;
				this.cb.boardMatrix[7-(SW/8)][SW%8].isAttacked = true ;
				if (this.pieceColor == 'w'){
					this.cb.boardMatrix[7-SW/8][SW%8].piecesAttackingW.add(this) ;
				} else {
					this.cb.boardMatrix[7-SW/8][SW%8].piecesAttackingB.add(this) ;
				}
			}

			if (this.isMoveLegal(new Move(SE, this)) == true) {
				this.cb.boardMatrix[7-(SE/8)][(SE%8)].isAttacked = true;
				listAttackingSquares.add(new Move(SE, this)) ;
				this.cb.boardMatrix[7-(SE/8)][SE%8].isAttacked = true ;
				if (this.pieceColor == 'w'){
					this.cb.boardMatrix[7-SE/8][SE%8].piecesAttackingW.add(this) ;
				} else {
					this.cb.boardMatrix[7-SE/8][SE%8].piecesAttackingB.add(this) ;
				}
			}

			
		}

		for (int i = column; i < 64; i = i + 8){
			boolean verticalMove = isMoveLegal(new Move(i, this));
			if (verticalMove == true){
				this.cb.boardMatrix[7-(i/8)][i%8].isAttacked = true ;
				listAttackingSquares.add(new Move(i, this));
			}
		}

		for (int i = row *8; i < (row+1)*8; i++ ){
			boolean verticalMove = isMoveLegal(new Move(i, this));
			if (verticalMove == true){
				this.cb.boardMatrix[7-(i/8)][i%8].isAttacked = true ;
				listAttackingSquares.add(new Move(i, this));
			}
		}

		return listAttackingSquares;

	}

	@Override
    public Queen copy(ChessBoard board) {
        Queen newPiece = new Queen(this.piecePosition, this.pieceColor, this.pieceLetter, board);
        return newPiece;
    }

}
