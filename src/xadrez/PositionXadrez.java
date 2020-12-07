package xadrez;

import tabuleiro.Position;

public class PositionXadrez {
	private char coluna;
	private int linha;
	
	public PositionXadrez(char coluna, int linha) {
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
	
	protected Position toposition() {
		return new Position(8-linha,coluna-'a');
	}
	protected static PositionXadrez fromposition(Position position) {
		return new PositionXadrez((char)('a'+position.getColuna()),8 - position.getLinha());
	}
	
	@Override
	public String toString() {
		return "" + coluna + linha;
	}
}
