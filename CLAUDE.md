# app-back — Backend do Schedule App

Contexto geral do produto em `../CLAUDE.md`. Este arquivo cobre convenções específicas do backend.

## Stack

Java 21, Spring Boot 3.5.5, Maven, PostgreSQL + Flyway (migrations), Spring Security com JWT stateless (`com.auth0:java-jwt`), Lombok, Bean Validation, login social via Google (`google-api-client`).

## Estrutura de pacotes (package-by-feature)

Tudo vive sob `com.softix.app_back.<feature>` — **não** por camada (não existe `controller/`, `service/`, `repository/` na raiz). Cada feature típica tem:

- `Entity.java` — entidade JPA
- `EntityDTO.java` — DTO de leitura/escrita (às vezes `Request`/`Response` separados quando o shape de entrada difere do de saída, ex.: `ClientRequest` vs `ClientResponse`)
- `EntityRepository.java` — Spring Data JPA
- `EntityService.java` — regra de negócio + orquestração + mapeamento DTO↔Entity
- `EntityController.java` — `@RestController`, fino, só delega pro Service

Sub-features ganham subpacote (ex.: `company/review/`, `company/public_api/`, `appointment/customer_appointment/`). Siga esse padrão ao criar uma feature nova — **não** crie pastas `controllers/`, `services/` etc.

Exceção conhecida: existe um pacote `utils` fora de `com.softix.app_back` (`utils.model`, `utils.model.tenant`, `utils.security`). É legado/inconsistente com o resto — não é o padrão a replicar, é só onde vivem hoje `RootEntity`, `TenantEntity` e `SecurityUtils`.

## Hierarquia de entidades — sempre seguir

```
RootEntity (id UUID, createdAt/updatedAt, createdByUserId/updatedByUserId, @Version)
  └── TenantEntity (+ companyId, Hibernate @Filter "tenantFilter")
        └── <Entidade de domínio> (Appointment, Client, Product, ...)
```

Toda entidade nova que pertence a uma empresa **deve** estender `TenantEntity`, não `RootEntity` direto (a menos que seja genuinamente global, como `Country`/`State`/`City`).

## Multi-tenancy — não reimplementar isso na mão

`TenantAspect` (AOP, `@Aspect` sobre todo `*Repository`/`*Service`/`*Controller` do pacote) habilita automaticamente o filtro Hibernate `tenantFilter` com o `companyId` do usuário autenticado (via `SecurityUtils.companyId()`). Isso significa que queries JPA normais já saem filtradas por empresa sem precisar passar `companyId` manualmente na maioria dos casos.

- Para um endpoint que precisa ignorar esse filtro (ex.: ações do `MASTER_ADMIN` que cruzam empresas, ou endpoints públicos), use a anotação `@IgnoreTenantFilter` — não desabilite o filtro manualmente em outro lugar.
- Quando algo *não* usa esse mecanismo (ex.: query customizada que já recebe `companyId` como parâmetro explícito, como em `AppointmentRepository`), há normalmente um motivo (resolver o `companyId` de forma diferente para o fluxo do cliente final vs. do admin) — olhe `SecurityUtils.resolveCompanyId(...)` antes de assumir que é redundante.

## Autenticação — Strategy pattern para login externo

`ExternalAuthStrategy` é a interface (`getProvider()` + `authenticate(credential)`); cada provedor (hoje só `GoogleAuthStrategy`) é um `@Component` que a implementa. `ExternalAuthStrategyResolver` injeta `List<ExternalAuthStrategy>` e monta um `Map<AuthProvider, ExternalAuthStrategy>` automaticamente — nenhum `if/else` de provedor em lugar nenhum.

**Para adicionar um novo provedor de login social** (Facebook, Apple, etc.):
1. Adicionar o valor no enum `AuthProvider`.
2. Criar `<Provider>AuthStrategy implements ExternalAuthStrategy` como `@Component` num subpacote `auth/external/<provider>/`.
3. Não tocar em `ExternalAuthStrategyResolver` — ele já descobre a nova strategy via injeção de lista.

Login local (usuário/senha) continua via `AuthController`/JWT próprio (`TokenConfig`), independente desse mecanismo de strategy.

## Convenções a manter

- Controllers **sem** lógica de negócio — só `@Valid @RequestBody` → chamar Service → devolver DTO. `@ResponseStatus` explícito para 201/204.
- Erros de negócio: `throw new ResponseStatusException(HttpStatus.X, "mensagem em PT-BR")` direto no Service.
- Paginação: sempre `Page<T>`/`Pageable` do Spring Data, nunca lista simples + contagem manual.
- Queries com múltiplos filtros opcionais: `@Query` JPQL com `:param IS NULL OR ...`, como em `AppointmentRepository.findAdvanced`.
- Mapeamento DTO↔Entity é manual, feito em métodos privados `toDTO`/`toXxxDTO` dentro do próprio Service.

## Pontos de melhoria identificados (referência para quando for tocar em cada área — não é uma lista de tarefas para executar de imediato)

1. **Injeção por campo (`@Autowired` em campo) em vez de por construtor.** Field injection dificulta testes (não dá para mockar sem reflection) e esconde dependências obrigatórias. Ao criar ou refatorar um Service/Controller, prefira injeção por construtor (`private final X x;` + construtor, ou `@RequiredArgsConstructor` do Lombok).
2. **Não há tratamento de erro centralizado.** Toda exceção de negócio é um `ResponseStatusException` com string literal solta no Service — não existe `@ControllerAdvice`/`@ExceptionHandler` global nem exceptions de domínio próprias. Isso funciona mas dificulta consistência de formato de erro e futura internacionalização. Quando crescer a superfície de erros, vale criar exceptions próprias (`BusinessException` etc.) + um `@ControllerAdvice` central.
3. **Services grandes fazem tudo:** validação + orquestração + mapeamento DTO na mesma classe (`AppointmentService` tem ~566 linhas). Ao adicionar lógica nova num Service já grande, considere extrair o mapeamento para uma classe `Mapper` dedicada (ou avaliar MapStruct) em vez de crescer ainda mais o Service.
4. **`@Data` do Lombok em entidades JPA** gera `equals`/`hashCode`/`toString` sobre todos os campos, incluindo relações `@ManyToOne` lazy — risco de `LazyInitializationException` em `toString()`/logs e de contratos de equals instáveis. Está parcialmente mitigado (`@EqualsAndHashCode(callSuper = true)` em algumas entidades), mas vale revisar caso apareçam bugs estranhos de coleções/comparação.
5. **Sem testes automatizados** além do boilerplate (`AppBackApplicationTests`). Ao implementar uma feature com regra de negócio não trivial (ex.: cálculo de disponibilidade/conflito de horário em `AppointmentService`), considere ao menos um teste de Service com Mockito — é justamente esse tipo de lógica (janelas de tempo, conflitos) que mais quebra silenciosamente.
6. **Pacote `utils` fora de `com.softix.app_back`.** Não é urgente mover, mas não replique esse padrão em código novo — novo código utilitário compartilhado deveria ir para `com.softix.app_back.shared` ou similar, mantendo o root package único.
