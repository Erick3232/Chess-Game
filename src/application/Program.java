package application;

import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ChessMatch chessMath = new ChessMatch();
		
		while(true) {
			UI.printBoard(chessMath.getPieces());
			System.out.println();
			System.out.println("Source: ");
			ChessPosition source = UI.readChessPosition(scanner);
			
			System.out.println();
			System.out.println("Target: ");
			ChessPosition target = UI.readChessPosition(scanner);
			
			ChessPiece capturedPiece = chessMath.performChessPiece(source, target);
		}
		
	}
}
