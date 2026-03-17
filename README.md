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
- `http://localhost:8080/calc/sqrt?x=16` ← novo (TP3)

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

1. **Push automático** — disparado ao fazer `git push` na branch `main`.
2. **Execução manual (Run workflow)** — disparada pela aba Actions com parâmetros `run_tests` e `run_lint`.

---

## 🚀 TP3 — Controle, Segurança e Personalização do Pipeline

### Etapa 1 — Runner Auto-Hospedado

**Arquivo:** `.github/workflows/self-hosted-demo.yml`

O runner foi registrado localmente via:
```bash
mkdir actions-runner && cd actions-runner
curl -o actions-runner-linux-x64-2.315.0.tar.gz -L \
  https://github.com/actions/runner/releases/download/v2.315.0/actions-runner-linux-x64-2.315.0.tar.gz
tar xzf ./actions-runner-linux-x64-2.315.0.tar.gz
./config.sh --url https://github.com/juliatlrc/devops --token <TOKEN>
./run.sh
```

O workflow usa `runs-on: self-hosted` e executa `uname -a`, `java -version`, `docker --version`.
Durante a execução, instala `jq` via `apt-get` como software adicional.

**Para reexecutar:** Actions > TP3 - Etapa 1 - Self-Hosted Runner > Run workflow

---

### Etapa 2 — Variáveis e Secrets

**Arquivo:** `.github/workflows/vars-and-secrets-demo.yml`

**Configurar em Settings > Secrets and variables > Actions:**
| Tipo | Nome | Valor |
|------|------|-------|
| Variable | `APP_MODE` | `production` |
| Variable | `SUPPORT_EMAIL` | `suporte@ricknmorty.dev` |
| Secret | `PROD_TOKEN` | `<seu-token>` |

- Variáveis acessadas via `${{ vars.APP_MODE }}` e `${{ vars.SUPPORT_EMAIL }}`
- Secret acessado via `${{ secrets.PROD_TOKEN }}` — valor mascarado nos logs

**Para reexecutar:** Actions > TP3 - Etapa 2 - Variaveis e Secrets > Run workflow

---

### Etapa 3 — Contextos e Escopos de Variáveis

**Arquivo:** `.github/workflows/env-context-demo.yml`

Demonstra os três níveis de escopo:
- **workflow-level:** `STAGE=test`, `APP_NAME=ricknmorty-api`
- **job-level:** `DEPLOY_ENV=staging`, `APP_NAME=ricknmorty-api-job-override` (sobrescreve)
- **step-level:** `STAGE=production` (sobrescreve), `STEP_ONLY=so-aqui` (local ao step)

Exibe `github.actor`, `github.ref`, `runner.os`, `runner.arch`.

**Para reexecutar:** Actions > TP3 - Etapa 3 - Env Context Demo > Run workflow

---

### Etapa 4 — Permissões do GITHUB_TOKEN

**Arquivo:** `.github/workflows/auto-issue.yml`

O bloco `permissions` eleva `issues: write` mantendo o restante no mínimo:
```yaml
permissions:
  issues: write
  contents: read
```

Se `APP_MODE` não estiver definida, o deploy falha e uma issue é criada automaticamente
via `actions/github-script` usando `${{ secrets.GITHUB_TOKEN }}`.

**Para reexecutar:** Actions > TP3 - Etapa 4 - Permissoes GITHUB_TOKEN > Run workflow

---

### Etapa 5 — Ambientes Dev e Prod

**Arquivos:** `.github/workflows/deploy-dev.yml` e `.github/workflows/deploy-prod.yml`

| Ambiente | Branch | Proteção | Variável |
|----------|--------|----------|----------|
| `dev` | `dev` | Nenhuma (automático) | `DEV_API_URL` |
| `prod` | `main` | Required reviewer (aprovação manual) | `PROD_API_URL` |

- Push em `dev` → deploy imediato
- Push em `main` → aguarda aprovação em Settings > Environments > prod

**Configurar em Settings > Environments:**
1. Criar ambiente `dev` com variável `DEV_API_URL`
2. Criar ambiente `prod` com variável `PROD_API_URL`, secret `PROD_REGISTRY_TOKEN` e reviewer obrigatório

---

### Etapa 6 — Novo Endpoint `/calc/sqrt`

**Arquivos alterados:**
- `api/src/main/java/com/ricknmorty/api/service/CalculatorService.java` — método `sqrt(double x)`
- `api/src/main/java/com/ricknmorty/api/controller/CalculatorController.java` — endpoint `GET /calc/sqrt`
- `api/src/test/java/com/ricknmorty/api/service/CalculatorServiceTest.java` — 7 novos testes

**Uso:**
```bash
curl "http://localhost:8080/calc/sqrt?x=16"
# {"x":16.0,"operation":"sqrt","result":4.0}

curl "http://localhost:8080/calc/sqrt?x=-1"
# HTTP 400 {"error":"Raiz quadrada de número negativo não é permitida..."}
```

O pipeline de CI existente executa os testes automaticamente em pushes na `main`.
