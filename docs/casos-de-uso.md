# Carmesim — Documento de Casos de Uso

## 1. Visão geral do sistema
O **Carmesim** é uma plataforma desktop de suporte a comunidades de RPG de mesa. Mestres criam campanhas, convidam jogadores, agendam sessões, publicam missões e distribuem XP e recompensas; jogadores mantêm seus personagens, respondem a convites, confirmam presença e aceitam missões. Um mural social leve permite publicar e comentar, com denúncias analisadas por moderadores, e as avaliações após cada sessão formam a reputação de mestres e jogadores. Administradores gerenciam usuários e perfis. Os dados ficam em um banco SQLite local, criado na primeira execução.

## 2. Atores

| Ator | Descrição |
|---|---|
| **Jogador** | Participante da comunidade. Registra a conta, cria personagens, responde a convites, confirma presença em sessões, aceita missões, publica no mural, denuncia conteúdo e avalia os colegas. |
| **Mestre** | Jogador com o perfil de mestre. Cria campanhas, convida jogadores, revisa personagens, agenda e encerra sessões, publica e conclui missões. |
| **Moderador** | Analisa denúncias de publicações e decide pela manutenção ou remoção do conteúdo. |
| **Administrador** | Gerencia usuários e perfis de acesso. |

> Os perfis são papéis: um mesmo usuário pode acumular **Jogador** e **Mestre**. Moderador e Administrador são atribuídos pelo administrador.

## 3. Lista de casos de uso

| ID | Nome | Ator principal | Tipo | Histórias de origem |
|---|---|---|---|---|
| UC01 | Autenticar usuário | Jogador, Mestre, Moderador, Administrador | Não-CRUD | US01 |
| UC02 | Registrar conta | Jogador | CRUD | US02 |
| UC03 | Gerenciar usuários e perfis | Administrador | CRUD | US03 |
| UC04 | Gerenciar personagem | Jogador | CRUD | US10 |
| UC05 | Revisar personagem | Mestre | Não-CRUD | US11 |
| UC06 | Gerenciar campanha | Mestre | CRUD | US04 |
| UC07 | Convidar jogador para campanha | Mestre | Não-CRUD | US05 |
| UC08 | Responder convite de campanha | Jogador | Não-CRUD | US06 |
| UC09 | Agendar sessão | Mestre | Não-CRUD | US07 |
| UC10 | Confirmar presença em sessão | Jogador | Não-CRUD | US08 |
| UC11 | Encerrar sessão e distribuir XP | Mestre | Não-CRUD | US09 |
| UC12 | Publicar missão | Mestre | Não-CRUD | US12 |
| UC13 | Aceitar missão | Jogador | Não-CRUD | US13 |
| UC14 | Concluir missão e distribuir recompensa | Mestre | Não-CRUD | US14 |
| UC15 | Participar do mural (publicar e comentar) | Jogador (também Mestre) | CRUD | US15 |
| UC16 | Denunciar publicação | Jogador | Não-CRUD | US16 |
| UC17 | Moderar denúncia | Moderador | Não-CRUD | US17 |
| UC18 | Avaliar participantes da sessão | Jogador (também Mestre) | Não-CRUD | US18 |

**Relação entre casos de uso:** UC16 `<<extend>>` UC15. Ao navegar no mural, o jogador pode, opcionalmente, denunciar uma publicação. Não há `<<include>>` forçado: o login (UC01) é pré-condição dos demais e não é desenhado como inclusão.

## 4. Conferência dos requisitos mínimos

