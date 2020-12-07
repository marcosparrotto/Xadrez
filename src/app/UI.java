package app;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import xadrez.Cor;
import xadrez.Partida;
import xadrez.PieceXadrez;
import xadrez.PositionXadrez;

public class UI {
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	public static void limparTela() {
	    System.out.print("\033[H\033[2J");  
	    System.out.flush();  
	}
	
	public static PositionXadrez lendoPositionXadrez(Scanner sc){
		try{
			String s = sc.nextLine();
			char coluna = s.charAt(0);
			int linha = Integer.parseInt(s.substring(1));
			return new PositionXadrez(coluna, linha);
		} catch(RuntimeException e) {
			throw new InputMismatchException("Erro lendo posicao no Xadrez, valores validos a-h 1-8");
		}
	}
	
	public static void printPartida(Partida partida, List<PieceXadrez> capturadas) {
		printTabuleiro(partida.getpieces());
		System.out.println();
		printpiecesCapturadas(capturadas);
		System.out.println();
		System.out.println("Turno: " + partida.getTurno());
		if(!partida.getCheckMate()) {
			System.out.println("Esperando jogador: " + partida.getJogadorAtual());
			if(partida.getCheck()) {
				System.out.println();
				System.out.println("CHECK!!");
			}
		} else {
			System.out.println("CHECKMATE!!");
			System.out.println("Vencedor: " + partida.getJogadorAtual());
		}
		
	}
	
	public static void printTabuleiro(PieceXadrez[][] pe�as) {
		for(int i=0; i<pe�as.length;i++) {
			System.out.print((8-i)+ " ");
			for(int j=0; j<pe�as.length;j++) {
				printpiece(pe�as[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	public static void printTabuleiro(PieceXadrez[][] pe�as, boolean[][] possiveisMovimentos) {
		for(int i=0; i<pe�as.length;i++) {
			System.out.print((8-i)+ " ");
			for(int j=0; j<pe�as.length;j++) {
				printpiece(pe�as[i][j], possiveisMovimentos[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	private static void printpiece(PieceXadrez pe�a, boolean corLetra) {
		if(corLetra) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if (pe�a == null) {
            System.out.print("-" + ANSI_RESET);
        }
        else {
            if (pe�a.getCor() == Cor.Brancas) {
                System.out.print(ANSI_WHITE + pe�a + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_GREEN + pe�a + ANSI_RESET);
            }
        }
        System.out.print(" ");
	}
	
	private static void printpiecesCapturadas(List<PieceXadrez> capturadas) {
		List<PieceXadrez> brancas = capturadas.stream().filter(x -> x.getCor() == Cor.Brancas).collect(Collectors.toList());
		List<PieceXadrez> pretas = capturadas.stream().filter(x -> x.getCor() == Cor.Pretas).collect(Collectors.toList());
		System.out.println("Pecas capturadas:");
		System.out.print("Brancas: ");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(brancas.toArray()));
		System.out.print(ANSI_RESET);
		System.out.print("Pretas: ");
		System.out.print(ANSI_GREEN);
		System.out.println(Arrays.toString(pretas.toArray()));
		System.out.print(ANSI_RESET);
	}
}
