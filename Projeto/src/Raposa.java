import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * Um modelo simples de uma raposa.
 * Raposas envelhecem, se movem, comem coelhos e morrem.
 *
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-11
 */
public class Raposa
{
    // Características compartilhadas por todas as raposas (campos estáticos).

    // A idade em que uma raposa pode começar a se reproduzir.
    private static final int IDADE_REPRODUCAO = 10;
    // A idade até a qual uma raposa pode viver.
    private static final int IDADE_MAXIMA = 150;
    // A probabilidade de uma raposa se reproduzir.
    private static final double PROBABILIDADE_REPRODUCAO = 0.09;
    // O número máximo de nascimentos.
    private static final int TAMANHO_MAXIMO_NINHADA = 3;
    // O valor alimentar de um único coelho. Na prática, este é o
    // número de passos que uma raposa pode dar antes de ter que comer novamente.
    private static final int VALOR_ALIMENTAR_COELHO = 4;
    // Um gerador de números aleatórios compartilhado para controlar reprodução.
    private static final Random rand = new Random();
    
    // Características individuais (campos de instância).

    // A idade da raposa.
    private int idade;
    // Se a raposa está viva ou não.
    private boolean viva;
    // A posição da raposa
    private Localizacao localizacao;
    // O nível de comida da raposa, que é aumentado ao comer coelhos.
    private int nivelComida;

    /**
     * Cria uma raposa. Uma raposa pode ser criada como recém-nascida (idade zero
     * e sem fome) ou com idade aleatória.
     *
     * @param idadeAleatoria Se verdadeiro, a raposa terá idade e nível de fome aleatórios.
     */
    public Raposa(boolean idadeAleatoria)
    {
        idade = 0;
        viva = true;
        if(idadeAleatoria) {
            idade = rand.nextInt(IDADE_MAXIMA);
            nivelComida = rand.nextInt(VALOR_ALIMENTAR_COELHO);
        }
        else {
            // deixa idade em 0
            nivelComida = VALOR_ALIMENTAR_COELHO;
        }
    }
    
    /**
     * Isto é o que a raposa faz na maior parte do tempo: ela caça por
     * coelhos. No processo, ela pode se reproduzir, morrer de fome,
     * ou morrer de velhice.
     */
    public void cacar(Campo campoAtual, Campo campoAtualizado, List novasRaposas)
    {
        incrementarIdade();
        incrementarFome();
        if(estaViva()) {
            // Novas raposas nascem em localizações adjacentes.
            int nascimentos = reproduzir();
            for(int b = 0; b < nascimentos; b++) {
                Raposa novaRaposa = new Raposa(false);
                novasRaposas.add(novaRaposa);
                Localizacao loc = campoAtualizado.localizacaoAdjacenteAleatoria(localizacao);
                novaRaposa.definirLocalizacao(loc);
                campoAtualizado.colocar(novaRaposa, loc);
            }
            // Move-se em direção à fonte de comida se encontrada.
            Localizacao novaLocalizacao = encontrarComida(campoAtual, localizacao);
            if(novaLocalizacao == null) {  // nenhuma comida encontrada - move-se aleatoriamente
                novaLocalizacao = campoAtualizado.localizacaoAdjacenteLivre(localizacao);
            }
            if(novaLocalizacao != null) {
                definirLocalizacao(novaLocalizacao);
                campoAtualizado.colocar(this, novaLocalizacao);
            }
            else {
                // não pode se mover nem ficar - superlotação - todas as localizações ocupadas
                viva = false;
            }
        }
    }
    
    /**
     * Aumenta a idade. Isso pode resultar na morte da raposa.
     */
    private void incrementarIdade()
    {
        idade++;
        if(idade > IDADE_MAXIMA) {
            viva = false;
        }
    }
    
    /**
     * Torna esta raposa mais faminta. Isso pode resultar na morte da raposa.
     */
    private void incrementarFome()
    {
        nivelComida--;
        if(nivelComida <= 0) {
            viva = false;
        }
    }
    
    /**
     * Diz à raposa para procurar coelhos adjacentes à sua localização atual.
     * @param campo O campo no qual ela deve procurar.
     * @param localizacao Onde no campo ela está localizada.
     * @return Onde a comida foi encontrada, ou null se não foi.
     */
    private Localizacao encontrarComida(Campo campo, Localizacao localizacao)
    {
        Iterator localizacoesAdjacentes =
                          campo.localizacoesAdjacentes(localizacao);
        while(localizacoesAdjacentes.hasNext()) {
            Localizacao onde = (Localizacao) localizacoesAdjacentes.next();
            Object animal = campo.getObjetoEm(onde);
            if(animal instanceof Coelho) {
                Coelho coelho = (Coelho) animal;
                if(coelho.estaVivo()) {
                    coelho.definirComido();
                    nivelComida = VALOR_ALIMENTAR_COELHO;
                    return onde;
                }
            }
        }
        return null;
    }
        
    /**
     * Gera um número representando o número de nascimentos,
     * se ela puder se reproduzir.
     * @return O número de nascimentos (pode ser zero).
     */
    private int reproduzir()
    {
        int nascimentos = 0;
        if(podeReproduzir() && rand.nextDouble() <= PROBABILIDADE_REPRODUCAO) {
            nascimentos = rand.nextInt(TAMANHO_MAXIMO_NINHADA) + 1;
        }
        return nascimentos;
    }

    /**
     * Uma raposa pode se reproduzir se atingiu a idade de reprodução.
     */
    private boolean podeReproduzir()
    {
        return idade >= IDADE_REPRODUCAO;
    }
    
    /**
     * Verifica se a raposa está viva ou não.
     * @return Verdadeiro se a raposa ainda está viva.
     */
    public boolean estaViva()
    {
        return viva;
    }

    /**
     * Define a localização do animal.
     * @param linha A coordenada vertical da localização.
     * @param coluna A coordenada horizontal da localização.
     */
    public void definirLocalizacao(int linha, int coluna)
    {
        this.localizacao = new Localizacao(linha, coluna);
    }

    /**
     * Define a localização da raposa.
     * @param localizacao A localização da raposa.
     */
    public void definirLocalizacao(Localizacao localizacao)
    {
        this.localizacao = localizacao;
    }
}
