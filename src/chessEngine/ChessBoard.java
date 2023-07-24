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

public class ChessBoard implements Cloneable{

	public static final String EMPTY = "-" ;
	public static final Square[][] boardMatrix = new Square[8][8];
	public static String FEN ;

	private static King whiteKing ;
	private static King blackKing ;

	public static List<Move> targetedByWhite = new ArrayList<Move>() ;
	public static List<Move> targetedByBlack = new ArrayList<Move>() ;
	
	//Constructor
	public ChessBoard(String FEN_board) {
		this.FEN = FEN_board ;
		
		createBoard();
		printBoard();
		setAttackedSquares();

		this.getBlackKing().isChecked = this.getBlackKing().KingChecked();
		this.getWhiteKing().isChecked = this.getWhiteKing().KingChecked();
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


	public boolean MakeMove(Move move) {

		//ChessBoard futureBoard = this.clone();
		Piece piece = move.getPieceMoving() ;
		int target  = move.getTargetSquare() ;

		Square targetSquare = this.boardMatrix[7 - (target / 8)][target % 8] ;
		// used only as a reference to create a brand new Square
		Square startSquare = this.boardMatrix[7 - (piece.piecePosition / 8)][piece.piecePosition % 8] ;
		
		char c = piece.pieceColor ;
		/*
		 * check if there is a piece there not of the same color
		 * empty the starting square
		 * update the target square
		 */

		

		if (targetSquare.pieceThere == false) {
			targetSquare.pieceThere = true ;
		}
		if (piece.isMoveLegal(move)){
			piece.move(move);
			System.out.println("move legal");
			// update start square
			this.boardMatrix[7 - (piece.piecePosition / 8)][piece.piecePosition % 8] = new Square(startSquare.isAttacked, startSquare.squareNumber, false);

			// update target square
			targetSquare.piece = piece.copy(this) ;
			targetSquare.piece.piecePosition = target ;
			targetSquare.piece.targetMoves = targetSquare.piece.setAttackingSquares() ;
			targetSquare.isAttacked = (c == 'w') ? (targetSquare.piecesAttackingB.isEmpty() == false) : (targetSquare.piecesAttackingW.isEmpty() == false) ;
			targetSquare.representation = piece.pieceLetter ;
		} else {
			return false ;
		}
		
		return true ;
		
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
							targetedByWhite.add(mv);
						} else if (square.piece.pieceColor == 'b' && !targetedByBlack.contains(mv.getTargetSquare())){
							targetedByBlack.add(mv);
						}
					}
				}
			}
		}
	}

	@Override
	public ChessBoard clone() {
		try {
			// TODO FIND THE PROBLEM HERE WITH THE CLONING !
			ChessBoard clonedBoard = (ChessBoard) super.clone();

			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					clonedBoard.boardMatrix[i][j] = this.boardMatrix[i][j].copy(clonedBoard);
				}
			}

			return clonedBoard;
		} catch (CloneNotSupportedException e) {
			// Handle cloning exception if needed
			System.out.println("Cloning Process Failed");
			System.out.println(e);
			return null;
		}
	}




}
