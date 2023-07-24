package chessEngine;
import java.util.List ;
import java.util.ArrayList ;

public class Square{
	
	// static means that they are shared among all instances of the class
	public boolean isAttacked;
    public int squareNumber;
    public boolean pieceThere; // false indicates empty square
    public Piece piece;
    public char representation;
    public List<Piece> piecesAttackingW = new ArrayList<Piece>();
    public List<Piece> piecesAttackingB = new ArrayList<Piece>();

    public Square(boolean attacked, int number, boolean pieceThere) {
        this.isAttacked = attacked;
        this.squareNumber = number;
        this.pieceThere = pieceThere;
        this.representation = '-';
    }

    public Square copy(ChessBoard newBoard) {
        Square copiedSquare = new Square(this.isAttacked, this.squareNumber, this.pieceThere);
        copiedSquare.representation = this.representation;

        // Deep copy the piece
        if (this.piece != null) {
            copiedSquare.piece = this.piece.copy(newBoard);
        }

        copiedSquare.piecesAttackingB.addAll(this.piecesAttackingB);
        copiedSquare.piecesAttackingW.addAll(this.piecesAttackingW);

        // Deep copy the lists
       /*
        *  List<Piece> copiedPiecesAttackingW = new ArrayList<>();
        for (Piece piece : this.piecesAttackingW) {
            copiedPiecesAttackingW.add(piece.copy(newBoard));
        }
        copiedSquare.piecesAttackingW.addAll(copiedPiecesAttackingW);

        List<Piece> copiedPiecesAttackingB = new ArrayList<>();
        for (Piece piece : this.piecesAttackingB) {
            copiedPiecesAttackingB.add(piece.copy(newBoard));
        }
        copiedSquare.piecesAttackingB.addAll(copiedPiecesAttackingB);
        */


        return copiedSquare;
    }
}
