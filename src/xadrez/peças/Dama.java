package xadrez.pe�as;

import tabuleiro.Posi��o;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.Pe�aXadrez;

public class Dama extends Pe�aXadrez{

	public Dama(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "D";
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posi��o p = new Posi��o(0,0);
		
		//Acima
		p.setValores(posi��o.getLinha() -1, posi��o.getColuna());
		while(getTabuleiro().posi��oExiste(p) && !getTabuleiro().existePe�a(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha()-1);
		}
		if(getTabuleiro().posi��oExiste(p) && haPe�aOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//Esquerda
		p.setValores(posi��o.getLinha(), posi��o.getColuna()-1);
		while(getTabuleiro().posi��oExiste(p) && !getTabuleiro().existePe�a(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna()-1);
		}
		if(getTabuleiro().posi��oExiste(p) && haPe�aOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//Direita
		p.setValores(posi��o.getLinha(), posi��o.getColuna()+1);
		while(getTabuleiro().posi��oExiste(p) && !getTabuleiro().existePe�a(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna()+1);
		}
		if(getTabuleiro().posi��oExiste(p) && haPe�aOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		//Baixo
		p.setValores(posi��o.getLinha() +1, posi��o.getColuna());
		while(getTabuleiro().posi��oExiste(p) && !getTabuleiro().existePe�a(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha()+1);
		}
		if(getTabuleiro().posi��oExiste(p) && haPe�aOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// Cima + Direita
				p.setValores(posi��o.getLinha() - 1, posi��o.getColuna() - 1);
				while (getTabuleiro().posi��oExiste(p) && !getTabuleiro().existePe�a(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setValores(p.getLinha() - 1, p.getColuna() - 1);
				}
				if (getTabuleiro().posi��oExiste(p) && haPe�aOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}

				// Cima + Esquerda
				p.setValores(posi��o.getLinha() - 1, posi��o.getColuna() + 1);
				while (getTabuleiro().posi��oExiste(p) && !getTabuleiro().existePe�a(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setValores(p.getLinha() - 1, p.getColuna() + 1);
				}
				if (getTabuleiro().posi��oExiste(p) && haPe�aOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				
				// Baixo + Direita
				p.setValores(posi��o.getLinha() + 1, posi��o.getColuna() - 1);
				while (getTabuleiro().posi��oExiste(p) && !getTabuleiro().existePe�a(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setValores(p.getLinha() + 1, p.getColuna() - 1);
				}
				if (getTabuleiro().posi��oExiste(p) && haPe�aOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}

				// Baixo + Esquerda
				p.setValores(posi��o.getLinha() + 1, posi��o.getColuna() + 1);
				while (getTabuleiro().posi��oExiste(p) && !getTabuleiro().existePe�a(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setValores(p.getLinha() + 1, p.getColuna() + 1);
				}
				if (getTabuleiro().posi��oExiste(p) && haPe�aOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
		
		
			
		
		return mat;
	}
}
