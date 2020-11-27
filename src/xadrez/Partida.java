package xadrez;

import tabuleiro.Posi��o;
import tabuleiro.Tabuleiro;
import xadrez.pe�as.Rei;
import xadrez.pe�as.Torre;

public class Partida {
	private Tabuleiro tabuleiro;
	
	public Partida() {
		tabuleiro = new Tabuleiro(8,8);
		setupInicial();
	}
	
	public Pe�aXadrez[][] getPe�as(){
		Pe�aXadrez[][] matriz = new Pe�aXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for(int i=0; i<tabuleiro.getLinhas();i++) {
			for(int j=0; j<tabuleiro.getColunas();j++) {
				matriz[i][j] = (Pe�aXadrez) tabuleiro.pe�a(i,j);
			}
		}
		return matriz;
	}
	
	private void setupInicial() {
		tabuleiro.lugarPe�a(new Torre(tabuleiro, Cor.Brancas), new Posi��o(1,1));
		tabuleiro.lugarPe�a(new Rei(tabuleiro, Cor.Pretas), new Posi��o(1,1));
	}
}
