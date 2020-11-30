package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peça;
import tabuleiro.Posição;
import tabuleiro.Tabuleiro;
import xadrez.peças.Rei;
import xadrez.peças.Torre;

public class Partida {
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;

	private List<Peça> peçasNoTabuleiro = new ArrayList<>();
	private List<Peça> capturadas = new ArrayList<>();

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

	public PeçaXadrez[][] getPeças() {
		PeçaXadrez[][] matriz = new PeçaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				matriz[i][j] = (PeçaXadrez) tabuleiro.peça(i, j);
			}
		}
		return matriz;
	}

	public boolean[][] possiveisMovimentos(PosiçãoXadrez posiçãoOrigem) {
		Posição origem = posiçãoOrigem.toPosição();
		validarPosiçãoOrigem(origem);
		return tabuleiro.peça(origem).possiveisMovimentos();
	}

	public PeçaXadrez executarMovimentoXadrez(PosiçãoXadrez posiçãoOrigem, PosiçãoXadrez posiçãoDestino) {
		Posição origem = posiçãoOrigem.toPosição();
		Posição destino = posiçãoDestino.toPosição();
		validarPosiçãoDestino(origem, destino);
		Peça peçaCapturada = realizeMovimento(origem, destino);

		if (testarCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, peçaCapturada);
			throw new XadrezException("Nao pode se colocar/continuar em Check!");
		}
		
		check = testarCheck(corOponente(jogadorAtual));
		
		if(testarCheckMate(corOponente(jogadorAtual))){
			checkMate = true;
		} else {
			proximoTurno();
		}
		return (PeçaXadrez) peçaCapturada;
	}

	private Peça realizeMovimento(Posição origem, Posição destino) {
		Peça p = tabuleiro.removerPeça(origem);
		Peça peçaCapturada = tabuleiro.removerPeça(destino);
		tabuleiro.lugarPeça(p, destino);
		if (peçaCapturada != null) {
			peçasNoTabuleiro.remove(peçaCapturada);
			capturadas.add(peçaCapturada);
		}
		return peçaCapturada;
	}

	private void desfazerMovimento(Posição origem, Posição destino, Peça peçaCapturada) {
		Peça p = tabuleiro.removerPeça(destino);
		tabuleiro.lugarPeça(p, origem);
		if (peçaCapturada != null) {
			tabuleiro.lugarPeça(peçaCapturada, destino);
			capturadas.remove(peçaCapturada);
			peçasNoTabuleiro.add(peçaCapturada);
		}
	}

	private void validarPosiçãoOrigem(Posição posição) {
		if (!tabuleiro.existePeça(posição)) {
			throw new XadrezException("Nao ha peca nesta posicao");
		}
		if (jogadorAtual != ((PeçaXadrez) tabuleiro.peça(posição)).getCor()) {
			throw new XadrezException("Escolha uma peca sua");
		}
		if (!tabuleiro.peça(posição).haMovimentoPossivel()) {
			throw new XadrezException("Nao ha movimentos possiveis para peca escolhida");
		}
	}

	private void validarPosiçãoDestino(Posição origem, Posição destino) {
		if (!tabuleiro.peça(origem).possivelMovimento(destino)) {
			throw new XadrezException("A peca escolhida nao pode se mover para posicao de destino");
		}
	}

	private void lugarNovaPeça(char coluna, int linha, PeçaXadrez peça) {
		tabuleiro.lugarPeça(peça, new PosiçãoXadrez(coluna, linha).toPosição());
		peçasNoTabuleiro.add(peça);
	}

	private Cor corOponente(Cor cor) {
		return cor == Cor.Brancas ? Cor.Pretas : Cor.Brancas;
	}

	private PeçaXadrez Rei(Cor cor) {
		List<Peça> list = peçasNoTabuleiro.stream().filter(x -> ((PeçaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peça p : list) {
			if (p instanceof Rei) {
				return (PeçaXadrez) p;
			}
		}
		throw new IllegalAccessError("Nao ha Rei da cor" + cor);
	}

	private boolean testarCheck(Cor cor) {
		Posição reiPosição = Rei(cor).getPosiçãoXadrez().toPosição();
		List<Peça> peçasOponentes = peçasNoTabuleiro.stream().filter(x -> ((PeçaXadrez) x).getCor() == corOponente(cor))
				.collect(Collectors.toList());
		for (Peça p : peçasOponentes) {
			boolean[][] mat = p.possiveisMovimentos();
			if (mat[reiPosição.getLinha()][reiPosição.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testarCheckMate(Cor cor) {
		if (!testarCheck(cor)) {
			return false;
		}
		List<Peça> list = peçasNoTabuleiro.stream().filter(x -> ((PeçaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peça p : list) {
			boolean[][] mat = p.possiveisMovimentos();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if(mat[i][j]) {
						Posição origem = ((PeçaXadrez) p).getPosiçãoXadrez().toPosição();
						Posição destino = new Posição(i,j);
						Peça peçaCapturada = realizeMovimento(origem, destino);
						boolean testarCheck = testarCheck(cor);
						desfazerMovimento(origem, destino, peçaCapturada);
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
		lugarNovaPeça('a', 1, new Torre(tabuleiro, Cor.Brancas));
		lugarNovaPeça('h', 1, new Torre(tabuleiro, Cor.Brancas));
		lugarNovaPeça('e', 1, new Rei(tabuleiro, Cor.Brancas));
		lugarNovaPeça('a', 8, new Torre(tabuleiro, Cor.Pretas));
		lugarNovaPeça('h', 8, new Torre(tabuleiro, Cor.Pretas));
		lugarNovaPeça('e', 8, new Rei(tabuleiro, Cor.Pretas));
	}
}
