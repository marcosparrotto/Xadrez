package xadrez;

import tabuleiro.Peça;
import tabuleiro.Tabuleiro;

public class PecaXadrez extends Peça {
	private Cor cor;

	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}


	
	
}
