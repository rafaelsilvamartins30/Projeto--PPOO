import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Representa uma grade retangular de posições do campo.
 * Cada posição pode armazenar um único animal.
 *
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-09
 */
public class Campo
{
    private static final Random rand = new Random();
    
    // A profundidade e largura do campo.
    private int profundidade, largura;
    // Armazenamento para os animais.
    private Object[][] campo;

    /**
     * Representa um campo com as dimensões dadas.
     * @param profundidade A profundidade do campo.
     * @param largura A largura do campo.
     */
    public Campo(int profundidade, int largura)
    {
        this.profundidade = profundidade;
        this.largura = largura;
        campo = new Object[profundidade][largura];
    }
    
    /**
     * Limpa o campo.
     */
    public void limpar()
    {
        for(int linha = 0; linha < profundidade; linha++) {
            for(int coluna = 0; coluna < largura; coluna++) {
                campo[linha][coluna] = null;
            }
        }
    }
    
    /**
     * Coloca um animal na localização dada.
     * Se já existe um animal na localização, ele será
     * perdido.
     * @param animal O animal a ser colocado.
     * @param linha Coordenada da linha da localização.
     * @param coluna Coordenada da coluna da localização.
     */
    public void colocar(Object animal, int linha, int coluna)
    {
        colocar(animal, new Localizacao(linha, coluna));
    }
    
    /**
     * Coloca um animal na localização dada.
     * Se já existe um animal na localização, ele será
     * perdido.
     * @param animal O animal a ser colocado.
     * @param localizacao Onde colocar o animal.
     */
    public void colocar(Object animal, Localizacao localizacao)
    {
        campo[localizacao.getLinha()][localizacao.getColuna()] = animal;
    }
    
    /**
     * Retorna o animal na localização dada, se houver.
     * @param localizacao Onde no campo.
     * @return O animal na localização dada, ou null se não houver nenhum.
     */
    public Object getObjetoEm(Localizacao localizacao)
    {
        return getObjetoEm(localizacao.getLinha(), localizacao.getColuna());
    }
    
    /**
     * Retorna o animal na localização dada, se houver.
     * @param linha A linha desejada.
     * @param coluna A coluna desejada.
     * @return O animal na localização dada, ou null se não houver nenhum.
     */
    public Object getObjetoEm(int linha, int coluna)
    {
        return campo[linha][coluna];
    }
    
    /**
     * Gera uma localização aleatória que é adjacente à
     * localização dada, ou é a mesma localização.
     * A localização retornada estará dentro dos limites válidos
     * do campo.
     * @param localizacao A localização da qual gerar uma adjacência.
     * @return Uma localização válida dentro da área da grade. Esta
     *         pode ser o mesmo objeto que o parâmetro localização.
     */
    public Localizacao localizacaoAdjacenteAleatoria(Localizacao localizacao)
    {
        int linha = localizacao.getLinha();
        int coluna = localizacao.getColuna();
        // Gera um deslocamento de -1, 0, ou +1 para linha e coluna atual.
        int proximaLinha = linha + rand.nextInt(3) - 1;
        int proximaColuna = coluna + rand.nextInt(3) - 1;
        // Verifica se a nova localização está fora dos limites.
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
    
    /**
     * Tenta encontrar uma localização livre que é adjacente à
     * localização dada. Se não houver nenhuma, então retorna a localização
     * atual se ela estiver livre. Caso contrário, retorna null.
     * A localização retornada estará dentro dos limites válidos
     * do campo.
     * @param localizacao A localização da qual gerar uma adjacência.
     * @return Uma localização válida dentro da área da grade. Esta pode ser o
     *         mesmo objeto que o parâmetro localização, ou null se todas as
     *         localizações ao redor estiverem cheias.
     */
    public Localizacao localizacaoAdjacenteLivre(Localizacao localizacao)
    {
        Iterator adjacentes = localizacoesAdjacentes(localizacao);
        while(adjacentes.hasNext()) {
            Localizacao proxima = (Localizacao) adjacentes.next();
            if(campo[proxima.getLinha()][proxima.getColuna()] == null) {
                return proxima;
            }
        }
        // verifica se a localização atual está livre
        if(campo[localizacao.getLinha()][localizacao.getColuna()] == null) {
            return localizacao;
        }
        else {
            return null;
        }
    }

    /**
     * Gera um iterador sobre uma lista embaralhada de localizações adjacentes
     * à dada. A lista não incluirá a localização em si.
     * Todas as localizações estarão dentro da grade.
     * @param localizacao A localização da qual gerar adjacências.
     * @return Um iterador sobre localizações adjacentes àquela dada.
     */
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
                    // Exclui localizações inválidas e a localização original.
                    if(proximaColuna >= 0 && proximaColuna < largura && (deslocamentoLinha != 0 || deslocamentoColuna != 0)) {
                        localizacoes.add(new Localizacao(proximaLinha, proximaColuna));
                    }
                }
            }
        }
        Collections.shuffle(localizacoes,rand);
        return localizacoes.iterator();
    }

    /**
     * @return A profundidade do campo.
     */
    public int getProfundidade()
    {
        return profundidade;
    }
    
    /**
     * @return A largura do campo.
     */
    public int getLargura()
    {
        return largura;
    }
}
