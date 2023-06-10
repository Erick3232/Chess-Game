package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ChessMatch chessMath = new ChessMatch();
		
		while(true) {
			try {
				UI.clearScreen();
				
				UI.printBoard(chessMath.getPieces());
				System.out.println();
				System.out.println("Source: ");
				ChessPosition source = UI.readChessPosition(scanner);
				
				System.out.println();
				System.out.println("Target: ");
				ChessPosition target = UI.readChessPosition(scanner);
				
				ChessPiece capturedPiece = chessMath.performChessPiece(source, target);
			}
			catch(ChessException e) {
				System.out.println(e.getMessage());
				scanner.next();
			}
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				scanner.next();
			}
		}
		
	}
}
