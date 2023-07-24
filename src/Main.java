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
		System.out.println("==================================");

		// TODO FIND THE PROBLEM HERE WITH THE CLONING (chessboard class) !
		ChessBoard updatedBoard = myChessBoard.clone();

		Piece bishop = updatedBoard.boardMatrix[3][6].piece ;
		King whiteKing = (King) updatedBoard.boardMatrix[6][1].piece ;
		Square kingSquare = updatedBoard.boardMatrix[6][1] ;
	
		System.out.println(bishop.piecePosition);
		System.out.println(kingSquare.isAttacked);
		boolean move_succesfull = updatedBoard.MakeMove(new Move(11, bishop));
		System.out.println(move_succesfull);
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