| Dimensão | Exigido | Carmesim |
|---|---|---|
| Atores | 3 a 6 | 4 |
| Casos de uso | 12 a 20 | 18 |
| CRUD puro | no máximo metade (9) | 5 (UC02, UC03, UC04, UC06, UC15) |
| Classes de domínio | 9 a 13 | 12: Usuario, Personagem, Campanha, Participacao, Sessao, Presenca, Missao, ParticipacaoMissao, Publicacao, Comentario, Denuncia, Avaliacao |
| Entidade com ciclo de vida (≥ 4 estados) | 1 a 2 | **Sessao:** Agendada → Confirmada → Em andamento → Encerrada (e Cancelada). **Missao:** Publicada → Aceita → Em andamento → Concluída (ou Falhada) |
| Regras de negócio (≥ 2) | 2 | 7 (abaixo) |
| Autenticação com perfis | obrigatória | UC01 + 4 perfis |

## 5. Regras de negócio

| ID | Regra | Tipo |
|---|---|---|
| RN01 | A campanha tem limite de vagas; o convite só pode ser aceito se houver vaga. | Validação |
| RN02 | Um usuário não pode ter duas sessões confirmadas com horários sobrepostos. | Validação |
| RN03 | A sessão passa a **Confirmada** quando atinge o número mínimo de presenças definido na campanha. | Validação |
| RN04 | O XP da sessão vai apenas aos presentes; o personagem sobe 1 nível a cada 100 XP acumulados (*valor proposto, ajustável*). | Cálculo |
| RN05 | O ouro da missão é dividido igualmente entre os personagens participantes; o resto da divisão fica com quem aceitou a missão primeiro (*proposta, ajustável*). | Cálculo |
| RN06 | Publicação denunciada por 3 usuários distintos fica oculta até a análise do moderador. | Validação |
| RN07 | Só participantes presentes em sessão encerrada podem avaliar, uma vez por avaliado; a reputação é a média das notas (1 a 5). | Validação e cálculo |

## 6. Especificações dos casos de uso (modelo livro didático)

> O formato abaixo segue o molde clássico (ator, pré e pós-condições, fluxo principal, fluxos alternativos). Confira com o gabarito mostrado em sala e ajuste os campos se for diferente.

### UC01 — Autenticar usuário
- **Ator principal:** Jogador, Mestre, Moderador, Administrador
- **Pré-condição:** usuário cadastrado e ativo.
- **Pós-condição:** sessão iniciada; funções liberadas conforme os perfis do usuário.
- **Origem:** US01

**Fluxo principal**
1. O usuário abre o sistema; o sistema exibe a tela de login.
2. O usuário informa login e senha.
3. O sistema valida as credenciais.
4. O sistema abre a tela inicial com as funções dos perfis do usuário.

**Fluxos alternativos**
- 3a. Credenciais inválidas: o sistema informa o erro e retorna ao passo 2; após 3 tentativas seguidas, bloqueia por 1 minuto.
- 3b. Usuário desativado: o sistema informa que o acesso está suspenso.

### UC02 — Registrar conta
- **Ator principal:** Jogador (visitante)
- **Pré-condição:** nenhuma.
- **Pós-condição:** conta criada com o perfil Jogador.
- **Origem:** US02

**Fluxo principal**
1. O visitante escolhe "Criar conta".
2. Informa nome, login, e-mail e senha.
3. O sistema valida os dados e grava a conta com a senha armazenada em hash.
4. O sistema confirma o cadastro e volta ao login.

**Fluxos alternativos**
- 3a. Login ou e-mail já em uso: o sistema informa e retorna ao passo 2.
- 3b. Senha fraca (menos de 8 caracteres): o sistema rejeita e orienta.

### UC03 — Gerenciar usuários e perfis
- **Ator principal:** Administrador
- **Pré-condição:** administrador autenticado.
- **Pós-condição:** usuário criado, alterado, ativado ou desativado; perfis atualizados.
- **Origem:** US03

**Fluxo principal**
1. O administrador abre "Usuários"; o sistema lista os usuários.
2. Seleciona um usuário (ou "Novo").
3. Altera dados, perfis (Jogador, Mestre, Moderador, Administrador) ou o status ativo.
4. Confirma; o sistema grava e atualiza a lista.

