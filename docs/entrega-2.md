# Carmesim — Entrega 2: diagramas de classes, atividades e pacotes

**Referência única:** Marco Tulio Valente, *Engenharia de Software Moderna*, Cap. 4 (Modelos), seções 4.3 (Diagrama de Classes), 4.4 (Diagrama de Pacotes) e 4.6 (Diagrama de Atividades). Disponível em https://engsoftmoderna.info/cap4.html. O livro adota a UML de *UML Distilled* (Fowler, 3ª ed.) e a usa como **esboço** (*sketch*), não como projeto detalhado.

| Diagrama | PNG | Fonte | Código correspondente |
|---|---|---|---|
| Classes | `diagramas/classes.png` | `diagramas/classes.puml` | `src/main/java/br/edu/ifpi/carmesim/dominio/` (12 classes + 6 enumerações) |
| Atividades (UC11) | `diagramas/atividades-uc11.png` | `diagramas/atividades-uc11.puml` | `Sessao.encerrar(...)`, `Sessao.avaliacoesLiberadas()`, `Personagem.ganharXp(...)` |
| Pacotes | `diagramas/pacotes.png` | `diagramas/pacotes.puml` | Pacotes `dominio`, `persistencia`, `seguranca`, `servico`, `ui` (cada um com `package-info.java`) |

## 1. Diagrama de classes (Cap. 4.3)

**Regras do livro aplicadas**

| Regra do livro | Como foi aplicada |
|---|---|
| Classe = retângulo com 3 compartimentos (nome, atributos, métodos) | Todas as 12 classes |
| Visibilidade `-` (privado) e `+` (público), com tipos dos atributos e parâmetros e tipo de retorno dos métodos | Atributos privados, métodos públicos, com tipos |
| Associação: seta de A para B quando A tem um atributo do tipo B; o nome do atributo fica na ponta da seta | Ex.: `Campanha → * sessoes → Sessao`. Atributos que são referências a outras classes **não** aparecem no compartimento de atributos, só como seta |
| Multiplicidade `1`, `0..1` e `*` | Usadas em todas as associações |
| Dependência: seta tracejada quando A usa B sem atributo nem herança, com `<<call>>` ou `<<create>>` | `Sessao ⇢ Personagem`, `Missao ⇢ Personagem` e `Usuario ⇢ Avaliacao`, todas `<<call>>` |
| Composição e agregação: o livro recomenda não usar | Não usadas |
| Esboço: omissões que não prejudicam o propósito são toleradas | Getters e setters omitidos do diagrama (existem no código) |

**Rastreabilidade (classe → casos de uso e regras)**

| Classe | Casos de uso | Regras de negócio no código |
|---|---|---|
| Usuario | UC01, UC02, UC03, UC18 | RN07 (`calcularReputacao`) |
| Personagem | UC04, UC05, UC13 | RN04 (`ganharXp`) |
| Campanha | UC06, UC07 | RN01 (`temVaga`) |
| Participacao | UC07, UC08 | |
| Sessao | UC09, UC10, UC11, UC18 | RN03 (`atualizarQuorum`), RN04 (`encerrar`); ciclo de vida: Agendada, Confirmada, Em andamento, Encerrada, Cancelada |
| Presenca | UC10, UC11 | |
| Missao | UC12, UC13, UC14 | RN05 (`dividirOuro`); ciclo de vida: Publicada, Aceita, Em andamento, Concluída, Falhada |
| ParticipacaoMissao | UC13, UC14 | |
| Publicacao | UC15, UC16, UC17 | RN06 (`denunciar`) |
| Comentario | UC15 | |
| Denuncia | UC16, UC17 | |
| Avaliacao | UC18 | RN07 (`validar`) |

## 2. Diagrama de atividades (Cap. 4.6): UC11 Encerrar sessão e distribuir XP

O livro descreve um processo em alto nível, com ficha (*token*) percorrendo os nodos. Elementos usados:

| Nodo do livro | No diagrama |
|---|---|
| Inicial e final | Início e fim do processo |
| Ações | Iniciar sessão, Encerrar sessão, Marcar presentes, Informar XP e resumo, Distribuir XP, Recalcular níveis, Liberar avaliações, Marcar sessão como Encerrada |
| Decisão (com guardas booleanas) | "Há presentes?" ([sim]/[não]) e "XP maior que zero?" ([sim]/[não]) |
| Merge | Une o retorno do laço de XP inválido e une os dois caminhos de "Há presentes?" |
| Fork e join | Em paralelo: (Distribuir XP, Recalcular níveis) e (Liberar avaliações). As duas partes são independentes, então a concorrência é real |

Origem: UC11, US09, RN04.

## 3. Diagrama de pacotes (Cap. 4.4)

Regras do livro: o retângulo de pacote mostra só o nome; há um único tipo de seta, tracejada, para qualquer relacionamento; a dependência existe mesmo que só uma classe dependa de outra.

| Pacote | Responsabilidade | Depende de |
|---|---|---|
| ui | Telas JavaFX | servico, seguranca, dominio |
| servico | Regras de negócio e casos de uso | seguranca, persistencia, dominio |
| seguranca | Autenticação, hash de senha, perfis (UC01) | dominio |
| persistencia | JDBC e SQLite (conexão e DAOs) | dominio |
| dominio | Classes de domínio e enumerações | nenhum |

Nenhum pacote depende de `ui`, e `dominio` não depende de ninguém.

## 4. Decisões para a equipe revisar

1. **Atividades só de UC11.** Escolhi o caso de uso com mais decisões e concorrência. UC14 (concluir missão) seria o segundo candidato.
2. **Sem herança.** O livro apresenta herança, mas o modelo não tem uma generalização natural; forçar uma só para aparecer no diagrama seria artificial.
3. **Enumerações fora do diagrama.** Os status são enums no código e só aparecem na nota do diagrama, para manter as 12 classes de domínio dentro do limite do PDF.
4. **Associações unidirecionais.** Segui o livro: seta só onde há atributo. Se uma consulta precisar navegar no sentido inverso, a associação vira bidirecional e o diagrama é atualizado primeiro.
5. **Valores das regras (100 XP por nível, 3 denúncias, resto do ouro).** São propostas ajustáveis, definidas em `Personagem.ganharXp`, `Publicacao.denunciar` e `Missao.dividirOuro`.
6. **Código desta entrega:** só o pacote `dominio`. Os demais pacotes têm apenas `package-info.java`; entram com as próximas entregas.
