import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.HashMap;

/**
 * Uma visualização gráfica da grade de simulação.
 * A visualização exibe um retângulo colorido para cada localização
 * representando seu conteúdo. Usa uma cor de fundo padrão.
 * Cores para cada tipo de espécie podem ser definidas usando o
 * método definirCor.
 *
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-23
 */
public class VisualizadorSimulador extends JFrame
{
    // Cores usadas para localizações vazias.
    private static final Color COR_VAZIO = Color.white;

    // Cor usada para objetos que não têm cor definida.
    private static final Color COR_DESCONHECIDA = Color.gray;

    private final String PREFIXO_PASSO = "Passo: ";
    private final String PREFIXO_POPULACAO = "População: ";
    private JLabel rotuloPasso, populacao;
    private VisaoCampo visaoCampo;

    // Um mapa para armazenar cores para participantes na simulação
    private HashMap cores;
    // Um objeto de estatísticas computando e armazenando informações da simulação
    private EstatisticasCampo estatisticas;

    /**
     * Cria uma visualização com a largura e altura dadas.
     */
    public VisualizadorSimulador(int altura, int largura)
    {
        estatisticas = new EstatisticasCampo();
        cores = new HashMap();

        setTitle("Simulação de Raposas e Coelhos");
        rotuloPasso = new JLabel(PREFIXO_PASSO, JLabel.CENTER);
        populacao = new JLabel(PREFIXO_POPULACAO, JLabel.CENTER);

        setLocation(100, 50);

        visaoCampo = new VisaoCampo(altura, largura);

        Container conteudo = getContentPane();
        conteudo.add(rotuloPasso, BorderLayout.NORTH);
        conteudo.add(visaoCampo, BorderLayout.CENTER);
        conteudo.add(populacao, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    /**
     * Define uma cor a ser usada para uma classe de animal.
     */
    public void definirCor(Class classeAnimal, Color cor)
    {
        cores.put(classeAnimal, cor);
    }

    /**
     * Obtém a cor a ser usada para uma classe de animal.
     */
    private Color obterCor(Class classeAnimal)
    {
        Color cor = (Color)cores.get(classeAnimal);
        if(cor == null) {
            // nenhuma cor definida para esta classe
            return COR_DESCONHECIDA;
        }
        else {
            return cor;
        }
    }

    /**
     * Mostra o status atual do campo.
     * @param passo Qual passo de iteração é.
     * @param campo Status do campo a ser representado.
     */
    public void mostrarStatus(int passo, Campo campo)
    {
        if(!isVisible())
            setVisible(true);

        rotuloPasso.setText(PREFIXO_PASSO + passo);

        estatisticas.reiniciar();
        visaoCampo.prepararPintura();

        for(int linha = 0; linha < campo.getProfundidade(); linha++) {
            for(int coluna = 0; coluna < campo.getLargura(); coluna++) {
                Object animal = campo.getObjetoEm(linha, coluna);
                if(animal != null) {
                    estatisticas.incrementarContagem(animal.getClass());
                    visaoCampo.desenharMarca(coluna, linha, obterCor(animal.getClass()));
                }
                else {
                    visaoCampo.desenharMarca(coluna, linha, COR_VAZIO);
                }
            }
        }
        estatisticas.contagemFinalizada();

        populacao.setText(PREFIXO_POPULACAO + estatisticas.getDetalhesPopulacao(campo));
        visaoCampo.repaint();
    }

    /**
     * Determina se a simulação deve continuar a executar.
     * @return verdadeiro se houver mais de uma espécie viva.
     */
    public boolean estaViavel(Campo campo)
    {
        return estatisticas.estaViavel(campo);
    }

    /**
     * Fornece uma visualização gráfica de um campo retangular. Esta é
     * uma classe aninhada (uma classe definida dentro de uma classe) que
     * define um componente personalizado para a interface do usuário. Este
     * componente exibe o campo.
     * Isto é algo bastante avançado de GUI - você pode ignorar isso
     * para seu projeto se quiser.
     */
    private class VisaoCampo extends JPanel
    {
        private final int FATOR_ESCALA_GRADE = 6;

        private int larguraGrade, alturaGrade;
        private int escalaX, escalaY;
        Dimension tamanho;
        private Graphics g;
        private Image imagemCampo;

        /**
         * Cria um novo componente VisaoCampo.
         */
        public VisaoCampo(int altura, int largura)
        {
            alturaGrade = altura;
            larguraGrade = largura;
            tamanho = new Dimension(0, 0);
        }

        /**
         * Informa ao gerenciador de GUI quão grande gostaríamos de ser.
         */
        public Dimension getPreferredSize()
        {
            return new Dimension(larguraGrade * FATOR_ESCALA_GRADE,
                                 alturaGrade * FATOR_ESCALA_GRADE);
        }

        /**
         * Prepara para uma nova rodada de pintura. Como o componente
         * pode ser redimensionado, calcula o fator de escala novamente.
         */
        public void prepararPintura()
        {
            if(! tamanho.equals(getSize())) {  // se o tamanho mudou...
                tamanho = getSize();
                imagemCampo = visaoCampo.createImage(tamanho.width, tamanho.height);
                g = imagemCampo.getGraphics();

                escalaX = tamanho.width / larguraGrade;
                if(escalaX < 1) {
                    escalaX = FATOR_ESCALA_GRADE;
                }
                escalaY = tamanho.height / alturaGrade;
                if(escalaY < 1) {
                    escalaY = FATOR_ESCALA_GRADE;
                }
            }
        }

        /**
         * Pinta em uma localização da grade neste campo em uma cor dada.
         */
        public void desenharMarca(int x, int y, Color cor)
        {
            g.setColor(cor);
            g.fillRect(x * escalaX, y * escalaY, escalaX-1, escalaY-1);
        }

        /**
         * O componente de visualização do campo precisa ser reexibido. Copia a
         * imagem interna para a tela.
         */
        public void paintComponent(Graphics g)
        {
            if(imagemCampo != null) {
                g.drawImage(imagemCampo, 0, 0, null);
            }
        }
    }
}