**Fluxos alternativos**
- 3a. Tentativa de remover o próprio perfil de administrador ou desativar a si mesmo: o sistema impede.

### UC04 — Gerenciar personagem
- **Ator principal:** Jogador
- **Pré-condição:** jogador autenticado.
- **Pós-condição:** personagem criado, editado ou excluído; novo personagem fica com status **Rascunho**.
- **Origem:** US10

**Fluxo principal**
1. O jogador abre "Meus personagens".
2. Escolhe "Novo" ou seleciona um existente.
3. Informa nome, classe, nível inicial, atributos e história.
4. Salva; o sistema grava com status Rascunho.
5. Quando pronto, o jogador envia para revisão de um mestre (status **Em revisão**).

**Fluxos alternativos**
- 3a. Dados obrigatórios ausentes: o sistema destaca os campos.
- 4a. Personagem em campanha ou missão ativa: o sistema bloqueia a exclusão.

### UC05 — Revisar personagem
- **Ator principal:** Mestre
- **Pré-condição:** personagem **Em revisão**, vinculado a uma campanha do mestre.
- **Pós-condição:** personagem **Aprovado** ou devolvido a **Rascunho** com observação.
- **Origem:** US11

**Fluxo principal**
1. O mestre abre a lista de personagens aguardando revisão.
2. Seleciona um personagem e examina a ficha.
3. Aprova.
4. O sistema altera o status para Aprovado.

**Fluxos alternativos**
- 3a. Recusa: o mestre registra o motivo; o sistema devolve o personagem a Rascunho.

### UC06 — Gerenciar campanha
- **Ator principal:** Mestre
- **Pré-condição:** mestre autenticado.
- **Pós-condição:** campanha criada, editada, arquivada ou excluída.
- **Origem:** US04

**Fluxo principal**
1. O mestre abre "Minhas campanhas".
2. Escolhe "Nova campanha".
3. Informa nome, descrição, limite de vagas e mínimo de presenças para confirmar sessão.
4. Salva; o sistema grava a campanha com o mestre como responsável.

**Fluxos alternativos**
- 3a. Limite de vagas menor que o número de jogadores já participantes: o sistema rejeita (RN01).
- 4a. Exclusão de campanha com sessões encerradas: o sistema oferece arquivar em vez de excluir.

### UC07 — Convidar jogador para campanha
- **Ator principal:** Mestre
- **Pré-condição:** campanha existente do mestre.
- **Pós-condição:** convite registrado com status **Pendente**.
- **Origem:** US05

**Fluxo principal**
1. O mestre abre a campanha e escolhe "Convidar jogador".
2. Busca o jogador pelo nome ou login.
3. O sistema verifica as vagas (RN01) e que o jogador ainda não participa.
4. O mestre confirma; o sistema registra o convite.

**Fluxos alternativos**
- 3a. Sem vagas: o sistema informa e impede o convite.
- 3b. Jogador já participa ou já tem convite pendente: o sistema informa.

### UC08 — Responder convite de campanha
- **Ator principal:** Jogador
- **Pré-condição:** convite **Pendente** para o jogador.
- **Pós-condição:** convite **Aceito** (jogador passa a participar) ou **Recusado**.
- **Origem:** US06

**Fluxo principal**
1. O jogador abre "Convites".
2. Seleciona um convite e visualiza os dados da campanha.
3. Aceita e escolhe um personagem aprovado (ou informa que usará um novo).
4. O sistema revalida a vaga (RN01), registra a participação e marca o convite como Aceito.

**Fluxos alternativos**
- 3a. Recusa: o sistema marca o convite como Recusado.
- 4a. A vaga foi preenchida enquanto isso: o sistema informa que o convite expirou.

### UC09 — Agendar sessão
- **Ator principal:** Mestre
- **Pré-condição:** campanha com ao menos um jogador participante.
- **Pós-condição:** sessão criada com status **Agendada**; participantes notificados no sistema.
- **Origem:** US07

