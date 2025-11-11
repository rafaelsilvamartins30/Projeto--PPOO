import java.util.List;
import java.util.Iterator;

/**
 * Um modelo simples de uma raposa.
 * Raposas envelhecem, se movem, caçam coelhos e morrem.
 * 
 * @author David J. Barnes e Michael Kolling
 * @version 2002-04-11 (traduzido)
 */
public class Raposa extends Animal
{
    // Características compartilhadas por todas as raposas (campos estáticos).
    
    // A idade na qual uma raposa pode começar a se reproduzir.
    private static final int IDADE_REPRODUTIVA = 10;
    // A idade máxima que uma raposa pode atingir.
    private static final int IDADE_MAXIMA = 150;
    // A probabilidade de uma raposa se reproduzir.
    private static final double PROBABILIDADE_REPRODUCAO = 0.09;
    // O número máximo de filhotes por ninhada.
    private static final int TAMANHO_MAXIMO_NINHADA = 3;
    // O valor alimentar de um único coelho (quantos passos a raposa sobrevive após comer).
    private static final int VALOR_ALIMENTAR_COELHO = 4;
    
    // Características individuais (campos de instância).
    // O nível de alimento da raposa, que aumenta quando ela come coelhos.
    private int nivelAlimento;

    /**
     * Cria uma raposa. Pode ser criada como recém-nascida (idade zero
     * e não faminta) ou com idade aleatória.
     * 
     * @param idadeAleatoria Se verdadeiro, a raposa terá idade e nível de fome aleatórios.
     */
    public Raposa(boolean idadeAleatoria)
    {
        super(idadeAleatoria);
        if(idadeAleatoria) {
            nivelAlimento = getAleatorio().nextInt(VALOR_ALIMENTAR_COELHO);
        }
        else {
            // mantém idade em 0
            nivelAlimento = VALOR_ALIMENTAR_COELHO;
        }
    }
    
    /**
     * Isto é o que a raposa faz na maior parte do tempo: caçar
     * coelhos. Nesse processo, pode se reproduzir, morrer de fome
     * ou morrer de velhice.
     */
    @Override
    public void agir(Campo campoAtual, Campo campoAtualizado, List<Ator> novasRaposas)
    {
        incrementarIdade();
        incrementarFome();
        if(estaVivo()) {
            // Novas raposas nascem em locais adjacentes.
            int nascimentos = reproduzir();
            for(int i = 0; i < nascimentos; i++) {
                Raposa novaRaposa = new Raposa(false);
                novasRaposas.add(novaRaposa);
                Localizacao loc = campoAtualizado.localizacaoAdjacenteAleatoria(getLocalizacao());
                novaRaposa.definirLocalizacao(loc);
                campoAtualizado.colocar(novaRaposa, loc);
            }
            // Move-se em direção à fonte de alimento, se encontrada.
            Localizacao novaLocalizacao = encontrarComida(campoAtual, getLocalizacao());
            if(novaLocalizacao == null) {  // nenhuma comida encontrada — move-se aleatoriamente
                novaLocalizacao = campoAtualizado.localizacaoAdjacenteLivre(getLocalizacao());
            }
            if(novaLocalizacao != null) {
                definirLocalizacao(novaLocalizacao);
                campoAtualizado.colocar(this, novaLocalizacao);
            }
            else {
                // não pode se mover nem ficar — superlotação — todas as posições ocupadas
                definirEstaVivo(false);
            }
        }
    }

    /**
     * Retorna a idade máxima da raposa.
     * @return A idade máxima.
     */
    @Override
    protected int IDADE_MAXIMA() {
        return IDADE_MAXIMA;
    }
    
    /**
     * Aumenta a fome da raposa. Isso pode resultar em sua morte.
     */
    private void incrementarFome()
    {
        nivelAlimento--;
        if(nivelAlimento <= 0) {
            definirEstaVivo(false);
        }
    }
    
    /**
     * Ordena que a raposa procure coelhos adjacentes à sua localização atual.
     * @param campo O campo onde procurar.
     * @param localizacao A posição atual no campo.
     * @return Onde o alimento foi encontrado, ou null se não foi.
     */
    private Localizacao encontrarComida(Campo campo, Localizacao localizacao)
    {
        Iterator locaisAdjacentes = campo.localizacoesAdjacentes(localizacao);
        while(locaisAdjacentes.hasNext()) {
            Localizacao onde = (Localizacao) locaisAdjacentes.next();
            Object animal = campo.getObjetoEm(onde);
            if(animal instanceof Coelho) {
                Coelho coelho = (Coelho) animal;
                if(coelho.estaVivo()) { 
                    coelho.foiComido();
                    nivelAlimento = VALOR_ALIMENTAR_COELHO;
                    return onde;
                }
            }
        }
        return null;
    }

    /**
     * A probabilidade de reprodução da raposa.
     * @return A probabilidade de reprodução.
     */
    @Override
    protected double PROBABILIDADE_REPRODUCAO() {
        return PROBABILIDADE_REPRODUCAO;
    }

    /**
     * O tamanho máximo da ninhada da raposa.
     * @return O tamanho máximo da ninhada.
     */
    @Override
    protected int TAMANHO_MAXIMO_NINHADA() {
        return TAMANHO_MAXIMO_NINHADA;
    }

    /**
     * A idade em que uma raposa começa a procriar.
     * @return A idade reprodutiva.
     */
    @Override
    protected int getIdadeReprodutiva() {
        return IDADE_REPRODUTIVA;
    }
}
