package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardGame.Board;
import boardGame.Piece;
import boardGame.Position;
import chess.pieces.Bishop;
import chess.pieces.Horse;
import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	private ChessPiece promoted;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

	
	public ChessMatch() {
		board = new Board(8,8);
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}
	public int getTurn() {
		return turn;
	}
	public void setTurn(int turn) {
		this.turn = turn;
	}
	public boolean getCheck() {
		return check;
	}
	public ChessPiece getPromoted() {
		return promoted;
	}
	public boolean getCheckMate() {
		return checkMate;
	}
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer(Color currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	public ChessPiece[][] getPieces(){
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for(int i = 0; i < board.getRows(); i++) {
			for(int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
	public ChessPiece performChessPiece(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source,target);
		Piece capturedPiece = makeMove(source,target);
		
		if(testCheck(currentPlayer)) {
			returnMove(source, target, capturedPiece);
			throw new ChessException("You are in the target of the check!");
		}
		ChessPiece movedPiece = (ChessPiece)board.piece(target);
		
		promoted = null;
		if (movedPiece instanceof Pawn) {
			if ((movedPiece.getColor() == Color.WHITE && target.getRows() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRows() == 7)) {
				promoted = (ChessPiece)board.piece(target);
				promoted = replacePromotedPiece("Q");
			}
		}
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		if(testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		} else {
			nextTurn();
		}
		return (ChessPiece)capturedPiece;
	}
	public ChessPiece replacePromotedPiece(String type) {
		if (promoted == null) {
			throw new IllegalStateException("There is no piece to be promoted");
		}
		if (!type.equals("B") && !type.equals("N") && !type.equals("R") & !type.equals("Q")) {			
			return promoted;
		}

		Position pos = promoted.getChessPosition().toPosition();
		Piece p = board.removePiece(pos);
		piecesOnTheBoard.remove(p);

		ChessPiece newPiece = newPiece(type, promoted.getColor());
		board.placePiece(newPiece, pos);
		piecesOnTheBoard.add(newPiece);

		return newPiece;
	}

	private ChessPiece newPiece(String type, Color color) {
		if (type.equals("B")) return new Bishop(board, color);
		if (type.equals("H")) return new Horse(board, color);
		if (type.equals("Q")) return new Queen(board, color);
		
		return new Rook(board, color);
	}
	
	private Piece makeMove(Position position, Position target) {
		ChessPiece p =(ChessPiece)board.removePiece(position);
		p.increaseMoveCount();
		Piece captured = board.removePiece(target);
		board.placePiece(p, target);
		
		if(capturedPieces != null) {
			piecesOnTheBoard.remove(captured);
			capturedPieces.add(captured);
		}
		
		if(p instanceof King && target.getColumns() == position.getColumns() + 2) {
			Position sourceTower = new Position(position.getRows(), position.getColumns() + 3);
			Position targetTower = new Position(position.getRows(), position.getColumns() + 1);
			
			ChessPiece rook = (ChessPiece)board.removePiece(sourceTower);
			board.placePiece(rook, targetTower);
			rook.increaseMoveCount();

		}
		if(p instanceof King && target.getColumns() == position.getColumns() - 2) {
			Position sourceTower = new Position(position.getRows(), position.getColumns() - 4);
			Position targetTower = new Position(position.getRows(), position.getColumns() - 1);
			
			ChessPiece rook = (ChessPiece)board.removePiece(sourceTower);
			board.placePiece(rook, targetTower);
			rook.increaseMoveCount();

		}
		return captured;
	}
	private void returnMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece)board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);
		
		if(capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
		
		if(p instanceof King && target.getColumns() == source.getColumns() + 2) {
			Position sourceTower = new Position(source.getRows(), source.getColumns() + 3);
			Position targetTower = new Position(source.getRows(), source.getColumns() + 1);
			
			ChessPiece rook = (ChessPiece)board.removePiece(targetTower);
			board.placePiece(rook, sourceTower);
			rook.decreaseMoveCount();

		}
		if(p instanceof King && target.getColumns() == source.getColumns() - 2) {
			Position sourceTower = new Position(source.getRows(), source.getColumns() - 4);
			Position targetTower = new Position(source.getRows(), source.getColumns() - 1);
			
			ChessPiece rook = (ChessPiece)board.removePiece(targetTower);
			board.placePiece(rook, sourceTower);
			rook.decreaseMoveCount();

		}
	}
	private void validateSourcePosition(Position position) {
		if(!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if(currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
			throw new ChessException("The Turn is not your!");
		}
		if(!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for chess position");
		}
	}
	private void validateTargetPosition(Position source,Position target) {
		if(!board.piece(source).possibleMove(target)) {
			throw new ChessException("The Piece can't move for position target");
		}
		
	}
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board");
	}
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRows()][kingPosition.getColumns()]) {
				return true;
			}
		}
		return false;
	}
	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) {
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			boolean[][] mat = p.possibleMoves();
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					if (mat[i][j]) {
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						returnMove(source, target, capturedPiece);
						
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column,row).toPosition());
		piecesOnTheBoard.add(piece);
		
	}
	private void initialSetup() {
		//Tower
				placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
		        
		        //Horse
		        placeNewPiece('b', 1, new Horse(board, Color.WHITE));
		        placeNewPiece('g', 1, new Horse(board, Color.WHITE));
		        
		        //Bishop
		        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
		        
		        //Kings and Queens
		        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
		        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
		        
		        //Pawns
		        placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
		        placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
		        placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
		        placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
		        placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
		        placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
		        placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
		        placeNewPiece('h', 2, new Pawn(board, Color.WHITE));
		        
		        placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
		        placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
		        placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
		        placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
		        placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
		        placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
		        placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
		        placeNewPiece('h', 7, new Pawn(board, Color.BLACK));
		        
		        //Tower
		        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
		        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
		        
		        //Horse
		        placeNewPiece('b', 8, new Horse(board, Color.BLACK));
		        placeNewPiece('g', 8, new Horse(board, Color.BLACK));
		        
		        //Bishop
		        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
		        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
		        
		        //Queen and King
		        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
		        placeNewPiece('d', 8, new Queen(board, Color.BLACK));

		
	}
	
}
