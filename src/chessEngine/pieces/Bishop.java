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
	    
	    System.out.println(startRow + "---" + startColumn);
	    System.out.println(targetRow + "---  " + targetColumn);
	    
	

	    // Ensure the target square is on the board.
	    if (target < 0 || target > 63) {
	    	System.out.println("OUT OF RANGE");
	        return false;
	    }

		if (target == this.piecePosition){
			return false ;
		}

		char currentplayer = this.pieceColor ;

		if (cb.isKingInCheck(currentplayer) == true){
			return false ;
		}

	 // Check if the move is diagonal
	    if (Math.abs(targetRow - startRow) == Math.abs(targetColumn - startColumn)) {
	        // Check if the path to the target square is clear
	        int rowStep = (targetRow > startRow) ? 1 : -1;
	        int columnStep = (targetColumn > startColumn) ? 1 : -1;
	        
	        int tempRow = 7 - startRow - rowStep;
	        int tempColumn = startColumn + columnStep;
	        
			//chessBoard[targetRow][targetColumn].representation = 'o' ;

	        while (tempRow != targetRow && tempColumn != targetColumn) {
				/*
				System.out.println(tempRow + "--- (update)" + tempColumn);
				System.out.println("");
				chessBoard[tempRow][tempColumn].representation = 'o' ;
				ChessBoard.printBoard();
				System.out.println("");
				System.out.println(""); */
	    	    
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
	    
	    System.out.println("Not diagonal");

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
		
		
		return listAttackingSquares;
	}

}
