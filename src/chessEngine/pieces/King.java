package chessEngine.pieces;
import chessEngine.Piece;
import chessEngine.Square;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import chessEngine.ChessBoard;
import chessEngine.Move;

public class King extends Piece{

	public int kingPosition ;
	public char kingColor;
	public boolean isChecked ;
	public char kingLetter ;
	public Square[][] chessBoard ;
	
	public King(int position, char color, Square[][] board) {
		super(position, color, board);
		this.chessBoard = board ;
		this.kingPosition = position ;
		this.kingColor = color ;
		
		final int row = kingPosition / 8 ;
		final int column = kingPosition % 8 ;
		
		if (kingColor == 'b') {
			kingLetter = 'k' ;
			board[row][column].piecesAttackingB = setAttackingSquares() ;
		}else {
			kingLetter = 'K' ;
			board[row][column].piecesAttackingW = setAttackingSquares() ;
		}
		this.isChecked = KingChecked();
		

		
	}
	
	private boolean KingChecked() {
		final int row = kingPosition / 8 ;
		final int column = kingPosition % 8 ;
		return chessBoard[7-row][7-column].isAttacked ;
	}
	
	
	public void move(int targetPosition) {
		if (isMoveLegal(new Move(targetPosition)) == true) {
			kingPosition = targetPosition ;
		} else {
			System.out.println("Illegal Move");
		}
	}
	
	@Override
	public boolean isMoveLegal(Move kingMove) {
		final int startRow = kingPosition / 8 ;
		final int startColumn = kingPosition % 8 ;

		final int targetRow = kingMove.getTargetSquare() / 8 ;
		final int targetColumn = kingMove.getTargetSquare() % 8 ;
		
		List<Integer> piecesAttackingTarget = kingColor == 'w' ? chessBoard[targetRow][targetColumn].piecesAttackingW
				: chessBoard[targetRow][targetColumn].piecesAttackingB ;
		ArrayList<Integer> deplacement = new ArrayList<Integer>(Arrays.asList(7,8,9,1,-1,-7,-8,-9));
		
		// here its the same types of moves for capturing and moving around
		if (piecesAttackingTarget.isEmpty() == true) {
			if(deplacement.contains((kingMove.getTargetSquare() - kingPosition))) {
				return true ;
			}
		}
		
		return false ;
	}

	@Override
	public List<Integer> setAttackingSquares() {
		
		List<Integer> listAttackingSquares = new ArrayList<>();
		listAttackingSquares.add(kingPosition+7);
		listAttackingSquares.add(kingPosition+9);
		listAttackingSquares.add(kingPosition+8);
		listAttackingSquares.add(kingPosition+1);
		listAttackingSquares.add(kingPosition-1);
		listAttackingSquares.add(kingPosition-8);
		listAttackingSquares.add(kingPosition-9);
		listAttackingSquares.add(kingPosition-7);
		
		
		return listAttackingSquares ;
	}

}
