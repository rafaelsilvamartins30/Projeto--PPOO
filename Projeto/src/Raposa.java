import java.util.List;
import java.util.Iterator;
import java.util.Random;
public class Raposa
{
    private static final int IDADE_REPRODUCAO = 10;
    private static final int IDADE_MAXIMA = 150;
    private static final double PROBABILIDADE_REPRODUCAO = 0.09;
    private static final int TAMANHO_MAXIMO_NINHADA = 3;
    private static final int VALOR_ALIMENTAR_COELHO = 4;
    private static final Random rand = new Random();
    private int idade;
    private boolean viva;
    private Localizacao localizacao;
    private int nivelComida;
    public Raposa(boolean idadeAleatoria)
    {
        idade = 0;
        viva = true;
        if(idadeAleatoria) {
            idade = rand.nextInt(IDADE_MAXIMA);
            nivelComida = rand.nextInt(VALOR_ALIMENTAR_COELHO);
        }
        else {
            nivelComida = VALOR_ALIMENTAR_COELHO;
        }
    }
    public void cacar(Campo campoAtual, Campo campoAtualizado, List novasRaposas)
    {
        incrementarIdade();
        incrementarFome();
        if(estaViva()) {
            int nascimentos = reproduzir();
            for(int b = 0; b < nascimentos; b++) {
                Raposa novaRaposa = new Raposa(false);
                novasRaposas.add(novaRaposa);
                Localizacao loc = campoAtualizado.localizacaoAdjacenteAleatoria(localizacao);
                novaRaposa.definirLocalizacao(loc);
                campoAtualizado.colocar(novaRaposa, loc);
            }
            Localizacao novaLocalizacao = encontrarComida(campoAtual, localizacao);
            if(novaLocalizacao == null) {  
                novaLocalizacao = campoAtualizado.localizacaoAdjacenteLivre(localizacao);
            }
            if(novaLocalizacao != null) {
                definirLocalizacao(novaLocalizacao);
                campoAtualizado.colocar(this, novaLocalizacao);
            }
            else {
                viva = false;
            }
        }
    }
    private void incrementarIdade()
    {
        idade++;
        if(idade > IDADE_MAXIMA) {
            viva = false;
        }
    }
    private void incrementarFome()
    {
        nivelComida--;
        if(nivelComida <= 0) {
            viva = false;
        }
    }
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
    public boolean estaViva()
    {
        return viva;
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
