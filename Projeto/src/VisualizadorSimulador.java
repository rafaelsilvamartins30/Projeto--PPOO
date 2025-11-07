import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.HashMap;
public class VisualizadorSimulador extends JFrame
{
    private static final Color COR_VAZIO = Color.white;
    private static final Color COR_DESCONHECIDA = Color.gray;
    private final String PREFIXO_PASSO = "Passo: ";
    private final String PREFIXO_POPULACAO = "População: ";
    private JLabel rotuloPasso, populacao;
    private VisaoCampo visaoCampo;
    private HashMap cores;
    private EstatisticasCampo estatisticas;
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
    public void definirCor(Class classeAnimal, Color cor)
    {
        cores.put(classeAnimal, cor);
    }
    private Color obterCor(Class classeAnimal)
    {
        Color cor = (Color)cores.get(classeAnimal);
        if(cor == null) {
            return COR_DESCONHECIDA;
        }
        else {
            return cor;
        }
    }
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
    public boolean estaViavel(Campo campo)
    {
        return estatisticas.estaViavel(campo);
    }
    private class VisaoCampo extends JPanel
    {
        private final int FATOR_ESCALA_GRADE = 6;
        private int larguraGrade, alturaGrade;
        private int escalaX, escalaY;
        Dimension tamanho;
        private Graphics g;
        private Image imagemCampo;
        public VisaoCampo(int altura, int largura)
        {
            alturaGrade = altura;
            larguraGrade = largura;
            tamanho = new Dimension(0, 0);
        }
        public Dimension getPreferredSize()
        {
            return new Dimension(larguraGrade * FATOR_ESCALA_GRADE,
                                 alturaGrade * FATOR_ESCALA_GRADE);
        }
        public void prepararPintura()
        {
            if(! tamanho.equals(getSize())) {  
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
        public void desenharMarca(int x, int y, Color cor)
        {
            g.setColor(cor);
            g.fillRect(x * escalaX, y * escalaY, escalaX-1, escalaY-1);
        }
        public void paintComponent(Graphics g)
        {
            if(imagemCampo != null) {
                g.drawImage(imagemCampo, 0, 0, null);
            }
        }
    }
}
