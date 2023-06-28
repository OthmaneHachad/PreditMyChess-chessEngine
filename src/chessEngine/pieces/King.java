package chessEngine.pieces;
import chessEngine.Piece;
import chessEngine.Square;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import chessEngine.ChessBoard;
import chessEngine.Move;

public class King extends Piece{

	protected boolean isChecked ;
	
	public King(int position, char color, char letter, ChessBoard board) {
		super(position, color, letter, board);
		this.piecePosition = position ;
		this.pieceColor = color ;
		
		final int row = this.piecePosition / 8 ;
		final int column = this.piecePosition % 8 ;
		
		if (this.pieceColor == 'b') {
			this.pieceLetter = 'k' ;
			chessBoard[row][column].piecesAttackingB = setAttackingSquares() ;
		}else {
			this.pieceLetter = 'K' ;
			chessBoard[row][column].piecesAttackingW = setAttackingSquares() ;
		}
		this.isChecked = KingChecked();
		

		
	}
	
	private boolean KingChecked() {
		final int row = this.piecePosition / 8 ;
		final int column = this.piecePosition % 8 ;
		return chessBoard[7-row][7-column].isAttacked ;
	}
	
	
	public void move(int targetPosition) {
		if (isMoveLegal(new Move(targetPosition)) == true) {
			this.piecePosition = targetPosition ;
		} else {
			System.out.println("Illegal Move");
		}
	}
	
	@Override
	public boolean isMoveLegal(Move kingMove) {
		final int startRow = this.piecePosition / 8 ;
		final int startColumn = this.piecePosition % 8 ;

		final int targetRow = kingMove.getTargetSquare() / 8 ;
		final int targetColumn = kingMove.getTargetSquare() % 8 ;
		
		List<Integer> piecesAttackingTarget = this.pieceColor == 'w' ? chessBoard[targetRow][targetColumn].piecesAttackingW
				: chessBoard[targetRow][targetColumn].piecesAttackingB ;
		ArrayList<Integer> deplacement = new ArrayList<Integer>(Arrays.asList(7,8,9,1,-1,-7,-8,-9));
		
		// here its the same types of moves for capturing and moving around
		if (piecesAttackingTarget.isEmpty() == true) {
			if(deplacement.contains((kingMove.getTargetSquare() - this.piecePosition))) {
				return true ;
			}
		}
		
		return false ;
	}

	@Override
	public List<Integer> setAttackingSquares() {
		
		List<Integer> listAttackingSquares = new ArrayList<>();
		listAttackingSquares.add(this.piecePosition+7);
		listAttackingSquares.add(this.piecePosition+9);
		listAttackingSquares.add(this.piecePosition+8);
		listAttackingSquares.add(this.piecePosition+1);
		listAttackingSquares.add(this.piecePosition-1);
		listAttackingSquares.add(this.piecePosition-8);
		listAttackingSquares.add(this.piecePosition-9);
		listAttackingSquares.add(this.piecePosition-7);
		
		
		return listAttackingSquares ;
	}

}
