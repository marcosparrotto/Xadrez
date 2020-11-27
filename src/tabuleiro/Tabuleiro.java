package tabuleiro;

public class Tabuleiro {
	private int linhas;
	private int colunas;
	private Pe�a[][] pe�as;
	
	public Tabuleiro(int linhas, int colunas) {
		this.linhas = linhas;
		this.colunas = colunas;
		pe�as = new Pe�a[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public void setLinhas(int linhas) {
		this.linhas = linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public void setColunas(int colunas) {
		this.colunas = colunas;
	}
	
	public Pe�a pe�a(int linha, int coluna) {
		return pe�as[linha][coluna];
	}
	
	public Pe�a pe�a(Posi��o posi��o) {
		return pe�as[posi��o.getLinha()][posi��o.getColuna()];
	}
	
	public void lugarPe�a(Pe�a pe�a, Posi��o posi��o) {
		pe�as[posi��o.getLinha()][posi��o.getColuna()] = pe�a;
		pe�a.posi��o = posi��o;
	}
}
