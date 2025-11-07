# Refatoração Completa para Português
## Arquivos Traduzidos
### 1. Location.java → Localizacao.java
- Classe: `Location` → `Localizacao`
- Atributos: `row` → `linha`, `col` → `coluna`
- Métodos: `getRow()` → `getLinha()`, `getCol()` → `getColuna()`
### 2. Field.java → Campo.java
- Classe: `Field` → `Campo`
- Atributos: `depth` → `profundidade`, `width` → `largura`, `field` → `campo`
- Métodos principais:
  - `clear()` → `limpar()`
  - `place()` → `colocar()`
  - `getObjectAt()` → `getObjetoEm()`
  - `randomAdjacentLocation()` → `localizacaoAdjacenteAleatoria()`
  - `freeAdjacentLocation()` → `localizacaoAdjacenteLivre()`
  - `adjacentLocations()` → `localizacoesAdjacentes()`
  - `getDepth()` → `getProfundidade()`
  - `getWidth()` → `getLargura()`
### 3. Rabbit.java → Coelho.java
- Classe: `Rabbit` → `Coelho`
- Constantes:
  - `BREEDING_AGE` → `IDADE_REPRODUCAO`
  - `MAX_AGE` → `IDADE_MAXIMA`
  - `BREEDING_PROBABILITY` → `PROBABILIDADE_REPRODUCAO`
  - `MAX_LITTER_SIZE` → `TAMANHO_MAXIMO_NINHADA`
- Atributos: `age` → `idade`, `alive` → `vivo`, `location` → `localizacao`
- Métodos principais:
  - `run()` → `correr()`
  - `incrementAge()` → `incrementarIdade()`
  - `breed()` → `reproduzir()`
  - `canBreed()` → `podeReproduzir()`
  - `isAlive()` → `estaVivo()`
  - `setEaten()` → `definirComido()`
  - `setLocation()` → `definirLocalizacao()`
### 4. Fox.java → Raposa.java
- Classe: `Fox` → `Raposa`
- Constantes:
  - `BREEDING_AGE` → `IDADE_REPRODUCAO`
  - `MAX_AGE` → `IDADE_MAXIMA`
  - `BREEDING_PROBABILITY` → `PROBABILIDADE_REPRODUCAO`
  - `MAX_LITTER_SIZE` → `TAMANHO_MAXIMO_NINHADA`
  - `RABBIT_FOOD_VALUE` → `VALOR_ALIMENTAR_COELHO`
- Atributos: `age` → `idade`, `alive` → `viva`, `location` → `localizacao`, `foodLevel` → `nivelComida`
- Métodos principais:
  - `hunt()` → `cacar()`
  - `incrementAge()` → `incrementarIdade()`
  - `incrementHunger()` → `incrementarFome()`
  - `findFood()` → `encontrarComida()`
  - `breed()` → `reproduzir()`
  - `canBreed()` → `podeReproduzir()`
  - `isAlive()` → `estaViva()`
  - `setLocation()` → `definirLocalizacao()`
### 5. Counter.java → Contador.java
- Classe: `Counter` → `Contador`
- Atributos: `name` → `nome`, `count` → `contagem`
- Métodos:
  - `getName()` → `getNome()`
  - `getCount()` → `getContagem()`
  - `increment()` → `incrementar()`
  - `reset()` → `reiniciar()`
### 6. FieldStats.java → EstatisticasCampo.java
- Classe: `FieldStats` → `EstatisticasCampo`
- Atributos: `counters` → `contadores`, `countsValid` → `contagensValidas`
- Métodos:
  - `getPopulationDetails()` → `getDetalhesPopulacao()`
  - `reset()` → `reiniciar()`
  - `incrementCount()` → `incrementarContagem()`
  - `countFinished()` → `contagemFinalizada()`
  - `isViable()` → `estaViavel()`
  - `generateCounts()` → `gerarContagens()`
### 7. Simulator.java → Simulador.java
- Classe: `Simulator` → `Simulador`
- Constantes:
  - `DEFAULT_WIDTH` → `LARGURA_PADRAO`
  - `DEFAULT_DEPTH` → `PROFUNDIDADE_PADRAO`
  - `FOX_CREATION_PROBABILITY` → `PROBABILIDADE_CRIACAO_RAPOSA`
  - `RABBIT_CREATION_PROBABILITY` → `PROBABILIDADE_CRIACAO_COELHO`
- Atributos:
  - `animals` → `animais`
  - `newAnimals` → `novosAnimais`
  - `field` → `campo`
  - `updatedField` → `campoAtualizado`
  - `step` → `passo`
  - `view` → `visualizador`
- Métodos:
  - `runLongSimulation()` → `executarSimulacaoLonga()`
  - `simulate()` → `simular()`
  - `simulateOneStep()` → `simularUmPasso()`
  - `reset()` → `reiniciar()`
  - `populate()` → `popular()`
### 8. SimulatorView.java → VisualizadorSimulador.java
- Classe: `SimulatorView` → `VisualizadorSimulador`
- Constantes:
  - `EMPTY_COLOR` → `COR_VAZIO`
  - `UNKNOWN_COLOR` → `COR_DESCONHECIDA`
  - `STEP_PREFIX` → `PREFIXO_PASSO`
  - `POPULATION_PREFIX` → `PREFIXO_POPULACAO`
- Atributos:
  - `stepLabel` → `rotuloPasso`
  - `population` → `populacao`
  - `fieldView` → `visaoCampo`
  - `colors` → `cores`
  - `stats` → `estatisticas`
- Métodos:
  - `setColor()` → `definirCor()`
  - `getColor()` → `obterCor()`
  - `showStatus()` → `mostrarStatus()`
  - `isViable()` → `estaViavel()`
- Classe interna: `FieldView` → `VisaoCampo`
  - Métodos: `preparePaint()` → `prepararPintura()`, `drawMark()` → `desenharMarca()`
### 9. Principal.java
- Atualizado para usar `Simulador` e `executarSimulacaoLonga()`
## Compilação e Execução
O código foi completamente traduzido e está funcional:
```bash
# Compilar
cd Projeto/src
javac *.java
# Executar
java Principal
```
## Status
✅ Todos os arquivos traduzidos
✅ Compilação bem-sucedida
✅ Execução testada e funcionando
