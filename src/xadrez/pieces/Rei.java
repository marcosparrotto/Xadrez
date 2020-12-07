package xadrez.pieces;

import tabuleiro.Position;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.Partida;
import xadrez.PieceXadrez;

public class Rei extends PieceXadrez {

	private Partida partida;

	public Rei(Tabuleiro tabuleiro, Cor cor, Partida partida) {
		super(tabuleiro, cor);
		this.partida = partida;
	}

	@Override
	public String toString() {
		return "R";
	}

	private boolean podeMover(Position position) {
		PieceXadrez p = (PieceXadrez) getTabuleiro().piece(position);
		return p == null || p.getCor() != getCor();
	}

	private boolean testarRookpossivel(Position position) {
		PieceXadrez p = (PieceXadrez) getTabuleiro().piece(position);
		return p != null && p instanceof Torre && p.getCor() == getCor() && getcontagemMovimentos() == 0;
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Position p = new Position(0, 0);

		// Acima
		p.setValores(position.getLinha() - 1, position.getColuna());
		if (getTabuleiro().positionExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Baixo
		p.setValores(position.getLinha() + 1, position.getColuna());
		if (getTabuleiro().positionExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Esquerda
		p.setValores(position.getLinha(), position.getColuna() - 1);
		if (getTabuleiro().positionExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Direita
		p.setValores(position.getLinha(), position.getColuna() + 1);
		if (getTabuleiro().positionExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Acima + Esquerda
		p.setValores(position.getLinha() - 1, position.getColuna() - 1);
		if (getTabuleiro().positionExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Acima + Direita
		p.setValores(position.getLinha() - 1, position.getColuna() + 1);
		if (getTabuleiro().positionExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Baixo + Esquerda
		p.setValores(position.getLinha() + 1, position.getColuna() - 1);
		if (getTabuleiro().positionExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Baixo + Direita
		p.setValores(position.getLinha() + 1, position.getColuna() + 1);
		if (getTabuleiro().positionExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Roque
		if (getcontagemMovimentos() == 0 && !partida.getCheck()) {
			// Roque pequeno
			Position posTorreRei = new Position(position.getLinha(), position.getColuna() + 3);
			if (testarRookpossivel(posTorreRei)) {
				Position p1 = new Position(position.getLinha(), position.getColuna() + 1);
				Position p2 = new Position(position.getLinha(), position.getColuna() + 2);
				if (getTabuleiro().piece(p1) == null && getTabuleiro().piece(p2) == null) {
					mat[position.getLinha()][position.getColuna() + 2] = true;
				}
			}

			// Roque grande
			Position posTorreDama = new Position(position.getLinha(), position.getColuna() - 4);
			if (testarRookpossivel(posTorreDama)) {
				Position p1 = new Position(position.getLinha(), position.getColuna() - 1);
				Position p2 = new Position(position.getLinha(), position.getColuna() - 2);
				Position p3 = new Position(position.getLinha(), position.getColuna() - 3);
				if (getTabuleiro().piece(p1) == null && getTabuleiro().piece(p2) == null && getTabuleiro().piece(p3) == null) {
					mat[position.getLinha()][position.getColuna() - 2] = true;
				}
			}
		}

		return mat;
	}
}