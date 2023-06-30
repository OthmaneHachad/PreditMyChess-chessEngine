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
	
	public Pawn(int position, char color, char letter, ChessBoard board) {
		// here promotion is doublePromotion boolean
		super(position, color, letter, board);
		this.targetMoves = setAttackingSquares();
		
		if ((position >= 8 && position <= 15) && (color == 'b')) {
			this.doublePromotion = false ;
		} else if ((position >= 48 && position <= 55) && (color == 'w')) {
			this.doublePromotion = false ;
		}
	}
	
	@Override
	public void move(Move move) {
		if (isMoveLegal(move) == true) {
			this.piecePosition = move.getTargetSquare() ;
			this.doublePromotion = true ;
		} else {
			System.out.println("Illegal Move");
		}
		
	}
	
	@Override
	public boolean isMoveLegal(Move move) {
		
		final int startRow = piecePosition / 8 ;
		final int startColumn = piecePosition % 8 ;

		final int targetRow = move.getTargetSquare() / 8 ;
		final int targetColumn = move.getTargetSquare() % 8 ;

		char currentplayer = this.pieceColor ;

		if (cb.isKingInCheck(currentplayer) == true){
			return false ;
		}
		
		if (chessBoard[7-targetRow][targetColumn].piece != null) {
			// suppose we just want to promote normally our pawn
			if ((move.getTargetSquare() - piecePosition) == 8) {
				return true ;
			}
			
			// here we address the one-time double promotion case
			if ((move.getTargetSquare() - piecePosition) == 16 
					&& doublePromotion == false) {
				return true ;
			}
			// En passant
			if(chessBoard[7-targetRow][targetColumn].piece != null){
				if((move.getTargetSquare() - piecePosition) == 7
						|| (move.getTargetSquare() - piecePosition) == 9) {
					return true ;
				}
			}
			
			
		} 
		if (chessBoard[7-targetRow][targetColumn].piece != null) {
			if((move.getTargetSquare() - piecePosition) == 7
					|| (move.getTargetSquare() - piecePosition) == 9) {
				return true ;
			}
			
		}
		return false ;
		
	}

	@Override
	public List<Move> setAttackingSquares() {
		
		List<Move> listAttackingSquares = new ArrayList<>();
		listAttackingSquares.add(new Move(piecePosition+7));
		chessBoard[7-((piecePosition+7)/8)][(piecePosition+7)%8].isAttacked = true ;
		listAttackingSquares.add(new Move(piecePosition+9));
		chessBoard[7-((piecePosition+9)/8)][(piecePosition+9)%8].isAttacked = true ;
		
		return listAttackingSquares ;
	}
	
}
