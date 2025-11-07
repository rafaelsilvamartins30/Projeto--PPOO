import java.util.List;
import java.util.Random;


public class Coelho
{
    // Características compartilhadas por todos os coelhos (campos estáticos).

    // A idade em que um coelho pode começar a se reproduzir.
    private static final int IDADE_REPRODUCAO = 5;
    // A idade até a qual um coelho pode viver.
    private static final int IDADE_MAXIMA = 50;
    // A probabilidade de um coelho se reproduzir.
    private static final double PROBABILIDADE_REPRODUCAO = 0.15;
    // O número máximo de nascimentos.
    private static final int TAMANHO_MAXIMO_NINHADA = 5;
    // Um gerador de números aleatórios compartilhado para controlar reprodução.
    private static final Random rand = new Random();
    
    // Características individuais (campos de instância).

    // A idade do coelho.
    private int idade;
    // Se o coelho está vivo ou não.
    private boolean vivo;
    // A posição do coelho
    private Localizacao localizacao;

    /**
     * Cria um novo coelho. Um coelho pode ser criado com idade
     * zero (recém-nascido) ou com uma idade aleatória.
     *
     * @param idadeAleatoria Se verdadeiro, o coelho terá uma idade aleatória.
     */
    public Coelho(boolean idadeAleatoria)
    {
        idade = 0;
        vivo = true;
        if(idadeAleatoria) {
            idade = rand.nextInt(IDADE_MAXIMA);
        }
    }
    
    /**
     * Isto é o que o coelho faz na maior parte do tempo - ele corre
     * por aí. Às vezes ele se reproduzirá ou morrerá de velhice.
     */
    public void correr(Campo campoAtualizado, List novosCoelhos)
    {
        incrementarIdade();
        if(vivo) {
            int nascimentos = reproduzir();
            for(int b = 0; b < nascimentos; b++) {
                Coelho novoCoelho = new Coelho(false);
                novosCoelhos.add(novoCoelho);
                Localizacao loc = campoAtualizado.localizacaoAdjacenteAleatoria(localizacao);
                novoCoelho.definirLocalizacao(loc);
                campoAtualizado.colocar(novoCoelho, loc);
            }
            Localizacao novaLocalizacao = campoAtualizado.localizacaoAdjacenteLivre(localizacao);
            // Só transfere para o campo atualizado se houver uma localização livre
            if(novaLocalizacao != null) {
                definirLocalizacao(novaLocalizacao);
                campoAtualizado.colocar(this, novaLocalizacao);
            }
            else {
                // não pode se mover nem ficar - superlotação - todas as localizações ocupadas
                vivo = false;
            }
        }
    }
    
    /**
     * Aumenta a idade.
     * Isso pode resultar na morte do coelho.
     */
    private void incrementarIdade()
    {
        idade++;
        if(idade > IDADE_MAXIMA) {
            vivo = false;
        }
    }
    
    /**
     * Gera um número representando o número de nascimentos,
     * se ele puder se reproduzir.
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
     * Um coelho pode se reproduzir se atingiu a idade de reprodução.
     */
    private boolean podeReproduzir()
    {
        return idade >= IDADE_REPRODUCAO;
    }
    
    /**
     * Verifica se o coelho está vivo ou não.
     * @return Verdadeiro se o coelho ainda está vivo.
     */
    public boolean estaVivo()
    {
        return vivo;
    }

    /**
     * Informa ao coelho que ele foi comido :(
     */
    public void definirComido()
    {
        vivo = false;
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
     * Define a localização do coelho.
     * @param localizacao A localização do coelho.
     */
    public void definirLocalizacao(Localizacao localizacao)
    {
        this.localizacao = localizacao;
    }
}
