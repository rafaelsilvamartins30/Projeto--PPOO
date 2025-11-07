import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;

public class EstatisticasCampo
{
    // Contadores para cada tipo de entidade (raposa, coelho, etc.) na simulação.
    private HashMap contadores;
    // Se os contadores estão atualmente atualizados.
    private boolean contagensValidas;

    /**
     * Constrói um objeto de estatísticas do campo.
     */
    public EstatisticasCampo()
    {
        // Configura uma coleção para contadores para cada tipo de animal que
        // possamos encontrar
        contadores = new HashMap();
        contagensValidas = true;
    }

    /**
     * @return Uma string descrevendo quais animais estão no campo.
     */
    public String getDetalhesPopulacao(Campo campo)
    {
        StringBuffer buffer = new StringBuffer();
        if(!contagensValidas) {
            gerarContagens(campo);
        }
        Iterator chaves = contadores.keySet().iterator();
        while(chaves.hasNext()) {
            Contador info = (Contador) contadores.get(chaves.next());
            buffer.append(info.getNome());
            buffer.append(": ");
            buffer.append(info.getContagem());
            buffer.append(' ');
        }
        return buffer.toString();
    }
    
    /**
     * Invalida o conjunto atual de estatísticas; reinicia todas as
     * contagens para zero.
     */
    public void reiniciar()
    {
        contagensValidas = false;
        Iterator chaves = contadores.keySet().iterator();
        while(chaves.hasNext()) {
            Contador cnt = (Contador) contadores.get(chaves.next());
            cnt.reiniciar();
        }
    }

    /**
     * Incrementa a contagem para uma classe de animal.
     */
    public void incrementarContagem(Class classeAnimal)
    {
        Contador cnt = (Contador) contadores.get(classeAnimal);
        if(cnt == null) {
            // ainda não temos um contador para esta espécie - criar um
            cnt = new Contador(classeAnimal.getName());
            contadores.put(classeAnimal, cnt);
        }
        cnt.incrementar();
    }

    /**
     * Indica que uma contagem de animais foi concluída.
     */
    public void contagemFinalizada()
    {
        contagensValidas = true;
    }

    /**
     * Determina se a simulação ainda é viável.
     * Ou seja, deve continuar a executar.
     * @return verdadeiro se houver mais de uma espécie viva.
     */
    public boolean estaViavel(Campo campo)
    {
        // Quantas contagens são diferentes de zero.
        int naoZero = 0;
        if(!contagensValidas) {
            gerarContagens(campo);
        }
        Iterator chaves = contadores.keySet().iterator();
        while(chaves.hasNext()) {
            Contador info = (Contador) contadores.get(chaves.next());
            if(info.getContagem() > 0) {
                naoZero++;
            }
        }
        return naoZero > 1;
    }
    
    /**
     * Gera contagens do número de raposas e coelhos.
     * Estas não são mantidas atualizadas conforme raposas e coelhos
     * são colocados no campo, mas apenas quando uma solicitação
     * é feita para a informação.
     */
    private void gerarContagens(Campo campo)
    {
        reiniciar();
        for(int linha = 0; linha < campo.getProfundidade(); linha++) {
            for(int coluna = 0; coluna < campo.getLargura(); coluna++) {
                Object animal = campo.getObjetoEm(linha, coluna);
                if(animal != null) {
                    incrementarContagem(animal.getClass());
                }
            }
        }
        contagensValidas = true;
    }
}
