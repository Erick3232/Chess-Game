package boardGame;

public class Board {
	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int rows, int columns) {
		if(rows < 1 || columns < 1) {
			throw new BoardException("Error creating board!");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}
	public int getColumns() {
		return columns;
	}
	public Piece piece(int rows, int columns) {
		if(!positionExists(rows, columns)) {
			throw new BoardException("Position, not in the board!");
		}
		return pieces[rows][columns];
	}
	public Piece piece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Position, not in the board!");
		}
		return pieces[position.getRows()][position.getColumns()];
	}
	public void placePiece(Piece piece, Position position) {
		if(thereIsAPiece(position)) {
			throw new BoardException("There is already piece on this position!"+ " Position: " + "{" + position + "}");
		}
		pieces[position.getRows()][position.getColumns()] = piece;
		piece.position = position;
	}
	public boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}
	public boolean positionExists(Position position) {
		return positionExists(position.getRows(),position.getColumns());
	}
	public boolean thereIsAPiece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Position, not in the board!");
		}	
		return piece(position) != null;
	}
	
}