**Fluxo principal**
1. O mestre abre a campanha e escolhe "Agendar sessão".
2. Informa título, data, horário de início e duração.
3. O sistema verifica conflito de horário do mestre (RN02).
4. O mestre confirma; o sistema grava a sessão como Agendada.

**Fluxos alternativos**
- 3a. Conflito com outra sessão do mestre: o sistema informa e retorna ao passo 2.
- 2a. Data no passado: o sistema rejeita.
- 4a. O mestre cancela uma sessão já agendada: o sistema muda o status para Cancelada e avisa os participantes.

### UC10 — Confirmar presença em sessão
- **Ator principal:** Jogador
- **Pré-condição:** jogador participante da campanha; sessão **Agendada** ou **Confirmada**.
- **Pós-condição:** presença do jogador registrada como confirmada; sessão pode passar a **Confirmada** (RN03).
- **Origem:** US08

**Fluxo principal**
1. O jogador abre "Minhas sessões" e seleciona a sessão.
2. Escolhe "Confirmar presença".
3. O sistema verifica conflito de horário do jogador (RN02).
4. O sistema registra a presença e, se o mínimo foi atingido, muda a sessão para Confirmada (RN03).

**Fluxos alternativos**
- 3a. Conflito de horário: o sistema informa e não registra.
- 2a. Recusar presença: o sistema registra "não vai" e mantém a sessão no status atual.

### UC11 — Encerrar sessão e distribuir XP
- **Ator principal:** Mestre
- **Pré-condição:** sessão **Confirmada** (ou **Em andamento**) da campanha do mestre.
- **Pós-condição:** sessão **Encerrada**; presenças reais e XP gravados; níveis dos personagens atualizados.
- **Origem:** US09

**Fluxo principal**
1. O mestre abre a sessão e escolhe "Iniciar"; o sistema muda o status para Em andamento.
2. Ao final, escolhe "Encerrar sessão".
3. O sistema lista os participantes; o mestre marca quem esteve presente.
4. O mestre informa o XP da sessão e um resumo.
5. O sistema distribui o XP aos presentes e recalcula os níveis (RN04).
6. O sistema muda o status para Encerrada e libera as avaliações (UC18).

**Fluxos alternativos**
- 4a. XP negativo ou nulo: o sistema rejeita.
- 3a. Nenhum presente marcado: o sistema pede confirmação e encerra sem XP.

### UC12 — Publicar missão
- **Ator principal:** Mestre
- **Pré-condição:** campanha do mestre existente.
- **Pós-condição:** missão com status **Publicada**.
- **Origem:** US12

**Fluxo principal**
1. O mestre abre a campanha e escolhe "Nova missão".
2. Informa título, descrição, dificuldade, recompensa em ouro e número máximo de personagens.
3. O sistema valida os dados.
4. O mestre publica; o sistema grava a missão como Publicada.

**Fluxos alternativos**
- 3a. Recompensa negativa ou máximo de personagens menor que 1: o sistema rejeita.

### UC13 — Aceitar missão
- **Ator principal:** Jogador
- **Pré-condição:** missão **Publicada** ou **Aceita** com vagas; jogador com personagem **Aprovado** na campanha.
- **Pós-condição:** personagem vinculado à missão; status **Aceita** (ou **Em andamento** quando o mestre a iniciar).
- **Origem:** US13

**Fluxo principal**
1. O jogador abre as missões da campanha.
2. Seleciona uma missão e escolhe "Aceitar".
3. Escolhe o personagem aprovado.
4. O sistema verifica o limite de personagens e registra a participação.

**Fluxos alternativos**
- 3a. Nenhum personagem aprovado: o sistema orienta a usar UC04.
- 4a. Missão lotada: o sistema informa.
- 4b. O mestre inicia a missão (status Em andamento): não aceita novas entradas.

