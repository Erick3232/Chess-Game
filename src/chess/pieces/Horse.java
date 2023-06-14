package chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessPiece;
import chess.Color;

public class Horse extends ChessPiece{

	public Horse(Board board, Color color) {
		super(board, color);
	}
	public String toString() {
		return "H";
	}
	
	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] math = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0,0);
		
		p.setValues(position.getRows() - 2, position.getColumns() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			math[p.getRows()][p.getColumns()] = true;
		}
		p.setValues(position.getRows() - 2, position.getColumns() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			math[p.getRows()][p.getColumns()] = true;
		}
		p.setValues(position.getRows() + 2, position.getColumns() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			math[p.getRows()][p.getColumns()] = true;
		}
		p.setValues(position.getRows() + 2, position.getColumns() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			math[p.getRows()][p.getColumns()] = true;
		}
		
		return math;
	}

}
