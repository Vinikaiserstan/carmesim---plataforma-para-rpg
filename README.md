<p align="center"><img src="docs/img/logo.png" alt="Logo do Carmesim" width="160"></p>

# Carmesim

**Plataforma de suporte a comunidades de RPG de mesa.** O sistema organiza campanhas, sessões, missões e personagens, e oferece um mural social leve (publicações, comentários, denúncias e reputação) para a comunidade.

**Disciplina:** Análise e Projeto de Sistemas · Instituto Federal do Piauí

## Integrantes
- vinicius neiva teixeira

## Tecnologias
Java 21 · JavaFX · SQLite (sqlite-jdbc) · JDBC · Maven

## Como executar
Pré-requisitos: JDK 21 e Maven.

```bash
git clone <url-do-repositorio>
cd carmesim
mvn javafx:run
```

Na semana 1 o sistema abre apenas uma tela vazia. A criação automática do banco SQLite (`carmesim.db`) na primeira execução será implementada junto com as primeiras entregas de código.

## Documentação
- [Casos de uso](docs/casos-de-uso.md)
- [Histórias de usuário (Product Backlog)](docs/historias-de-usuario.md)
- Diagrama: [PNG](docs/diagramas/casos-de-uso.png) · [fonte PlantUML](docs/diagramas/casos-de-uso.puml)
- [Entrega 2: classes, atividades e pacotes](docs/entrega-2.md)
  - Classes: [PNG](docs/diagramas/classes.png) · [fonte](docs/diagramas/classes.puml)
  - Atividades (UC11): [PNG](docs/diagramas/atividades-uc11.png) · [fonte](docs/diagramas/atividades-uc11.puml)
  - Pacotes: [PNG](docs/diagramas/pacotes.png) · [fonte](docs/diagramas/pacotes.puml)
