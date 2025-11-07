import java.awt.Color;
public class Contador
{
    private String nome;
    private int contagem;
    public Contador(String nome)
    {
        this.nome = nome;
        contagem = 0;
    }
    public String getNome()
    {
        return nome;
    }
    public int getContagem()
    {
        return contagem;
    }
    public void incrementar()
    {
        contagem++;
    }
    public void reiniciar()
    {
        contagem = 0;
    }
}
