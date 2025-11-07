import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.awt.Color;

/**
 * Um simulador simples de predador-presa, baseado em um campo contendo
 * coelhos e raposas.
 *
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-09
 */
public class Simulador
{
    // As variáveis private static final representam
    // informações de configuração para a simulação.
    // A largura padrão para a grade.
    private static final int LARGURA_PADRAO = 50;
    // A profundidade padrão da grade.
    private static final int PROFUNDIDADE_PADRAO = 50;
    // A probabilidade de que uma raposa será criada em qualquer posição da grade.
    private static final double PROBABILIDADE_CRIACAO_RAPOSA = 0.02;
    // A probabilidade de que um coelho será criado em qualquer posição da grade.
    private static final double PROBABILIDADE_CRIACAO_COELHO = 0.08;

    // A lista de animais no campo
    private List animais;
    // A lista de animais recém-nascidos
    private List novosAnimais;
    // O estado atual do campo.
    private Campo campo;
    // Um segundo campo, usado para construir o próximo estágio da simulação.
    private Campo campoAtualizado;
    // O passo atual da simulação.
    private int passo;
    // Uma visão gráfica da simulação.
    private VisualizadorSimulador visualizador;

    /**
     * Constrói um campo de simulação com tamanho padrão.
     */
    public Simulador()
    {
        this(PROFUNDIDADE_PADRAO, LARGURA_PADRAO);
    }
    
    /**
     * Cria um campo de simulação com o tamanho dado.
     * @param profundidade Profundidade do campo. Deve ser maior que zero.
     * @param largura Largura do campo. Deve ser maior que zero.
     */
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

        // Cria uma visão do estado de cada localização no campo.
        visualizador = new VisualizadorSimulador(profundidade, largura);
        visualizador.definirCor(Raposa.class, Color.blue);
        visualizador.definirCor(Coelho.class, Color.orange);

        // Configura um ponto de partida válido.
        reiniciar();
    }
    
    /**
     * Executa a simulação do seu estado atual por um período razoavelmente longo,
     * por exemplo, 500 passos.
     */
    public void executarSimulacaoLonga()
    {
        simular(500);
    }
    
    /**
     * Executa a simulação do seu estado atual pelo número de passos dado.
     * Para antes do número de passos dado se deixar de ser viável.
     */
    public void simular(int numeroPassos)
    {
        for(int passo = 1; passo <= numeroPassos && visualizador.estaViavel(campo); passo++) {
            simularUmPasso();
        }
    }
    
    /**
     * Executa a simulação do seu estado atual por um único passo.
     * Itera sobre todo o campo atualizando o estado de cada
     * raposa e coelho.
     */
    public void simularUmPasso()
    {
        passo++;
        novosAnimais.clear();

        // deixa todos os animais agirem
        for(Iterator iter = animais.iterator(); iter.hasNext(); ) {
            Object animal = iter.next();
            if(animal instanceof Coelho) {
                Coelho coelho = (Coelho)animal;
                if(coelho.estaVivo()) {
                    coelho.correr(campoAtualizado, novosAnimais);
                }
                else {
                    iter.remove();   // remove coelhos mortos da coleção
                }
            }
            else if(animal instanceof Raposa) {
                Raposa raposa = (Raposa)animal;
                if(raposa.estaViva()) {
                    raposa.cacar(campo, campoAtualizado, novosAnimais);
                }
                else {
                    iter.remove();   // remove raposas mortas da coleção
                }
            }
            else {
                System.out.println("animal desconhecido encontrado");
            }
        }
        // adiciona animais recém-nascidos à lista de animais
        animais.addAll(novosAnimais);

        // Troca o campo e campoAtualizado no final do passo.
        Campo temp = campo;
        campo = campoAtualizado;
        campoAtualizado = temp;
        campoAtualizado.limpar();

        // exibe o novo campo na tela
        visualizador.mostrarStatus(passo, campo);
    }
        
    /**
     * Reinicia a simulação para uma posição inicial.
     */
    public void reiniciar()
    {
        passo = 0;
        animais.clear();
        campo.limpar();
        campoAtualizado.limpar();
        popular(campo);

        // Mostra o estado inicial na visualização.
        visualizador.mostrarStatus(passo, campo);
    }
    
    /**
     * Popula o campo com raposas e coelhos.
     */
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
                // senão deixa a localização vazia.
            }
        }
        Collections.shuffle(animais);
    }
}
