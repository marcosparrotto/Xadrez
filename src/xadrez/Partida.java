package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Pe�a;
import tabuleiro.Posi��o;
import tabuleiro.Tabuleiro;
import xadrez.pe�as.Rei;
import xadrez.pe�as.Torre;

public class Partida {
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;

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
		
		check = testarCheck(corOponente(jogadorAtual));
		
		if(testarCheckMate(corOponente(jogadorAtual))){
			checkMate = true;
		} else {
			proximoTurno();
		}
		return (Pe�aXadrez) pe�aCapturada;
	}

	private Pe�a realizeMovimento(Posi��o origem, Posi��o destino) {
		Pe�a p = tabuleiro.removerPe�a(origem);
		Pe�a pe�aCapturada = tabuleiro.removerPe�a(destino);
		tabuleiro.lugarPe�a(p, destino);
		if (pe�aCapturada != null) {
			pe�asNoTabuleiro.remove(pe�aCapturada);
			capturadas.add(pe�aCapturada);
		}
		return pe�aCapturada;
	}

	private void desfazerMovimento(Posi��o origem, Posi��o destino, Pe�a pe�aCapturada) {
		Pe�a p = tabuleiro.removerPe�a(destino);
		tabuleiro.lugarPe�a(p, origem);
		if (pe�aCapturada != null) {
			tabuleiro.lugarPe�a(pe�aCapturada, destino);
			capturadas.remove(pe�aCapturada);
			pe�asNoTabuleiro.add(pe�aCapturada);
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
					if(mat[i][j]) {
						Posi��o origem = ((Pe�aXadrez) p).getPosi��oXadrez().toPosi��o();
						Posi��o destino = new Posi��o(i,j);
						Pe�a pe�aCapturada = realizeMovimento(origem, destino);
						boolean testarCheck = testarCheck(cor);
						desfazerMovimento(origem, destino, pe�aCapturada);
						if(!testarCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void setupInicial() {
		lugarNovaPe�a('a', 1, new Torre(tabuleiro, Cor.Brancas));
		lugarNovaPe�a('h', 1, new Torre(tabuleiro, Cor.Brancas));
		lugarNovaPe�a('e', 1, new Rei(tabuleiro, Cor.Brancas));
		lugarNovaPe�a('a', 8, new Torre(tabuleiro, Cor.Pretas));
		lugarNovaPe�a('h', 8, new Torre(tabuleiro, Cor.Pretas));
		lugarNovaPe�a('e', 8, new Rei(tabuleiro, Cor.Pretas));
	}
}
