package chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{
	
	private ChessMatch chess;
	public King(Board board, Color color, ChessMatch chess) {
		super(board, color);
		this.chess = chess;
	}
	@Override
	public String toString() {
		return "K";
	}
	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}
	private boolean testRookCastling(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
	}
	@Override
	public boolean[][] possibleMoves() {	
		boolean[][] math = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0,0);
		
		//Move UP
		p.setValues(position.getRows() - 1, position.getColumns());
		if(getBoard().positionExists(p) && canMove(p)) {
			math[p.getRows()][p.getColumns()] = true;
		}
		
		//Move DOWN
		p.setValues(position.getRows() + 1, position.getColumns());
		if(getBoard().positionExists(p) && canMove(p)) {
			math[p.getRows()][p.getColumns()] = true;
		}
		
		//Move RIGHT
		p.setValues(position.getRows(), position.getColumns() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			math[p.getRows()][p.getColumns()] = true;
		}
		
		//Move LEFT
		p.setValues(position.getRows(), position.getColumns() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			math[p.getRows()][p.getColumns()] = true;
		}
		
		p.setValues(position.getRows() - 1, position.getColumns() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			math[p.getRows()][p.getColumns()] = true;
		}
		
		p.setValues(position.getRows() + 1, position.getColumns() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			math[p.getRows()][p.getColumns()] = true;
		}
		
		p.setValues(position.getRows() + 1, position.getColumns() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			math[p.getRows()][p.getColumns()] = true;
		}
		p.setValues(position.getRows() - 1, position.getColumns() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			math[p.getRows()][p.getColumns()] = true;
		}
		
		if(getMoveCount() == 0 && !chess.getCheck()) {
			Position p1 = new Position(position.getRows(), position.getColumns() + 3);
			if(testRookCastling(p1)) {
				Position p2 = new Position(position.getRows(), position.getColumns() + 1);
				Position p3 = new Position(position.getRows(), position.getColumns() + 2);
				if(getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
					math[position.getRows()][position.getColumns() + 2] = true;
				}
			}
		
		}
		if(getMoveCount() == 0 && !chess.getCheck()) {
			Position p4 = new Position(position.getRows(), position.getColumns() - 4);
			if(testRookCastling(p4)) {
				Position p5 = new Position(position.getRows(), position.getColumns() - 1);
				Position p6 = new Position(position.getRows(), position.getColumns() - 2);
				Position p7 = new Position(position.getRows(), position.getColumns() - 3);
				if(getBoard().piece(p5) == null && getBoard().piece(p6) == null && getBoard().piece(p7) == null) {
					math[position.getRows()][position.getColumns() - 2] = true;
				}
			}
	}
		return math;
	}
}
