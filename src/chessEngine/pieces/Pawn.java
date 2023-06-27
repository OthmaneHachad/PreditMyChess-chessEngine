package chessEngine.pieces;
import chessEngine.Move;
import chessEngine.Piece;
import chessEngine.Square;

import java.util.ArrayList;
import java.util.List;

import chessEngine.ChessBoard;

public class Pawn extends Piece{
	
	// this boolean is used for knowing if the double promotion was done yet
	public boolean doublePromotion;
	public int pawnPosition ;
	public char pawnColor;
	public char pawnLetter ;
	public Square[][] chessBoard ;
	
	public Pawn(int position, char color, Square[][] board) {
		// here promotion is doublePromotion boolean
		super(position, color, board);
		this.chessBoard = board ;
		this.pawnPosition = position ;
		this.pawnColor = color ;
		
		final int row = pawnPosition / 8 ;
		final int column = pawnPosition % 8 ;
		
		if (pawnColor == 'b') {
			this.pawnLetter = 'p' ;
			board[row][column].piecesAttackingB = setAttackingSquares() ;
		} else {
			this.pawnLetter = 'P' ;
			board[row][column].piecesAttackingW = setAttackingSquares() ;
		}
		if ((position >= 8 && position <= 15) && (color == 'b')) {
			this.doublePromotion = false ;
		} else if ((position >= 48 && position <= 55) && (color == 'w')) {
			this.doublePromotion = false ;
		}
	}
	
	@Override
	public void move(int targetPosition) {
		if (isMoveLegal(new Move(targetPosition)) == true) {
			pawnPosition = targetPosition ;
			doublePromotion = true ;
		} else {
			System.out.println("Illegal Move");
		}
	}
	
	@Override
	public boolean isMoveLegal(Move pawnMove) {
		
		final int startRow = pawnPosition / 8 ;
		final int startColumn = pawnPosition % 8 ;

		final int targetRow = pawnMove.getTargetSquare() / 8 ;
		final int targetColumn = pawnMove.getTargetSquare() % 8 ;
		
		if (chessBoard[targetRow][targetColumn].representation == '-') {
			// suppose we just want to promote normally our pawn
			if ((pawnMove.getTargetSquare() - pawnPosition) == 8) {
				return true ;
			}
			
			// here we address the one-time double promotion case
			if ((pawnMove.getTargetSquare() - pawnPosition) == 16 
					&& doublePromotion == false) {
				return true ;
			}
			// En passant
			if(chessBoard[targetRow-1][targetColumn].representation != '-'){
				if((pawnMove.getTargetSquare() - pawnPosition) == 7
						|| (pawnMove.getTargetSquare() - pawnPosition) == 9) {
					return true ;
				}
			}
			
			
		} 
		if (chessBoard[targetRow][targetColumn].representation != '-') {
			if((pawnMove.getTargetSquare() - pawnPosition) == 7
					|| (pawnMove.getTargetSquare() - pawnPosition) == 9) {
				return true ;
			}
			
		}
		return false ;
		
	}

	@Override
	public List<Integer> setAttackingSquares() {
		
		List<Integer> listAttackingSquares = new ArrayList<>();
		listAttackingSquares.add(pawnPosition+7);
		listAttackingSquares.add(pawnPosition+9);
		
		return listAttackingSquares ;
	}
	
}
