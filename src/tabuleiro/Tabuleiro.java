package tabuleiro;

public class Tabuleiro {
	private int linhas;
	private int colunas;
	private Pe�a[][] pe�as;
	
	public Tabuleiro(int linhas, int colunas) {
		if(linhas <1 || colunas <1) {
			throw new TabuleiroException("Erro ao criar o tabuleiro, necessario pelo menos 1 linha e uma coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pe�as = new Pe�a[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
	
	public Pe�a pe�a(int linha, int coluna) {
		if(!posi��oExiste(linha, coluna)) {
			throw new TabuleiroException("Posicao invalida");
		}
		return pe�as[linha][coluna];
	}
	
	public Pe�a pe�a(Posi��o posi��o) {
		return pe�a(posi��o.getLinha(),posi��o.getColuna());
	}
	
	public void lugarPe�a(Pe�a pe�a, Posi��o posi��o) {
		if(existePe�a(posi��o)) {
			throw new TabuleiroException("J� tem uma peca nessa posicao");
		}
		pe�as[posi��o.getLinha()][posi��o.getColuna()] = pe�a;
		pe�a.posi��o = posi��o;
	}
	
	public Pe�a removerPe�a(Posi��o posi��o) {
		if(!posi��oExiste(posi��o)) {
			throw new TabuleiroException("Posicao invalida");
		}
		if(pe�a(posi��o)==null) {
			return null;
		}
		Pe�a aux = pe�a(posi��o);
		aux.posi��o = null;
		pe�as[posi��o.getLinha()][posi��o.getColuna()] = null;
		return aux;
	}
	
	public boolean posi��oExiste(int linha, int coluna) {
		return linha>=0 && linha<linhas && coluna >=0 && coluna<colunas;
	}
	
	public boolean posi��oExiste(Posi��o posi��o) {
		return posi��oExiste(posi��o.getLinha(), posi��o.getColuna());
	}
	
	public boolean existePe�a(Posi��o posi��o) {
		if(!posi��oExiste(posi��o)) {
			throw new TabuleiroException("Posicao invalida");
		}
		return pe�a(posi��o) != null;
	}
}
