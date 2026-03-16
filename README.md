# ricknmorty-api

![CI Status](https://github.com/juliatlrc/devops/actions/workflows/ci.yml/badge.svg)

API Spring Boot com dados da Rick & Morty, containerizada com Docker.

---

## Como executar

```bash
git clone https://github.com/juliatlrc/devops
cd devops
docker-compose up -d --build
```

Acesse:
- `http://localhost:8080/api/characters`
- `http://localhost:8080/api/episodes`
- `http://localhost:8080/api/locations`

---

## 🔍 Depuração de Falha no Pipeline (TP2 — Tarefa 5)

Durante o desenvolvimento do TP2, foi introduzido intencionalmente um erro no pipeline
adicionando `run: exit 1` em um step do job de build.

**Como o problema foi identificado:**
- Na aba **Actions** do GitHub, o workflow apareceu com ícone vermelho (❌)
- Clicando na execução com falha, foi possível ver qual job falhou
- Expandindo o step com erro, o log exibiu: `Process completed with exit code 1`
- A interface destacou exatamente a linha do erro no log de execução

**Como foi corrigido:**
- O comando `exit 1` foi removido e substituído por `mvn package -DskipTests`
- Um novo push para a branch `main` foi realizado
- O workflow executou novamente com sucesso (✅)

---

## 📊 Monitoramento e Modos de Execução (TP2 — Tarefa 6)

Foram realizadas duas execuções distintas do pipeline:

1. **Push automático** — disparado ao fazer `git push` na branch `main`. O workflow iniciou
   imediatamente sem nenhuma intervenção manual. Ideal para validação contínua de cada commit.

2. **Execução manual (Run workflow)** — disparada pela aba Actions usando o botão "Run workflow".
   Permitiu escolher os parâmetros `run_tests` e `run_lint`, dando controle sobre quais
   verificações executar sem precisar fazer um commit.

**Observação:** A principal diferença entre os dois modos está no controle e contexto.
O push automático garante que todo código novo seja validado sem depender de ação humana,
enquanto a execução manual é útil para revalidar o pipeline em situações específicas ou
testar configurações sem poluir o histórico de commits.
