import java.util.List;

import chessEngine.ChessBoard;
import chessEngine.pieces.Pawn;
import chessEngine.Move;
import chessEngine.Piece;

public class Main {

	public static void main(String[] args) {
		// rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2 
		// 8/2k5/5B2/P2pP1n1/Pq2N3/pppR4/3P2P1/5K2 w - - 0 1
		// 3N4/1N6/6P1/2P2Kpq/P2P4/kpP5/r3p3/4bB2 w - - 0 1
		final ChessBoard myChessBoard = new ChessBoard("2Q4K/6P1/1p2P3/2bP2B1/1p4rp/p7/3Pr2p/3k4 w - - 0 1");
		Piece piece = myChessBoard.boardMatrix[0][2].piece ;
		List <Move> mylist = piece.targetMoves ;
		System.out.println("=========");
		for (int i =0; i < mylist.size() ; i ++) {
			System.out.println(mylist.get(i).getTargetSquare());
		}
		
		//myChessBoard.boardMatrix[6][4].representation = 'o' ;
		myChessBoard.printBoard();
		//System.out.println(myChessBoard.boardMatrix[6][0].piece.getClass().getName());
		//boolean mybool = myChessBoard.boardMatrix[6][0].piece.isMoveLegal(new Move(24));
		//System.out.println(mybool);


		//System.out.println(mylist.size());
		
	}

}
