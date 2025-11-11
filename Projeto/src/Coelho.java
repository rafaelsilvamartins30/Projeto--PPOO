import java.util.List;
import java.util.Random;
public class Coelho
{
    private static final int IDADE_REPRODUCAO = 5;
    private static final int IDADE_MAXIMA = 50;
    private static final double PROBABILIDADE_REPRODUCAO = 0.15;
    private static final int TAMANHO_MAXIMO_NINHADA = 5;
    private static final Random rand = new Random();
    private int idade;
    private boolean vivo;
    private Localizacao localizacao;
    public Coelho(boolean idadeAleatoria)
    {
        idade = 0;
        vivo = true;
        if(idadeAleatoria) {
            idade = rand.nextInt(IDADE_MAXIMA);
        }
    }
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
            if(novaLocalizacao != null) {
                definirLocalizacao(novaLocalizacao);
                campoAtualizado.colocar(this, novaLocalizacao);
            }
            else {
                vivo = false;
            }
        }
    }
    private void incrementarIdade()
    {
        idade++;
        if(idade > IDADE_MAXIMA) {
            vivo = false;
        }
    }
    private int reproduzir()
    {
        int nascimentos = 0;
        if(podeReproduzir() && rand.nextDouble() <= PROBABILIDADE_REPRODUCAO) {
            nascimentos = rand.nextInt(TAMANHO_MAXIMO_NINHADA) + 1;
        }
        return nascimentos;
    }
    private boolean podeReproduzir()
    {
        return idade >= IDADE_REPRODUCAO;
    }
    public boolean estaVivo()
    {
        return vivo;
    }
    public void definirComido()
    {
        vivo = false;
    }
    public void definirLocalizacao(int linha, int coluna)
    {
        this.localizacao = new Localizacao(linha, coluna);
    }
    public void definirLocalizacao(Localizacao localizacao)
    {
        this.localizacao = localizacao;
    }
}
