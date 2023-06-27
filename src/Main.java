import java.util.List;

import chessEngine.ChessBoard;
import chessEngine.pieces.Pawn;
import chessEngine.Move;

public class Main {

	public static void main(String[] args) {
		// rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2 
		// 8/2k5/5B2/P2pP1n1/Pq2N3/pppR4/3P2P1/5K2 w - - 0 1
		// 3N4/1N6/6P1/2P2Kpq/P2P4/kpP5/r3p3/4bB2 w - - 0 1
		final ChessBoard myChessBoard = new ChessBoard("7K/4Np1R/1p1P4/p2p1B2/5k1b/2r4P/4q2p/7b w - - 0 1");
		
		List<Integer> mylist = myChessBoard.boardMatrix[6][4].piece.setAttackingSquares() ;
		System.out.println("=========");
		for (int i =0; i < mylist.size() ; i ++) {
			System.out.println(mylist.get(i));
		}
		
		//myChessBoard.boardMatrix[6][4].representation = 'o' ;
		myChessBoard.printBoard();
		//System.out.println(myChessBoard.boardMatrix[6][0].piece.getClass().getName());
		//boolean mybool = myChessBoard.boardMatrix[6][0].piece.isMoveLegal(new Move(24));
		//System.out.println(mybool);


		//System.out.println(mylist.size());
		
	}

}
