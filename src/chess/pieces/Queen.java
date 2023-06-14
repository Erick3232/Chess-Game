package chess.pieces;

import boardGame.Board;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece{

	public Queen(Board board, Color color) {
		super(board, color);
	}
	
	public String toString() {
		return "Q";
	}
	@Override
	public boolean[][] possibleMoves() {
		return null;
	}

}