### UC14 — Concluir missão e distribuir recompensa
- **Ator principal:** Mestre
- **Pré-condição:** missão **Em andamento** da campanha do mestre.
- **Pós-condição:** missão **Concluída** (ou **Falhada**); ouro creditado aos personagens.
- **Origem:** US14

**Fluxo principal**
1. O mestre abre a missão e escolhe "Concluir".
2. Informa o resultado: sucesso.
3. O sistema divide o ouro entre os personagens participantes (RN05).
4. O sistema credita o ouro, grava o histórico e muda o status para Concluída.

**Fluxos alternativos**
- 2a. Resultado: fracasso. O sistema muda o status para Falhada e não credita ouro.
- 3a. Nenhum personagem participante: o sistema impede a conclusão.

### UC15 — Participar do mural (publicar e comentar)
- **Ator principal:** Jogador (também Mestre)
- **Pré-condição:** usuário autenticado.
- **Pós-condição:** publicação ou comentário gravado e visível no mural.
- **Origem:** US15

**Fluxo principal**
1. O usuário abre o mural; o sistema lista as publicações visíveis.
2. Escolhe "Nova publicação", escreve o texto e confirma.
3. O sistema grava a publicação.
4. Outro usuário abre uma publicação, escreve um comentário e confirma; o sistema grava.

**Fluxos alternativos**
- 2a. Texto vazio ou acima de 1000 caracteres: o sistema rejeita.
- 4a. Publicação oculta (RN06): não aceita novos comentários.
- 1a. O usuário pode denunciar uma publicação (UC16, `<<extend>>`).

### UC16 — Denunciar publicação
- **Ator principal:** Jogador
- **Pré-condição:** publicação visível de outro usuário.
- **Pós-condição:** denúncia registrada com status **Aberta**; a publicação é ocultada se atingir 3 denúncias distintas (RN06).
- **Origem:** US16

**Fluxo principal**
1. Ao ver uma publicação, o jogador escolhe "Denunciar".
2. Seleciona o motivo e, opcionalmente, escreve um detalhe.
3. O sistema verifica que o jogador ainda não denunciou aquela publicação.
4. O sistema registra a denúncia e aplica a regra RN06.

**Fluxos alternativos**
- 3a. Denúncia repetida do mesmo usuário: o sistema informa e não registra.
- 2a. Denunciar a própria publicação: o sistema impede.

### UC17 — Moderar denúncia
- **Ator principal:** Moderador
- **Pré-condição:** denúncias com status **Aberta** existentes.
- **Pós-condição:** denúncias **Procedentes** ou **Improcedentes**; publicação removida ou reexibida.
- **Origem:** US17

**Fluxo principal**
1. O moderador abre a fila de denúncias.
2. Seleciona uma publicação denunciada e vê o conteúdo, os motivos e o autor.
3. Decide pela **remoção**.
4. O sistema marca as denúncias como Procedentes, remove a publicação e registra a decisão.

**Fluxos alternativos**
- 3a. Decide pela **manutenção**: o sistema marca as denúncias como Improcedentes e reexibe a publicação, se estava oculta.

### UC18 — Avaliar participantes da sessão
- **Ator principal:** Jogador (também Mestre)
- **Pré-condição:** sessão **Encerrada** em que o usuário esteve presente.
- **Pós-condição:** avaliações gravadas; reputação dos avaliados recalculada.
- **Origem:** US18

**Fluxo principal**
1. O usuário abre a sessão encerrada e escolhe "Avaliar".
2. O sistema lista os demais presentes.
3. O usuário atribui nota de 1 a 5 e, opcionalmente, um comentário a cada pessoa.
4. O sistema valida (RN07), grava as avaliações e recalcula a média de cada avaliado.

**Fluxos alternativos**
- 3a. Nota fora de 1 a 5: o sistema rejeita.
- 1a. Avaliação já enviada para aquele avaliado nesta sessão: o sistema não permite duplicar.
