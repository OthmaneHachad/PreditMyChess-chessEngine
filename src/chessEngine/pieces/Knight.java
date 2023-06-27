package chessEngine.pieces;

import java.util.List;

import chessEngine.Move;
import chessEngine.Piece;
import chessEngine.Square;

public class Knight extends Piece{

	
	public int knightPosition ;
	public char knightColor;
	public char knightLetter ;
	public Square[][] chessBoard ;
	
	
	public Knight(int position, char color, Square[][] board) {
		super(position, color, board);
		this.chessBoard = board ;
		this.knightPosition = position ;
		this.knightColor = color ;
		if (knightColor == 'b') {
			this.knightLetter = 'p' ;
		} else {
			this.knightLetter = 'P' ;
		}
	}

	@Override
	public boolean isMoveLegal(Move move) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void move(int targetPosition) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Integer> setAttackingSquares() {
		// TODO Auto-generated method stub
		return null;
	}

}
