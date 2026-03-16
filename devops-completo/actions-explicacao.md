# Diferença entre Workflows e Actions no GitHub Actions

## O que é um Workflow?

Um **workflow** é um processo automatizado configurado em um arquivo `.yml` dentro da pasta `.github/workflows/`. Ele define **quando** (gatilhos/triggers) e **o que** deve ser executado (jobs e steps). É o nível mais alto de automação no GitHub Actions.

Exemplos de gatilhos: `push`, `pull_request`, `workflow_dispatch`, `workflow_call`.

## O que é uma Action?

Uma **action** é uma unidade reutilizável e atômica de automação — um bloco de construção que executa uma tarefa específica dentro de um step de um workflow. Actions podem ser criadas pela comunidade, pela GitHub ou por você mesmo.

| Característica | Workflow | Action |
|---|---|---|
| Nível | Processo completo | Tarefa individual |
| Arquivo principal | `.github/workflows/*.yml` | `action.yml` |
| Reutilização | Via `workflow_call` | Via `uses:` |
| Publicação | Não publicada no Marketplace | Pode ser publicada |

## Como uma Action é estruturada internamente?

Uma action é composta por:

- **`action.yml`** — arquivo de metadados obrigatório
- Código de execução (JavaScript, Docker ou composite steps)

### O papel do `action.yml`

O `action.yml` define entradas, saídas e como a action executa:

```yaml
name: "Nome da Action"
description: "O que ela faz"

inputs:
  meu_parametro:
    description: "Descrição do parâmetro"
    required: true
    default: "valor padrão"

outputs:
  meu_resultado:
    description: "O que a action retorna"

runs:
  using: "node20"
  main: "index.js"
```

## Exemplo prático: actions/setup-java@v4

No nosso `ci.yml`, essa action é chamada da seguinte forma:

```yaml
- name: Configurar JDK 17
  uses: actions/setup-java@v4
  with:
    java-version: "17"
    distribution: "temurin"
```

- `uses: actions/setup-java@v4` → referencia o repositório `github.com/actions/setup-java` na versão `v4`
- `with:` → passa os **inputs** definidos no `action.yml` dessa action
- Internamente, ela instala o JDK e configura `JAVA_HOME` e `PATH` para os steps seguintes
