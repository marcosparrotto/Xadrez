package xadrez;

import tabuleiro.Posi��o;

public class Posi��oXadrez {
	private char coluna;
	private int linha;
	
	public Posi��oXadrez(char coluna, int linha) {
		if(coluna < 'a' || coluna > 'h' || linha < 1 || linha >8) {
			throw new XadrezException("Erro na posicao do xadrez");
		}
		this.coluna = coluna;
		this.linha = linha;
	}

	public char getColuna() {
		return coluna;
	}

	public int getLinha() {
		return linha;
	}
	
	protected Posi��o toPosi��o() {
		return new Posi��o(8-linha,coluna-'a');
	}
	protected static Posi��oXadrez fromPosi��o(Posi��o posi��o) {
		return new Posi��oXadrez((char)('a'+posi��o.getColuna()),8 - posi��o.getLinha());
	}
	
	@Override
	public String toString() {
		return "" + coluna + linha;
	}
}
