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
				ChessBoard.printBoard();
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
				ChessBoard.printBoard();
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
	        
			//chessBoard[targetRow][targetColumn].representation = 'o' ;

	        while (tempRow != targetRow && tempColumn != targetColumn) {
	    	    
	            if (chessBoard[tempRow][tempColumn].piece != null) {
	            	
	            	System.out.println("PIECE THEREE " + tempRow + "--" + tempColumn);
	            	System.out.println(chessBoard[tempRow][tempColumn].piece.getClass().getName() + "  " 
	            			+ chessBoard[tempRow][tempColumn].piece.piecePosition);
	            	
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
					return pathClear(startColumn, targetColumn, startRow, true, chessBoard);
				} else if (startColumn == targetColumn){
					return pathClear(startRow, targetRow, startColumn, false, chessBoard);
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
			
			if (this.isMoveLegal(new Move(NW)) == true) {
				chessBoard[7-NW/8][NW%8].isAttacked = true;
				listAttackingSquares.add(new Move(NW)) ;
			}
			
			if (this.isMoveLegal(new Move(NE)) == true) {
				chessBoard[7-NE/8][NE%8].isAttacked = true;
				listAttackingSquares.add(new Move(NE)) ;
			}

			if (this.isMoveLegal(new Move(SW)) == true) {
				chessBoard[7-SW/8][SW%8].isAttacked = true;
				listAttackingSquares.add(new Move(SW)) ;
			}

			if (this.isMoveLegal(new Move(SE)) == true) {
				chessBoard[7-(SE/8)][(SE%8)].isAttacked = true;
				listAttackingSquares.add(new Move(SE)) ;
			}

			
		}

		for (int i = column; i < 64; i = i + 8){
			boolean verticalMove = isMoveLegal(new Move(i));
			if (verticalMove == true){
				chessBoard[7-(i/8)][i%8].isAttacked = true ;
				listAttackingSquares.add(new Move(i));
			}
		}

		for (int i = row *8; i < (row+1)*8; i++ ){
			boolean verticalMove = isMoveLegal(new Move(i));
			if (verticalMove == true){
				chessBoard[7-(i/8)][i%8].isAttacked = true ;
				listAttackingSquares.add(new Move(i));
			}
		}


		
		
		return listAttackingSquares;

	}

}
