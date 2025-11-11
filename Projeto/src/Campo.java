import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
public class Campo
{
    private static final Random rand = new Random();
    private int profundidade, largura;
    private Object[][] campo;
    public Campo(int profundidade, int largura)
    {
        this.profundidade = profundidade;
        this.largura = largura;
        campo = new Object[profundidade][largura];
    }
    public void limpar()
    {
        for(int linha = 0; linha < profundidade; linha++) {
            for(int coluna = 0; coluna < largura; coluna++) {
                campo[linha][coluna] = null;
            }
        }
    }
    public void colocar(Object animal, int linha, int coluna)
    {
        colocar(animal, new Localizacao(linha, coluna));
    }
    public void colocar(Object animal, Localizacao localizacao)
    {
        campo[localizacao.getLinha()][localizacao.getColuna()] = animal;
    }
    public Object getObjetoEm(Localizacao localizacao)
    {
        return getObjetoEm(localizacao.getLinha(), localizacao.getColuna());
    }
    public Object getObjetoEm(int linha, int coluna)
    {
        return campo[linha][coluna];
    }
    public Localizacao localizacaoAdjacenteAleatoria(Localizacao localizacao)
    {
        int linha = localizacao.getLinha();
        int coluna = localizacao.getColuna();
        int proximaLinha = linha + rand.nextInt(3) - 1;
        int proximaColuna = coluna + rand.nextInt(3) - 1;
        if(proximaLinha < 0 || proximaLinha >= profundidade || proximaColuna < 0 || proximaColuna >= largura) {
            return localizacao;
        }
        else if(proximaLinha != linha || proximaColuna != coluna) {
            return new Localizacao(proximaLinha, proximaColuna);
        }
        else {
            return localizacao;
        }
    }
    public Localizacao localizacaoAdjacenteLivre(Localizacao localizacao)
    {
        Iterator adjacentes = localizacoesAdjacentes(localizacao);
        while(adjacentes.hasNext()) {
            Localizacao proxima = (Localizacao) adjacentes.next();
            if(campo[proxima.getLinha()][proxima.getColuna()] == null) {
                return proxima;
            }
        }
        if(campo[localizacao.getLinha()][localizacao.getColuna()] == null) {
            return localizacao;
        }
        else {
            return null;
        }
    }
    public Iterator localizacoesAdjacentes(Localizacao localizacao)
    {
        int linha = localizacao.getLinha();
        int coluna = localizacao.getColuna();
        LinkedList localizacoes = new LinkedList();
        for(int deslocamentoLinha = -1; deslocamentoLinha <= 1; deslocamentoLinha++) {
            int proximaLinha = linha + deslocamentoLinha;
            if(proximaLinha >= 0 && proximaLinha < profundidade) {
                for(int deslocamentoColuna = -1; deslocamentoColuna <= 1; deslocamentoColuna++) {
                    int proximaColuna = coluna + deslocamentoColuna;
                    if(proximaColuna >= 0 && proximaColuna < largura && (deslocamentoLinha != 0 || deslocamentoColuna != 0)) {
                        localizacoes.add(new Localizacao(proximaLinha, proximaColuna));
                    }
                }
            }
        }
        Collections.shuffle(localizacoes,rand);
        return localizacoes.iterator();
    }
    public int getProfundidade()
    {
        return profundidade;
    }
    public int getLargura()
    {
        return largura;
    }
}
