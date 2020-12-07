package app;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.Partida;
import xadrez.PieceXadrez;
import xadrez.PositionXadrez;
import xadrez.XadrezException;

public class Main {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		Partida partida = new Partida();
		List<PieceXadrez> capturadas = new ArrayList<>();
		
		while(!partida.getCheckMate()) {
			try {
				UI.limparTela();
				UI.printPartida(partida, capturadas);
				System.out.println();
				System.out.print("Origem: ");
				PositionXadrez origem = UI.lendoPositionXadrez(sc);
				
				boolean[][] possiveisMovimentos = partida.possiveisMovimentos(origem);
				UI.limparTela();
				UI.printTabuleiro(partida.getpieces(), possiveisMovimentos);		
				System.out.println();
				System.out.print("Destino: ");
				PositionXadrez destino = UI.lendoPositionXadrez(sc);
				
				PieceXadrez pieceCapturada = partida.executarMovimentoXadrez(origem, destino);
				if(pieceCapturada != null) {
					capturadas.add(pieceCapturada);
				}
				if(partida.getpromovida() != null) {
					System.out.print("Escolha entre (T/C/B/D) para promover: ");
					String type = sc.nextLine().toUpperCase();
					while(!type.equals("T") && !type.equals("C") && !type.equals("B") && !type.equals("D")) {
						System.out.print("Tipo nao encontrado, escolha entre (T/C/B/D) para promover: ");
						type = sc.nextLine().toUpperCase();
					}
					partida.alterarpiecePromovida(type);
				}
			} catch (XadrezException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			
		}
		UI.limparTela();
		UI.printPartida(partida, capturadas);
	}

}
