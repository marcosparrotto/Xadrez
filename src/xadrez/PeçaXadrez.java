package xadrez;

import tabuleiro.Pe�a;
import tabuleiro.Posi��o;
import tabuleiro.Tabuleiro;

public abstract class Pe�aXadrez extends Pe�a {
	private Cor cor;
	private int contagemMovimentos;

	public Pe�aXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public int getcontagemMovimentos() {
		return contagemMovimentos;
	}
	
	public void incrementarcontagemMovimentos() {
		contagemMovimentos++;
	}
	
	public void decrementarcontagemMovimentos() {
		contagemMovimentos--;
	}
	
	public Posi��oXadrez getPosi��oXadrez() {
		return Posi��oXadrez.fromPosi��o(posi��o);
	}
	
	protected boolean haPe�aOponente(Posi��o posi��o) {
		Pe�aXadrez p = (Pe�aXadrez) getTabuleiro().pe�a(posi��o);
		return p != null && p.getCor() != cor;
	}


	
	
}
