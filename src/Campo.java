import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Representa uma grade retangular de posições no campo.
 * Cada posição é capaz de armazenar um único animal.
 * 
 * @author David J. Barnes e Michael Kolling
 * @version 2002-04-09 (traduzido)
 */
public class Campo
{
    private static final Random aleatorio = new Random();
    
    // A profundidade e a largura do campo.
    private int profundidade, largura;
    // Armazenamento para os animais.
    private Object[][] campo;

    /**
     * Representa um campo com as dimensões fornecidas.
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
     * Esvazia o campo.
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
     * Coloca um animal na localização especificada.
     * Se já houver um animal naquela localização, ele será perdido.
     * @param animal O animal a ser colocado.
     * @param linha A coordenada da linha.
     * @param coluna A coordenada da coluna.
     */
    public void colocar(Object animal, int linha, int coluna)
    {
        colocar(animal, new Localizacao(linha, coluna));
    }
    
    /**
     * Coloca um animal na localização especificada.
     * Se já houver um animal naquela localização, ele será perdido.
     * @param animal O animal a ser colocado.
     * @param localizacao Onde colocar o animal.
     */
    public void colocar(Object animal, Localizacao localizacao)
    {
        campo[localizacao.getLinha()][localizacao.getColuna()] = animal;
    }
    
    /**
     * Retorna o animal na localização especificada, se houver.
     * @param localizacao Onde no campo.
     * @return O animal na localização, ou null se não houver nenhum.
     */
    public Object getObjetoEm(Localizacao localizacao)
    {
        return getObjetoEm(localizacao.getLinha(), localizacao.getColuna());
    }
    
    /**
     * Retorna o animal na posição especificada, se houver.
     * @param linha A linha desejada.
     * @param coluna A coluna desejada.
     * @return O animal na posição, ou null se não houver nenhum.
     */
    public Object getObjetoEm(int linha, int coluna)
    {
        return campo[linha][coluna];
    }
    
    /**
     * Gera uma localização aleatória que é adjacente à
     * localização fornecida, ou a própria localização.
     * A localização retornada estará dentro dos limites válidos
     * do campo.
     * @param localizacao A localização base.
     * @return Uma localização válida dentro da área da grade.
     */
    public Localizacao localizacaoAdjacenteAleatoria(Localizacao localizacao)
    {
        int linha = localizacao.getLinha();
        int coluna = localizacao.getColuna();
        // Gera um deslocamento de -1, 0 ou +1 tanto para linha quanto para coluna.
        int proxLinha = linha + aleatorio.nextInt(3) - 1;
        int proxColuna = coluna + aleatorio.nextInt(3) - 1;
        // Verifica se a nova localização está fora dos limites.
        if(proxLinha < 0 || proxLinha >= profundidade || proxColuna < 0 || proxColuna >= largura) {
            return localizacao;
        }
        else if(proxLinha != linha || proxColuna != coluna) {
            return new Localizacao(proxLinha, proxColuna);
        }
        else {
            return localizacao;
        }
    }
    
    /**
     * Tenta encontrar uma localização livre adjacente à
     * localização fornecida. Se não houver, retorna a própria
     * localização se ela estiver livre. Caso contrário, retorna null.
     * A localização retornada estará dentro dos limites válidos do campo.
     * @param localizacao A localização base.
     * @return Uma localização livre válida ou null se todas estiverem ocupadas.
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
     * à fornecida. A lista não inclui a própria localização.
     * Todas as localizações estarão dentro da grade.
     * @param localizacao A localização base.
     * @return Um iterador sobre as localizações adjacentes.
     */
    public Iterator localizacoesAdjacentes(Localizacao localizacao)
    {
        int linha = localizacao.getLinha();
        int coluna = localizacao.getColuna();
        LinkedList locais = new LinkedList();
        for(int deslocLinha = -1; deslocLinha <= 1; deslocLinha++) {
            int proxLinha = linha + deslocLinha;
            if(proxLinha >= 0 && proxLinha < profundidade) {
                for(int deslocCol = -1; deslocCol <= 1; deslocCol++) {
                    int proxColuna = coluna + deslocCol;
                    // Exclui localizações inválidas e a própria.
                    if(proxColuna >= 0 && proxColuna < largura && (deslocLinha != 0 || deslocCol != 0)) {
                        locais.add(new Localizacao(proxLinha, proxColuna));
                    }
                }
            }
        }
        Collections.shuffle(locais, aleatorio);
        return locais.iterator();
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