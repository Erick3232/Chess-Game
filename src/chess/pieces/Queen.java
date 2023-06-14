package chess.pieces;

import boardGame.Board;
import boardGame.Position;
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
		boolean[][] math = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0,0);
		//Move UP
				p.setValues(position.getRows() - 1, position.getColumns());
				while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
					math[p.getRows()][p.getColumns()] = true;
					p.setRows(p.getRows() - 1);
				}
				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
					math[p.getRows()][p.getColumns()] = true;
				}
				
				//Move LEFT
				p.setValues(position.getRows(), position.getColumns() - 1);
				while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
					math[p.getRows()][p.getColumns()] = true;
					p.setColumns(p.getColumns() - 1);
				}
				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
					math[p.getRows()][p.getColumns()] = true;
				}
				
				//Move RIGHT
				p.setValues(position.getRows(), position.getColumns() + 1);
				while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
					math[p.getRows()][p.getColumns()] = true;
					p.setColumns(p.getColumns() + 1);
				}
				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
					math[p.getRows()][p.getColumns()] = true;
				}
				
				//Move DOWN
				p.setValues(position.getRows() + 1, position.getColumns());
				while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
					math[p.getRows()][p.getColumns()] = true;
					p.setRows(p.getRows() + 1);
				}
				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
					math[p.getRows()][p.getColumns()] = true;
				}
				
		p.setValues(position.getRows() - 1, position.getColumns() + 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			math[p.getRows()][p.getColumns()] = true;
			p.setRows(p.getRows() - 1);
			p.setColumns(p.getColumns() + 1);
		}
		
		p.setValues(position.getRows() - 1, position.getColumns() - 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			math[p.getRows()][p.getColumns()] = true;
			p.setRows(p.getRows() - 1);
			p.setColumns(p.getColumns() - 1);
		}
		
		p.setValues(position.getRows() + 1, position.getColumns() + 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			math[p.getRows()][p.getColumns()] = true;
			p.setRows(p.getRows() + 1);
			p.setColumns(p.getColumns() + 1);
		}
		p.setValues(position.getRows() + 1, position.getColumns() - 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			math[p.getRows()][p.getColumns()] = true;
			p.setRows(p.getRows() + 1);
			p.setColumns(p.getColumns() - 1);
		}
		
		return math;
	}

}
