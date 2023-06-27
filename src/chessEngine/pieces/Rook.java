package chessEngine.pieces;

import java.util.ArrayList;
import java.util.List;

import chessEngine.Move;
import chessEngine.Piece;
import chessEngine.Square;
import chessEngine.ChessBoard;

public class Rook extends Piece{

	
	public int rookPosition ;
	public char rookColor;
	public char rookLetter ;
	public Square[][] chessBoard ;
	
	
	public Rook(int position, char color, Square[][] board) {
		super(position, color, board);
		this.chessBoard = board ;
		this.rookPosition = position ;
		this.rookColor = color ;
		if (rookColor == 'b') {
			this.rookLetter = 'p' ;
		} else {
			this.rookLetter = 'P' ;
		}
	}

	@Override

	public boolean isMoveLegal(Move move){
		final int startRow = rookPosition / 8 ;
		final int startColumn = rookPosition % 8 ;

		final int target  = move.getTargetSquare() ;

		final int targetRow = target / 8 ;
		final int targetColumn = target % 8 ;

		// make sure target not off grid
		if (target < 0 || target > 63){
			return false ;
		}

		if (target == this.piecePosition){
			return false ;
		}

		// Move horizontal
		if (startRow == targetRow){
			System.out.println("ITS A HORIZONTAL MOVE");
			return pathClear(startColumn, targetColumn, startRow, true, chessBoard);
		} else if (startColumn == targetColumn){
			System.out.println("ITS A VERTICAL MOVE");
			return pathClear(startRow, targetRow, startColumn, false, chessBoard);
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
	public void move(int targetPosition) {
		if (isMoveLegal(new Move(targetPosition)) == true) {
			rookPosition = targetPosition ;
		} else {
			System.out.println("Illegal Move");
		}
	}

	@Override
	public List<Integer> setAttackingSquares(){
		List<Integer> listAttackingSquares = new ArrayList<>() ;

		int start = this.piecePosition ;
		int column = start % 8;
		int row = start / 8 ;

		for (int i = column; i < 64; i = i + 8){
			boolean verticalMove = isMoveLegal(new Move(i));
			if (verticalMove == true){
				//chessBoard[7-(i/8)][i%8].representation = 'o' ;
				System.out.println("New Vertical Move !");
				listAttackingSquares.add(i);
			}
		}

		for (int i = row *8; i < (row+1)*8; i++ ){
			System.out.println("New Move: " + i);
			boolean verticalMove = isMoveLegal(new Move(i));
			if (verticalMove == true){
				//chessBoard[7-(i/8)][i%8].representation = 'o' ;
				System.out.println("New Horizontal Move !");
				listAttackingSquares.add(i);
			}
		}
		
		return listAttackingSquares ;
	}

}
