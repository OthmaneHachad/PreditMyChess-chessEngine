package chessEngine;


import chessEngine.Square ;
import java.util.HashMap ;
import java.util.List;
import java.util.ArrayList;

import chessEngine.pieces.Bishop;
import chessEngine.pieces.King;
import chessEngine.pieces.Knight;
import chessEngine.pieces.Pawn;
import chessEngine.pieces.Queen;
import chessEngine.pieces.Rook;

import java.util.Map ;

public class ChessBoard {

	public static final String EMPTY = "-" ;
	public static final Square[][] boardMatrix = new Square[8][8];
	public static String FEN ;

	private static King whiteKing ;
	private static King blackKing ;

	List<Integer> targetedByWhite = new ArrayList<Integer>() ;
	List<Integer> targetedByBlack = new ArrayList<Integer>() ;
	
	//Constructor
	public ChessBoard(String FEN_board) {
		this.FEN = FEN_board ;
		createBoard();
		printBoard();
		setAttackedSquares();
	}


	
	// Initiate the board
	public void createBoard() {
		
		String[] FEN_parsed = FEN.split(" ");
		Map<String, String> FEN_map = Map.of(
				"BoardLayout", FEN_parsed[0],
				"Player2move", FEN_parsed[1],
				"Roque", FEN_parsed[2],
				"EnPassant", FEN_parsed[3],
				"HalfMoveClock", FEN_parsed[FEN_parsed.length - 2],
				"FullMoveClock", FEN_parsed[FEN_parsed.length - 1]
				) ;
		
		
	
		String[] FEN_rows = FEN_map.get("BoardLayout").split("/") ; 
		for (int i = 0; i < 8; i ++) {
			int column = 0 ;
			for (char c : FEN_rows[i].toCharArray()) {
				
				if (Character.isDigit(c)) {
					for (int a = 0; a < Character.getNumericValue(c); a++) {
						boardMatrix[i][column] = new Square(false, ((7-i)*8 + column), false);
						column ++ ;
					}
				} else {
					boardMatrix[i][column] = new Square(false, ((7-i)*8 + column), true);
					boardMatrix[i][column].representation = c ;
					
					column ++ ;
					
				}
				
			}
		}
		
		// Loop a second time after all the Squares were initialized
		for (int j = 0; j < 8; j ++) {
			int column = 0 ;
			for (Square square: boardMatrix[j]) {
				char c = square.representation ;
				switch (c) {
				case 'p':
					square.piece =  new Pawn(((7-j)*8 + column), 'b', c, this) ; 
					break;
				case 'P':
					square.piece = new Pawn(((7-j)*8 + column), 'w', c, this);
					break;
				case 'k':
					whiteKing = new King(((7-j)*8 + column), 'b', c, this);
					square.piece = whiteKing;
					break;
				case 'K':
					blackKing = new King(((7-j)*8 + column), 'w', c, this);
					square.piece = blackKing;
					break;
				case 'Q':
					square.piece = new Queen(((7-j)*8 + column), 'w', c, this);
					break ;
				case 'q':
					square.piece = new Queen(((7-j)*8 + column), 'b', c, this);
					break ;
				case 'N':
					square.piece = new Knight(((7-j)*8 + column), 'w', c, this);
					break ;
				case 'n':
					square.piece = new Knight(((7-j)*8 + column), 'b', c, this);
					break ;
				case 'R':
					square.piece = new Rook(((7-j)*8 + column), 'w', c, this);
					break ;
				case 'r':
					square.piece = new Rook(((7-j)*8 + column), 'b', c, this);
					break ;
				case 'b':
					square.piece = new Bishop(((7-j)*8 + column), 'b', c, this);
					break ;
				case 'B':
					square.piece = new Bishop(((7-j)*8 + column), 'w', c, this);
					break ;
				
				}
				
				column ++ ;
			}
		}
		
	}
	



	public static void printBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(boardMatrix[i][j].representation + " ");
				// boardMatrix[i][j].squareNumber + "" + 
            }
            System.out.println();
        }
    }

	public King getWhiteKing(){
		return this.whiteKing;
	}

	public King getBlackKing(){
		return this.blackKing ;
	}

	public boolean isKingInCheck(char player){
		King king = (player == 'w') ? getWhiteKing() : getBlackKing() ;
		return king.isChecked ;
	}
	

	private void setAttackedSquares(){
		for (int j = 0; j <=7; j ++) {
			for (Square square: boardMatrix[j]) {
				if (square.piece != null){
					System.out.println(square.piece.pieceLetter);
					square.piece.targetMoves = square.piece.setAttackingSquares() ;
					for (Move mv: square.piece.targetMoves){
						if (square.piece.pieceColor == 'w' && !targetedByWhite.contains(mv.getTargetSquare())){
							targetedByWhite.add(mv.getTargetSquare());
						} else if (square.piece.pieceColor == 'b' && !targetedByBlack.contains(mv.getTargetSquare())){
							targetedByBlack.add(mv.getTargetSquare());
						}
					}
				}
			}
		}
	}

}
