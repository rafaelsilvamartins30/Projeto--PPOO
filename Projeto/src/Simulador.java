import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.awt.Color;
public class Simulador
{
    private static final int LARGURA_PADRAO = 50;
    private static final int PROFUNDIDADE_PADRAO = 50;
    private static final double PROBABILIDADE_CRIACAO_RAPOSA = 0.02;
    private static final double PROBABILIDADE_CRIACAO_COELHO = 0.08;
    private List animais;
    private List novosAnimais;
    private Campo campo;
    private Campo campoAtualizado;
    private int passo;
    private VisualizadorSimulador visualizador;
    public Simulador()
    {
        this(PROFUNDIDADE_PADRAO, LARGURA_PADRAO);
    }
    public Simulador(int profundidade, int largura)
    {
        if(largura <= 0 || profundidade <= 0) {
            System.out.println("As dimensões devem ser maiores que zero.");
            System.out.println("Usando valores padrão.");
            profundidade = PROFUNDIDADE_PADRAO;
            largura = LARGURA_PADRAO;
        }
        animais = new ArrayList();
        novosAnimais = new ArrayList();
        campo = new Campo(profundidade, largura);
        campoAtualizado = new Campo(profundidade, largura);
        visualizador = new VisualizadorSimulador(profundidade, largura);
        visualizador.definirCor(Raposa.class, Color.blue);
        visualizador.definirCor(Coelho.class, Color.orange);
        reiniciar();
    }
    public void executarSimulacaoLonga()
    {
        simular(500);
    }
    public void simular(int numeroPassos)
    {
        for(int passo = 1; passo <= numeroPassos && visualizador.estaViavel(campo); passo++) {
            simularUmPasso();
        }
    }
    public void simularUmPasso()
    {
        passo++;
        novosAnimais.clear();
        for(Iterator iter = animais.iterator(); iter.hasNext(); ) {
            Object animal = iter.next();
            if(animal instanceof Coelho) {
                Coelho coelho = (Coelho)animal;
                if(coelho.estaVivo()) {
                    coelho.correr(campoAtualizado, novosAnimais);
                }
                else {
                    iter.remove();   
                }
            }
            else if(animal instanceof Raposa) {
                Raposa raposa = (Raposa)animal;
                if(raposa.estaViva()) {
                    raposa.cacar(campo, campoAtualizado, novosAnimais);
                }
                else {
                    iter.remove();   
                }
            }
            else {
                System.out.println("animal desconhecido encontrado");
            }
        }
        animais.addAll(novosAnimais);
        Campo temp = campo;
        campo = campoAtualizado;
        campoAtualizado = temp;
        campoAtualizado.limpar();
        visualizador.mostrarStatus(passo, campo);
    }
    public void reiniciar()
    {
        passo = 0;
        animais.clear();
        campo.limpar();
        campoAtualizado.limpar();
        popular(campo);
        visualizador.mostrarStatus(passo, campo);
    }
    private void popular(Campo campo)
    {
        Random rand = new Random();
        campo.limpar();
        for(int linha = 0; linha < campo.getProfundidade(); linha++) {
            for(int coluna = 0; coluna < campo.getLargura(); coluna++) {
                if(rand.nextDouble() <= PROBABILIDADE_CRIACAO_RAPOSA) {
                    Raposa raposa = new Raposa(true);
                    animais.add(raposa);
                    raposa.definirLocalizacao(linha, coluna);
                    campo.colocar(raposa, linha, coluna);
                }
                else if(rand.nextDouble() <= PROBABILIDADE_CRIACAO_COELHO) {
                    Coelho coelho = new Coelho(true);
                    animais.add(coelho);
                    coelho.definirLocalizacao(linha, coluna);
                    campo.colocar(coelho, linha, coluna);
                }
            }
        }
        Collections.shuffle(animais);
    }
}
