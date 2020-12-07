package xadrez.pieces;

import tabuleiro.Position;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PieceXadrez;

public class Bispo extends PieceXadrez {

	public Bispo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Position p = new Position(0, 0);

		// Cima + Direita
		p.setValores(position.getLinha() - 1, position.getColuna() - 1);
		while (getTabuleiro().positionExiste(p) && !getTabuleiro().existePiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() - 1, p.getColuna() - 1);
		}
		if (getTabuleiro().positionExiste(p) && hapieceOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Cima + Esquerda
		p.setValores(position.getLinha() - 1, position.getColuna() + 1);
		while (getTabuleiro().positionExiste(p) && !getTabuleiro().existePiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() - 1, p.getColuna() + 1);
		}
		if (getTabuleiro().positionExiste(p) && hapieceOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// Baixo + Direita
		p.setValores(position.getLinha() + 1, position.getColuna() - 1);
		while (getTabuleiro().positionExiste(p) && !getTabuleiro().existePiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() + 1, p.getColuna() - 1);
		}
		if (getTabuleiro().positionExiste(p) && hapieceOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Baixo + Esquerda
		p.setValores(position.getLinha() + 1, position.getColuna() + 1);
		while (getTabuleiro().positionExiste(p) && !getTabuleiro().existePiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() + 1, p.getColuna() + 1);
		}
		if (getTabuleiro().positionExiste(p) && hapieceOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		return mat;
	}
}
