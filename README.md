# CheckPoint 2: O Desastre do FiapRideDelivery (Refatoração OOP)

**Disciplina:** Object-Oriented Programming - FIAP
**Projeto:** FiapRideDelivery (Sistema de Logística)
**Aluno:** Nícolas Borges Natal
**RM:** 568230
**Sala:** 2CCPR
**Professor:** Ygor Moraes Martins dos Anjos

---

## Sobre o projeto

Refatoração do sistema de logística da FiapRideDelivery, iniciado por um estagiário a pedido da diretoria. O código legado compilava e rodava, mas usava nomes sem significado, expunha todos os atributos como `public`, duplicava código entre `caminhao` e `moto` em vez de usar herança e "engessava" a `Rota`, que só aceitava caminhão.

A refatoração aplica os tópicos avaliados: **Encapsulamento, Herança, Associação, Construtores, Documentação e Clean Code**.

---

## Estrutura do repositório

```
prova_refatoracao_nicolas_borges_natal/
├── diagrama-fiapridedelivery-refatorado.png
├── src/
│   └── br/
│       └── com/
│           └── fiapridedelivery/
│               ├── model/
│               │   ├── Veiculo.java
│               │   ├── Caminhao.java
│               │   ├── Moto.java
│               │   ├── Pacote.java
│               │   ├── StatusPacote.java
│               │   └── Rota.java
│               └── main/
│                   └── SistemaPrincipal.java
└── README.md
```

---

## O que foi corrigido

### 1. Nomenclatura (Clean Code)

| Antes | Depois | Motivo |
|---|---|---|
| `caminhao` | `Caminhao` | Classes em PascalCase |
| `moto` | `Moto` | Classes em PascalCase |
| `pacote` | `Pacote` | Classes em PascalCase |
| `Principal` | `SistemaPrincipal` | Nome que revela a intenção |
| `pl` | `placa` | Abreviação sem significado eliminada |
| `cap` | `capacidadeCargaKg` | Nome autoexplicativo, com a unidade de medida |
| `eixos` | `quantidadeEixos` | Deixa claro que é uma contagem |
| `bau` | `possuiBau` | Booleano lido como pergunta (sim/não) |
| `cod` | `codigo` | Abreviação sem significado eliminada |
| `p` | `pesoKg` | Nome autoexplicativo, com a unidade de medida |
| `s` | `status` | Diz o que o dado realmente é |
| `muda(String x)` | `atualizarStatus(StatusPacote novoStatus)` | Verbo do domínio e parâmetro com nome e tipo claros |
| `vai()` | `iniciarEntrega()` | Verbo do domínio do negócio |
| `p1` / `c1` | `pacote` / `veiculo` | Números no nome não dizem nada |

### 2. Encapsulamento

- Todos os atributos passaram de `public` para `private`.
- A leitura acontece por **getters**; a alteração, apenas por métodos com regra de negócio (`atualizarStatus()`, `setPossuiBau()`).
- `placa`, `capacidadeCargaKg`, `quantidadeEixos`, `codigo` e `pesoKg` são `final`: depois de criados, não mudam.

### 3. Herança

- `caminhao` e `moto` repetiam `pl` e `cap`. Esses dados foram para a superclasse abstrata **`Veiculo`**, junto com as validações e o método `suportaPeso()`.
- `Caminhao` e `Moto` agora usam `extends Veiculo` e declaram só o que é específico de cada um (`quantidadeEixos` e `possuiBau`).
- `Veiculo` é `abstract`: não existe "veículo genérico", só caminhão, moto ou outro tipo concreto.
- O método abstrato `getTipo()` obriga cada subclasse a se identificar, e `toString()` é sobrescrito reaproveitando o `super.toString()`.

### 4. Associação

- A `Rota` guardava um `caminhao`, então entregar de moto exigia mudar a classe. Agora ela se associa ao tipo abstrato **`Veiculo`** e aceita `Caminhao`, `Moto` ou qualquer veículo futuro (ex.: `Van`) sem nenhuma alteração (polimorfismo).
- A `Rota` só é criada com um `Pacote` e um `Veiculo` válidos (não aceita nulo).

### 5. Construtores

- Todas as classes ganharam construtores com os dados obrigatórios, eliminando a atribuição direta de campos feita no `main` original.
- O objeto já nasce validado: se algum dado for inválido, o construtor lança `IllegalArgumentException`.
- O construtor de `Veiculo` é `protected`, usado apenas pelas subclasses.
- Todo `Pacote` nasce com status `PENDENTE`, sem precisar atribuir manualmente.

### 6. Blindagem das regras de negócio

- `capacidadeCargaKg` precisa ser maior que zero (o `-500.0` do código original agora é recusado).
- `placa` aceita o padrão antigo (`ABC1234`) ou Mercosul (`ABC1D23`) e é normalizada para maiúsculas, sem hífen.
- `Caminhao` exige no mínimo 2 eixos.
- `codigo` do pacote não aceita nulo nem vazio, e `pesoKg` precisa ser maior que zero.
- A `Rota` recusa um pacote mais pesado que a capacidade do veículo.
- O status segue o fluxo `PENDENTE` → `EM_TRANSITO` → `ENTREGUE`, e um pacote entregue não muda mais de status.

### 7. Status com enum

O status era uma `String` livre (`"Pendente"`), sujeita a erro de digitação e a valores inexistentes. Passou a ser o enum **`StatusPacote`**, com apenas três valores possíveis: `PENDENTE`, `EM_TRANSITO` e `ENTREGUE`.

### 8. Documentação

O código legado não tinha nenhuma documentação. Classes, construtores e métodos públicos agora têm comentários `//` diretos explicando a responsabilidade de cada um.

---

## Como executar

**Pelo Eclipse:** clique com o botão direito em `SistemaPrincipal.java` → *Run As* → *Java Application*.

**Pelo terminal:**

```bash
javac -encoding UTF-8 -d bin $(find src -name "*.java")
java -cp bin br.com.fiapridedelivery.main.SistemaPrincipal
```

---

## Saída esperada

```
=== Frota ===
Caminhão ABC1234 (capacidade: 5000.0 kg) - 3 eixos
Moto XYZ1A23 (capacidade: 30.0 kg) - com baú

=== Entrega com caminhão ===
Levando pacote BR999 no veículo Caminhão ABC1234
Pacote BR999 entregue pelo veículo ABC1234
Pacote BR999 (10.5 kg) - Entregue

=== Entrega com moto ===
Levando pacote BR1000 no veículo Moto XYZ1A23
Pacote BR1000 (2.3 kg) - Em trânsito

=== Validações ===
Erro: A capacidade de carga deve ser maior que zero.
Erro: O pacote BR2000 (50.0 kg) excede a capacidade do veículo XYZ1A23.
```

---

## Diagrama de classes

O arquivo `diagrama-fiapridedelivery-refatorado.png` (na raiz do repositório) foi gerado no Astah e representa o sistema já refatorado:

- `Veiculo` como classe abstrata (nome em itálico), com `Caminhao` e `Moto` ligadas a ela por **generalização** (herança).
- `Rota` com **associação** para `Pacote` e para `Veiculo`, e `Pacote` associado ao enum `StatusPacote`.
- Visibilidade `-` (private) nos atributos, `+` (public) nos métodos e `#` (protected) no construtor de `Veiculo`.