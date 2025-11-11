# 🦊🐇 Simulação de Ecossistema: Raposas e Coelhos (Java)

Este projeto é uma simulação orientada a objetos de um **ecossistema** com **raposas** e **coelhos**, que interagem em um campo bidimensional. Baseado no modelo clássico de Barnes & Kölling e **traduzido/adaptado para português**.

---

## 🎯 Objetivos didáticos

- Praticar **POO**: abstração, encapsulamento, herança e polimorfismo.  
- Usar **interfaces** para contratos comportamentais.  
- Trabalhar com **coleções Java** (List, HashMap, Iterator).  
- Simular dinâmicas **predador–presa** (nascimento, envelhecimento, morte, reprodução e alimentação).

---

## 🧩 Estrutura das classes

```
src/
├── Animal.java                # Classe abstrata base (idade, vida, reprodução, localização)
├── Ator.java                  # Interface: define agir(...) e estaVivo()
├── Campo.java                 # Grade 2D e utilidades de vizinhança/ocupação
├── Coelho.java                # Presa: movimenta, reproduz e pode morrer por superlotação/idade
├── Contador.java              # Utilitário de contagem por espécie
├── Desenhavel.java            # Interface opcional para elementos desenháveis (GUI)
├── EstatisticasCampo.java     # Coleta e exibe estatísticas de população/viabilidade
├── Localizacao.java           # Par (linha, coluna) com equals/hashCode
├── Raposa.java                # Predador: caça coelhos, sente fome e reproduz
├── Simulador.java             # Loop principal da simulação (popular, passos, troca de campos)
├── VisualizacaoSimulador.java # GUI (Swing/AWT) para desenhar o campo e mostrar estatísticas
└── Principal.java             # Ponto de entrada (main)
```

### Principais responsabilidades

- **Animal**: base para espécies (idade, vida, reprodução probabilística, localização).  
- **Ator**: contrato para `agir(...)` durante um passo da simulação.  
- **Campo**: mantém matriz de objetos e fornece vizinhanças (adjacentes livres/aleatórias).  
- **Coelho**: define parâmetros de reprodução/idade máxima e movimento simples.  
- **Raposa**: além de reproduzir/envelhecer, **caça coelhos** e tem **fome** que leva à morte.  
- **EstatisticasCampo**: contabiliza por classe e testa viabilidade (mais de uma espécie viva).  
- **VisualizacaoSimulador**: janela Swing que colore cada célula por espécie e mostra contagens.  
- **Simulador**: orquestra a simulação: popula o campo (probabilidades), itera passos, atualiza GUI.  
- **Principal**: cria `Simulador` e executa `simular(100)` por padrão.

---


## 🔧 Parâmetros principais da simulação

- **Dimensões do campo**: 50 x 50 (padrão).  
- **Prob. de criação**: raposa = 0.02; coelho = 0.08.  
- **Reprodução** e **limites de idade** são **específicos por espécie**:
  - Coelho: idade reprodutiva = 5; idade máxima = 50; prob. reprodução = 0.15; ninhada ≤ 5.  
  - Raposa: idade reprodutiva = 10; idade máxima = 150; prob. reprodução = 0.09; ninhada ≤ 3; fome.

> Esses parâmetros estão codificados nas classes das espécies e podem ser ajustados para experimentar diferentes dinâmicas.

---

## 🧪 Como alterar o número de passos

Na classe **Principal.java**:
```java
public class Principal {
  public static void main(String[] args) {
    Simulador simulador = new Simulador();
    simulador.simular(200); // altere 100 -> 200, 500, etc.
  }
}
```

---

## 🖼️ Interface (GUI)

- A **VisualizacaoSimulador** abre uma janela com:
  - **Passo** (iteração atual);
  - **População** por espécie;
  - Um **grid** onde cada célula é colorida conforme a espécie presente (ou branco se vazia).
- As cores das classes são registradas em `Simulador` via `visualizacao.definirCor(...)`.

---

## 👥 Autoria e créditos

- Adaptação/tradução para PT-BR e organização do código por estudantes da **UFLA (SI)**.  
- Baseado no projeto didático de **David J. Barnes & Michael Kölling**.

---

## 📄 Licença

Uso **educacional**. Verifique a política da sua instituição antes de redistribuir.
