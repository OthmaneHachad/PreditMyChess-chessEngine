package chessEngine;
import java.util.List ;
import java.util.ArrayList ;

public class Square {
	
	// static means that they are shared among all instances of the class
	public boolean isAttacked;
    public int squareNumber;
    public boolean pieceThere; // false indicates empty square
    public Piece piece;
    public char representation;
    public List<Integer> piecesAttackingW = new ArrayList<>();
    public List<Integer> piecesAttackingB = new ArrayList<>();

    public Square(boolean attacked, int number, boolean pieceThere) {
        this.isAttacked = attacked;
        this.squareNumber = number;
        this.pieceThere = pieceThere;
        this.representation = '-';
    }
}
