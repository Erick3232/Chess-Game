package chess.pieces;

import boardGame.Board;
import chess.ChessPiece;
import chess.Color;

public class Horse extends ChessPiece{

	public Horse(Board board, Color color) {
		super(board, color);
	}
	public String toString() {
		return "H";
	}

	@Override
	public boolean[][] possibleMoves() {
		return null;
	}

}
