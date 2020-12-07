package xadrez;

import tabuleiro.Piece;
import tabuleiro.Position;
import tabuleiro.Tabuleiro;

public abstract class PieceXadrez extends Piece {
	private Cor cor;
	private int contagemMovimentos;

	public PieceXadrez(Tabuleiro tabuleiro, Cor cor) {
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
	
	public PositionXadrez getpositionXadrez() {
		return PositionXadrez.fromposition(position);
	}
	
	protected boolean hapieceOponente(Position position) {
		PieceXadrez p = (PieceXadrez) getTabuleiro().piece(position);
		return p != null && p.getCor() != cor;
	}


	
	
}
