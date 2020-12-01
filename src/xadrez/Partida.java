package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Pe�a;
import tabuleiro.Posi��o;
import tabuleiro.Tabuleiro;
import xadrez.pe�as.Bispo;
import xadrez.pe�as.Cavalo;
import xadrez.pe�as.Dama;
import xadrez.pe�as.Pe�o;
import xadrez.pe�as.Rei;
import xadrez.pe�as.Torre;

public class Partida {
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	private Pe�aXadrez enPassant;
	private Pe�aXadrez promovida;

	private List<Pe�a> pe�asNoTabuleiro = new ArrayList<>();
	private List<Pe�a> capturadas = new ArrayList<>();

	public Partida() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.Brancas;
		setupInicial();
	}

	public int getTurno() {
		return turno;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public Pe�aXadrez getEnPassant() {
		return enPassant;
	}
	
	public Pe�aXadrez getpromovida() {
		return promovida;
	}

	private void proximoTurno() {
		turno++;
		jogadorAtual = jogadorAtual == Cor.Brancas ? Cor.Pretas : Cor.Brancas;
	}

	public Pe�aXadrez[][] getPe�as() {
		Pe�aXadrez[][] matriz = new Pe�aXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				matriz[i][j] = (Pe�aXadrez) tabuleiro.pe�a(i, j);
			}
		}
		return matriz;
	}

	public boolean[][] possiveisMovimentos(Posi��oXadrez posi��oOrigem) {
		Posi��o origem = posi��oOrigem.toPosi��o();
		validarPosi��oOrigem(origem);
		return tabuleiro.pe�a(origem).possiveisMovimentos();
	}

	public Pe�aXadrez executarMovimentoXadrez(Posi��oXadrez posi��oOrigem, Posi��oXadrez posi��oDestino) {
		Posi��o origem = posi��oOrigem.toPosi��o();
		Posi��o destino = posi��oDestino.toPosi��o();
		validarPosi��oDestino(origem, destino);
		Pe�a pe�aCapturada = realizeMovimento(origem, destino);

		if (testarCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, pe�aCapturada);
			throw new XadrezException("Nao pode se colocar/continuar em Check!");
		}

		Pe�aXadrez pe�aMovida = (Pe�aXadrez) tabuleiro.pe�a(destino);
		
		//Promover o pe�o
		promovida = null;
		if(pe�aMovida instanceof Pe�o) {
			if((pe�aMovida.getCor()==Cor.Brancas && destino.getLinha()==0) ||
					(pe�aMovida.getCor()==Cor.Pretas && destino.getLinha()==7)) {
				promovida = (Pe�aXadrez) tabuleiro.pe�a(destino);
				promovida = alterarPe�aPromovida("D");
			} 
		}
		
		check = testarCheck(corOponente(jogadorAtual));

		if (testarCheckMate(corOponente(jogadorAtual))) {
			checkMate = true;
		} else {
			proximoTurno();
		}

		// enPassant
		if (pe�aMovida instanceof Pe�o && (destino.getLinha() == origem.getLinha() + 2
				|| destino.getLinha() == origem.getLinha() - 2)) {
			enPassant = pe�aMovida;
		} else {
			enPassant = null;
		}

		return (Pe�aXadrez) pe�aCapturada;
	}
	
	public Pe�aXadrez alterarPe�aPromovida(String type) {
		if(promovida == null) {
			throw new IllegalStateException("Nao ha peca promovida");
		}
		if(!type.equals("T") && !type.equals("C") && !type.equals("B") && !type.equals("D")) {
			return promovida;
		}
		
		Posi��o pos = promovida.getPosi��oXadrez().toPosi��o();
		Pe�a p = tabuleiro.removerPe�a(pos);
		pe�asNoTabuleiro.remove(p);
		
		Pe�aXadrez novaPe�a = novaPe�a(type, promovida.getCor());
		tabuleiro.lugarPe�a(novaPe�a, pos);
		pe�asNoTabuleiro.add(novaPe�a);
		
		return novaPe�a;
	}
	
	private Pe�aXadrez novaPe�a(String type, Cor cor) {
		if(type.equals("D")) return new Dama(tabuleiro, cor);
		if(type.equals("T")) return new Torre(tabuleiro, cor);
		if(type.equals("C")) return new Cavalo(tabuleiro, cor);
		return new Bispo(tabuleiro, cor);
		
	}

	private Pe�a realizeMovimento(Posi��o origem, Posi��o destino) {
		Pe�aXadrez p = (Pe�aXadrez) tabuleiro.removerPe�a(origem);
		p.incrementarcontagemMovimentos();
		Pe�a pe�aCapturada = tabuleiro.removerPe�a(destino);
		tabuleiro.lugarPe�a(p, destino);
		if (pe�aCapturada != null) {
			pe�asNoTabuleiro.remove(pe�aCapturada);
			capturadas.add(pe�aCapturada);
		}

		// Roque pequeno
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posi��o origemTorreRei = new Posi��o(origem.getLinha(), origem.getColuna() + 3);
			Posi��o destinoTorreRei = new Posi��o(destino.getLinha(), origem.getColuna() + 1);
			Pe�aXadrez torre = (Pe�aXadrez) tabuleiro.removerPe�a(origemTorreRei);
			torre.incrementarcontagemMovimentos();
			tabuleiro.lugarPe�a(torre, destinoTorreRei);
		}

		// Roque grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posi��o origemTorreDama = new Posi��o(origem.getLinha(), origem.getColuna() - 4);
			Posi��o destinoTorreDama = new Posi��o(destino.getLinha(), origem.getColuna() - 1);
			Pe�aXadrez torre = (Pe�aXadrez) tabuleiro.removerPe�a(origemTorreDama);
			torre.incrementarcontagemMovimentos();
			tabuleiro.lugarPe�a(torre, destinoTorreDama);
		}
		
		//enPassant
		if(p instanceof Pe�o) {
			if(origem.getColuna() != destino.getColuna() && pe�aCapturada ==null) {
				Posi��o peaoPosi��o = enPassant.getPosi��oXadrez().toPosi��o();
				pe�aCapturada = tabuleiro.removerPe�a(peaoPosi��o);
				pe�asNoTabuleiro.remove(pe�aCapturada);
				capturadas.add(pe�aCapturada);
			}
		}

		return pe�aCapturada;
	}

	private void desfazerMovimento(Posi��o origem, Posi��o destino, Pe�a pe�aCapturada) {
		Pe�aXadrez p = (Pe�aXadrez) tabuleiro.removerPe�a(destino);
		p.decrementarcontagemMovimentos();
		tabuleiro.lugarPe�a(p, origem);
		if (pe�aCapturada != null) {
			tabuleiro.lugarPe�a(pe�aCapturada, destino);
			capturadas.remove(pe�aCapturada);
			pe�asNoTabuleiro.add(pe�aCapturada);
		}

		// Roque pequeno
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posi��o origemTorreRei = new Posi��o(origem.getLinha(), origem.getColuna() + 3);
			Posi��o destinoTorreRei = new Posi��o(destino.getLinha(), origem.getColuna() + 1);
			Pe�aXadrez torre = (Pe�aXadrez) tabuleiro.removerPe�a(destinoTorreRei);
			torre.decrementarcontagemMovimentos();
			tabuleiro.lugarPe�a(torre, origemTorreRei);
		}

		// Roque grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posi��o origemTorreDama = new Posi��o(origem.getLinha(), origem.getColuna() - 4);
			Posi��o destinoTorreDama = new Posi��o(destino.getLinha(), origem.getColuna() - 1);
			Pe�aXadrez torre = (Pe�aXadrez) tabuleiro.removerPe�a(destinoTorreDama);
			torre.decrementarcontagemMovimentos();
			tabuleiro.lugarPe�a(torre, origemTorreDama);
		}
		
		//enPassant
		if(p instanceof Pe�o) {
			if(origem.getColuna() != destino.getColuna() && pe�aCapturada == enPassant) {
				Posi��o peaoPosi��o = ((Pe�aXadrez) pe�aCapturada).getPosi��oXadrez().toPosi��o();
				if(p.getCor() == Cor.Brancas) {
					peaoPosi��o = new Posi��o(3, peaoPosi��o.getColuna());
				} else {
					peaoPosi��o = new Posi��o(4, peaoPosi��o.getColuna());
				}
				pe�aCapturada = tabuleiro.removerPe�a(destino);
				tabuleiro.lugarPe�a(pe�aCapturada, peaoPosi��o);
			}
		}

	}

	private void validarPosi��oOrigem(Posi��o posi��o) {
		if (!tabuleiro.existePe�a(posi��o)) {
			throw new XadrezException("Nao ha peca nesta posicao");
		}
		if (jogadorAtual != ((Pe�aXadrez) tabuleiro.pe�a(posi��o)).getCor()) {
			throw new XadrezException("Escolha uma peca sua");
		}
		if (!tabuleiro.pe�a(posi��o).haMovimentoPossivel()) {
			throw new XadrezException("Nao ha movimentos possiveis para peca escolhida");
		}
	}

	private void validarPosi��oDestino(Posi��o origem, Posi��o destino) {
		if (!tabuleiro.pe�a(origem).possivelMovimento(destino)) {
			throw new XadrezException("A peca escolhida nao pode se mover para posicao de destino");
		}
	}

	private void lugarNovaPe�a(char coluna, int linha, Pe�aXadrez pe�a) {
		tabuleiro.lugarPe�a(pe�a, new Posi��oXadrez(coluna, linha).toPosi��o());
		pe�asNoTabuleiro.add(pe�a);
	}

	private Cor corOponente(Cor cor) {
		return cor == Cor.Brancas ? Cor.Pretas : Cor.Brancas;
	}

	private Pe�aXadrez Rei(Cor cor) {
		List<Pe�a> list = pe�asNoTabuleiro.stream().filter(x -> ((Pe�aXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Pe�a p : list) {
			if (p instanceof Rei) {
				return (Pe�aXadrez) p;
			}
		}
		throw new IllegalAccessError("Nao ha Rei da cor" + cor);
	}

	private boolean testarCheck(Cor cor) {
		Posi��o reiPosi��o = Rei(cor).getPosi��oXadrez().toPosi��o();
		List<Pe�a> pe�asOponentes = pe�asNoTabuleiro.stream().filter(x -> ((Pe�aXadrez) x).getCor() == corOponente(cor))
				.collect(Collectors.toList());
		for (Pe�a p : pe�asOponentes) {
			boolean[][] mat = p.possiveisMovimentos();
			if (mat[reiPosi��o.getLinha()][reiPosi��o.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testarCheckMate(Cor cor) {
		if (!testarCheck(cor)) {
			return false;
		}
		List<Pe�a> list = pe�asNoTabuleiro.stream().filter(x -> ((Pe�aXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Pe�a p : list) {
			boolean[][] mat = p.possiveisMovimentos();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posi��o origem = ((Pe�aXadrez) p).getPosi��oXadrez().toPosi��o();
						Posi��o destino = new Posi��o(i, j);
						Pe�a pe�aCapturada = realizeMovimento(origem, destino);
						boolean testarCheck = testarCheck(cor);
						desfazerMovimento(origem, destino, pe�aCapturada);
						if (!testarCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void setupInicial() {

		lugarNovaPe�a('a', 2, new Pe�o(tabuleiro, Cor.Brancas, this));
		lugarNovaPe�a('b', 2, new Pe�o(tabuleiro, Cor.Brancas, this));
		lugarNovaPe�a('c', 2, new Pe�o(tabuleiro, Cor.Brancas, this));
		lugarNovaPe�a('d', 2, new Pe�o(tabuleiro, Cor.Brancas, this));
		lugarNovaPe�a('e', 2, new Pe�o(tabuleiro, Cor.Brancas, this));
		lugarNovaPe�a('f', 2, new Pe�o(tabuleiro, Cor.Brancas, this));
		lugarNovaPe�a('g', 2, new Pe�o(tabuleiro, Cor.Brancas, this));
		lugarNovaPe�a('h', 2, new Pe�o(tabuleiro, Cor.Brancas, this));
		lugarNovaPe�a('a', 1, new Torre(tabuleiro, Cor.Brancas));
		lugarNovaPe�a('b', 1, new Cavalo(tabuleiro, Cor.Brancas));
		lugarNovaPe�a('c', 1, new Bispo(tabuleiro, Cor.Brancas));
		lugarNovaPe�a('d', 1, new Dama(tabuleiro, Cor.Brancas));
		lugarNovaPe�a('e', 1, new Rei(tabuleiro, Cor.Brancas, this));
		lugarNovaPe�a('f', 1, new Bispo(tabuleiro, Cor.Brancas));
		lugarNovaPe�a('g', 1, new Cavalo(tabuleiro, Cor.Brancas));
		lugarNovaPe�a('h', 1, new Torre(tabuleiro, Cor.Brancas));

		lugarNovaPe�a('a', 7, new Pe�o(tabuleiro, Cor.Pretas, this));
		lugarNovaPe�a('b', 7, new Pe�o(tabuleiro, Cor.Pretas, this));
		lugarNovaPe�a('c', 7, new Pe�o(tabuleiro, Cor.Pretas, this));
		lugarNovaPe�a('d', 7, new Pe�o(tabuleiro, Cor.Pretas, this));
		lugarNovaPe�a('e', 7, new Pe�o(tabuleiro, Cor.Pretas, this));
		lugarNovaPe�a('f', 7, new Pe�o(tabuleiro, Cor.Pretas, this));
		lugarNovaPe�a('g', 7, new Pe�o(tabuleiro, Cor.Pretas, this));
		lugarNovaPe�a('h', 7, new Pe�o(tabuleiro, Cor.Pretas, this));
		lugarNovaPe�a('a', 8, new Torre(tabuleiro, Cor.Pretas));
		lugarNovaPe�a('b', 8, new Cavalo(tabuleiro, Cor.Pretas));
		lugarNovaPe�a('c', 8, new Bispo(tabuleiro, Cor.Pretas));
		lugarNovaPe�a('d', 8, new Dama(tabuleiro, Cor.Pretas));
		lugarNovaPe�a('e', 8, new Rei(tabuleiro, Cor.Pretas, this));
		lugarNovaPe�a('f', 8, new Bispo(tabuleiro, Cor.Pretas));
		lugarNovaPe�a('g', 8, new Cavalo(tabuleiro, Cor.Pretas));
		lugarNovaPe�a('h', 8, new Torre(tabuleiro, Cor.Pretas));
	}
}
