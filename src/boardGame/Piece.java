package boardGame;

public abstract class Piece {
	protected Position position;
	private Board board;
	
	public Piece(Board board) {
		super();
		this.board = board;
		position = null;
	}

	protected Board getBoard() {
		return board;
	}
	
	public abstract boolean[][] possibleMoves();
	
	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRows()][position.getColumns()]; //hulk methods = gancho de metodos
	}
	
	public boolean isThereAnyPossibleMove() {
		boolean[][] math = possibleMoves();
		for(int i = 0; i < math.length; i++) {
			for(int j = 0; j < math.length; j++) {
				if(math[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}
