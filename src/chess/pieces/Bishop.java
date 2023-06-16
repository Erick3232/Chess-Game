package chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece{

	public Bishop(Board board, Color color) {
		super(board, color);
	}
	public String toString() {
		return "B";
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] math = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0,0);
		
		p.setValues(position.getRows() - 1, position.getColumns() + 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			math[p.getRows()][p.getColumns()] = true;
			p.setRows(p.getRows() - 1);
			p.setColumns(p.getColumns() + 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			math[p.getRows()][p.getColumns()] = true;
		}
		
		p.setValues(position.getRows() - 1, position.getColumns() - 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			math[p.getRows()][p.getColumns()] = true;
			p.setRows(p.getRows() - 1);
			p.setColumns(p.getColumns() - 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			math[p.getRows()][p.getColumns()] = true;
		}
		
		p.setValues(position.getRows() + 1, position.getColumns() + 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			math[p.getRows()][p.getColumns()] = true;
			p.setRows(p.getRows() + 1);
			p.setColumns(p.getColumns() + 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			math[p.getRows()][p.getColumns()] = true;
		}
		p.setValues(position.getRows() + 1, position.getColumns() - 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			math[p.getRows()][p.getColumns()] = true;
			p.setRows(p.getRows() + 1);
			p.setColumns(p.getColumns() - 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			math[p.getRows()][p.getColumns()] = true;
		}
		return math;
	}

}
