import java.util.List;

import chessEngine.ChessBoard;
import chessEngine.pieces.King;
import chessEngine.pieces.Pawn;
import chessEngine.Move;
import chessEngine.Piece;
import chessEngine.Square;

public class Main {

	public static void main(String[] args) {

		final ChessBoard myChessBoard = new ChessBoard("5k2/5p2/1p6/2rpp1Bb/q2N3P/2b2p1n/1K6/2Q5 w - - 0 1");
		myChessBoard.printBoard();
		System.out.println("we create a new board==================================");

		ChessBoard updatedBoard = myChessBoard.clone();

		Piece bishop = updatedBoard.boardMatrix[3][6].piece ;
		King whiteKing = updatedBoard.getWhiteKing() ;
		Square kingSquare = updatedBoard.boardMatrix[6][1] ;
	

		// ***************************************************
		/*
		 * Our problem here is that Bishop can physically move to 11
		 * But his King is in check, thus it 38 -> 11 is illegal
		 * We then generate the pieces that set the squares isattacked attribute
		 * to true or false depending on the setAttackingSquares method
		 * But the king attribute "isChecked" is not updated
		 * So programmatically, the square is under attack, but the king is not checked
		 * TODO  find a way to update the king's information
		 */
		System.out.println(bishop.piecePosition);
		System.out.println("Is the white king's square attacked :" + kingSquare.isAttacked);
		System.out.println("Is the white king checked :" + whiteKing.isChecked);
		boolean move_succesfull = updatedBoard.MakeMove(new Move(11, bishop));
		System.out.println("Can the bishop move : " + move_succesfull);
		// updated bishop position
		System.out.println(bishop.piecePosition);
		updatedBoard.printBoard();
		
	
	}

	
	protected static int Evaluation(ChessBoard board) {
		/* 
		 * We will only consider Piece Material | King Safety | Control of the Center
		 */
		return -1 ;
	}

	public static double Search(ChessBoard board, int depth, int player){

		List<Move> moves = (player == 1) ? ChessBoard.targetedByWhite : ChessBoard.targetedByBlack ;
		double bestEval ;

		if (depth == 0){
			return Evaluation(board);
		}
		
		// performs a deepcopy
		

		if (player == 'w'){
			bestEval = Double.NEGATIVE_INFINITY ;
			for (Move move : moves){
				ChessBoard tempBoard = board.clone();
				board.MakeMove(move) ;
				// make the move
				double eval = Search(tempBoard, depth - 1, (player * -1));
				bestEval = Math.max(bestEval, eval);
			}
			return bestEval ;
		}else {
			bestEval = Double.POSITIVE_INFINITY ;
			for (Move move : moves){
				ChessBoard tempBoard = board.clone();
				double eval = Search(tempBoard, depth - 1, (player * -1));
				bestEval = Math.min(bestEval, eval);
			}
			return bestEval ;
		}
	}

	



}
