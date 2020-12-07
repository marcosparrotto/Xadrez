package xadrez.pieces;

import tabuleiro.Position;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PieceXadrez;

public class Cavalo extends PieceXadrez {

	public Cavalo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "C";
	}

	private boolean podeMover(Position position) {
		PieceXadrez p = (PieceXadrez) getTabuleiro().piece(position);
		return p == null || p.getCor() != getCor();
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Position p = new Position(0, 0);

		p.setValores(position.getLinha() - 1, position.getColuna() + 2);
		if (getTabuleiro().positionExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(position.getLinha() + 1, position.getColuna() + 2);
		if (getTabuleiro().positionExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(position.getLinha() - 1, position.getColuna() - 2);
		if (getTabuleiro().positionExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(position.getLinha() + 1, position.getColuna() - 2);
		if (getTabuleiro().positionExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(position.getLinha() - 2, position.getColuna() - 1);
		if (getTabuleiro().positionExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(position.getLinha() - 2, position.getColuna() + 1);
		if (getTabuleiro().positionExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(position.getLinha() + 2, position.getColuna() - 1);
		if (getTabuleiro().positionExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(position.getLinha() + 2, position.getColuna() + 1);
		if (getTabuleiro().positionExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		return mat;
	}
}