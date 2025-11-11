import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
public class EstatisticasCampo
{
    private HashMap contadores;
    private boolean contagensValidas;
    public EstatisticasCampo()
    {
        contadores = new HashMap();
        contagensValidas = true;
    }
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
    public void reiniciar()
    {
        contagensValidas = false;
        Iterator chaves = contadores.keySet().iterator();
        while(chaves.hasNext()) {
            Contador cnt = (Contador) contadores.get(chaves.next());
            cnt.reiniciar();
        }
    }
    public void incrementarContagem(Class classeAnimal)
    {
        Contador cnt = (Contador) contadores.get(classeAnimal);
        if(cnt == null) {
            cnt = new Contador(classeAnimal.getName());
            contadores.put(classeAnimal, cnt);
        }
        cnt.incrementar();
    }
    public void contagemFinalizada()
    {
        contagensValidas = true;
    }
    public boolean estaViavel(Campo campo)
    {
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
