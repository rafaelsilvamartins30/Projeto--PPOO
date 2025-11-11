import java.util.List;

/**
 * Um modelo simples de um coelho.
 * Coelhos envelhecem, se movem, se reproduzem e morrem.
 * 
 * @author David J. Barnes e Michael Kolling
 * @version 2002-04-11 (traduzido)
 */
public class Coelho extends Animal {
    // Características compartilhadas por todos os coelhos (campos estáticos).

    // A idade na qual um coelho pode começar a se reproduzir.
    private static final int IDADE_REPRODUTIVA = 5;
    // A idade máxima que um coelho pode alcançar.
    private static final int IDADE_MAXIMA = 50;
    // A probabilidade de um coelho se reproduzir.
    private static final double PROBABILIDADE_REPRODUCAO = 0.15;
    // O número máximo de filhotes por ninhada.
    private static final int TAMANHO_MAXIMO_NINHADA = 5;

    /**
     * Cria um novo coelho. Um coelho pode ser criado com idade
     * zero (recém-nascido) ou com uma idade aleatória.
     * 
     * @param idadeAleatoria Se verdadeiro, o coelho terá uma idade aleatória.
     */
    public Coelho(boolean idadeAleatoria)
    {
        super(idadeAleatoria);
    }
    
    /**
     * Isto é o que o coelho faz na maior parte do tempo — ele corre
     * por aí. Às vezes ele se reproduz ou morre de velhice.
     */
    @Override
    public void agir(Campo campoAtual, Campo campoAtualizado, List<Ator> novosCoelhos)
    {
        incrementarIdade();
        if(estaVivo()) {
            int nascimentos = reproduzir();
            for(int b = 0; b < nascimentos; b++) {
                Coelho novoCoelho = new Coelho(false);
                novosCoelhos.add(novoCoelho);
                Localizacao loc = campoAtualizado.localizacaoAdjacenteAleatoria(getLocalizacao());
                novoCoelho.definirLocalizacao(loc);
                campoAtualizado.colocar(novoCoelho, loc);
            }
            Localizacao novaLocalizacao = campoAtualizado.localizacaoAdjacenteLivre(getLocalizacao());
            // Só transfere para o campo atualizado se houver uma posição livre
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
     * A idade máxima que um coelho pode atingir.
     * 
     * @return A idade máxima.
     */
    @Override
    protected int IDADE_MAXIMA() {
        return IDADE_MAXIMA;
    }

    /**
     * A probabilidade de um coelho se reproduzir.
     * 
     * @return A probabilidade de reprodução.
     */
    @Override
    protected double PROBABILIDADE_REPRODUCAO() {
        return PROBABILIDADE_REPRODUCAO;
    }

    /**
     * O tamanho máximo da ninhada.
     * 
     * @return O tamanho máximo da ninhada.
     */
    @Override
    protected int TAMANHO_MAXIMO_NINHADA() {
        return TAMANHO_MAXIMO_NINHADA;
    }

    /**
     * A idade em que um coelho começa a procriar.
     * @return A idade reprodutiva.
     */
    @Override
    protected int getIdadeReprodutiva() {
        return IDADE_REPRODUTIVA;
    }

    /**
     * Informa que o coelho foi comido (morre).
     */
    public void foiComido()
    {
        definirEstaVivo(false);
    }
}