package mvc.controller;

import gamePieces.GridPoint;
import gamePieces.PieceType;
import gamePieces.Token;

public class Record {
	private Token piece;
	private GridPoint before;
	private GridPoint after;
	
	public Record(Token piece, GridPoint before, GridPoint after) {
		this.piece = piece;
		this.before = before;
		this.after = after;
	}
	
	public Token getPiece() {
		return this.piece;
	}
	
	public GridPoint getLastLoc() {
		return before;
	}
	
	public GridPoint getNextLoc() {
		return after;
	}

}
