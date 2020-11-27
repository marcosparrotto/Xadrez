package xadrez;

import tabuleiro.Pe�a;
import tabuleiro.Tabuleiro;

public class PecaXadrez extends Pe�a {
	private Cor cor;

	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}


	
	
}
