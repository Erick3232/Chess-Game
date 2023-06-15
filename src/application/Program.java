package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ChessMatch chessMath = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();
		while(true) {
			try {
				UI.clearScreen();
				
				UI.printMatch(chessMath,captured);
				System.out.println();
				System.out.println("Source: ");
				ChessPosition source = UI.readChessPosition(scanner);
				
				boolean[][] possibleMoves = chessMath.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMath.getPieces(), possibleMoves);
				
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(scanner);
				
				ChessPiece capturedPiece = chessMath.performChessPiece(source, target);
				if(capturedPiece != null) {
					captured.add(capturedPiece);
				}
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
