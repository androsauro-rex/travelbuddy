[README.md](https://github.com/user-attachments/files/32328677/README.md)
# ✈️ TravelBuddy

**TravelBuddy** è un'applicazione web full-stack per la pianificazione di viaggi in Europa: permette di creare itinerari strutturati per giorni e tappe, gestire il budget e le spese, e consultare/condividere i viaggi in base alla loro visibilità.

Progetto sviluppato durante il corso **Junior Java Developer** di **Generation Italy**, con metodologia Agile Scrum.

> ⚠️ **Stato del progetto**: in corso di sviluppo (work in progress). Alcune funzionalità descritte nella sezione [Roadmap](#-roadmap--funzionalità-future) non sono ancora implementate.

---

## 👥 Team

| Nome | Ruolo |
|---|---|
| Alessandro Fruci | Scrum Master & Backend |
| Mattia Arenzullo | Backend |
| Andrea Lamicela | Frontend |
| Lucia Antonina Gangemi | Frontend |

---

## 📖 Indice

- [Perché nasce TravelBuddy](#-perché-nasce-travelbuddy)
- [Funzionalità principali](#-funzionalità-principali)
- [Attori e ruoli](#-attori-e-ruoli)
- [Stack tecnologico](#-stack-tecnologico)
- [Architettura](#-architettura)
- [Modello dei dati](#-modello-dei-dati)
- [Struttura del progetto](#-struttura-del-progetto)
- [Autenticazione e sicurezza](#-autenticazione-e-sicurezza)
- [Come avviare il progetto](#-come-avviare-il-progetto)
- [API principali](#-api-principali)
- [Roadmap / funzionalità future](#-roadmap--funzionalità-future)
- [Cosa abbiamo imparato](#-cosa-abbiamo-imparato)

---

## 🎯 Perché nasce TravelBuddy

TravelBuddy nasce per aiutare gli utenti a organizzare e monitorare i propri viaggi, offrendo:

- pianificazione di un viaggio in modo semplice, ordinato e completo;
- tracciamento delle spese e dei luoghi visitati;
- conservazione di tutte le informazioni (itinerari, recensioni, foto) in un unico spazio digitale.

**Target**: chiunque desideri viaggiare in Europa in modo organizzato, con soggiorni strutturati sulla base di durata e budget prestabiliti.

---

## ✅ Funzionalità principali

- Registrazione e login con autenticazione **JWT**
- Creazione, modifica ed eliminazione di itinerari, con budget e tappe organizzate per giorno
- Calcolo delle spese pianificate ed extra per ogni itinerario
- Generazione di report in PDF dell'itinerario *(vedi nota nella roadmap)*
- Gestione completa dell'utente: creazione, aggiornamento parziale/totale, disattivazione/riattivazione, cancellazione account
- Gestione ruoli e moderazione (ban utenti, controllo contenuti)

---

## 🧑‍🤝‍🧑 Attori e ruoli

L'applicazione definisce 4 ruoli, con autorizzazioni gestite tramite Spring Security:

| Ruolo | Permessi |
|---|---|
| **Guest** | Visualizza solo gli itinerari pubblici. Non autenticato. |
| **User** | Crea e gestisce i propri itinerari (pubblici o privati). |
| **Moderator** | Controllo dei contenuti, modifica/rimozione di recensioni e foto. |
| **Admin** | Gestione totale del sistema: ban utenti, cambio ruoli. |

Gli endpoint sono organizzati per prefisso in base al livello di accesso richiesto (`/public/**`, `/guest/**`, `/user/**`, `/mod-content/**`, `/admin/**`, `/common/**` per le rotte condivise tra più ruoli autenticati).

---

## 🛠 Stack tecnologico

**Backend**
- Java 25
- Spring Boot (spring-boot-starter-parent 4.0.6)
- Spring Web (MVC)
- Spring Data JPA
- Spring Security + JWT (libreria `jjwt`)
- Bean Validation (`spring-boot-starter-validation`)
- ModelMapper (mapping tra Entity e DTO)
- Lombok

**Database**
- MySQL (driver `mysql-connector-j`), tramite Spring Data JPA / Hibernate

**Frontend**
- HTML5, CSS3, JavaScript
- Bootstrap 5
- Comunicazione con il backend tramite Fetch API REST (JSON)

**Strumenti**
- Maven (con Maven Wrapper incluso)
- Git / GitHub
- Metodologia Agile Scrum

---

## 🏗 Architettura

Il progetto segue un'architettura a 3 livelli, con il backend organizzato secondo il pattern **Controller → Service → Repository**:

```
Client (HTML/CSS/JS + Bootstrap)
        │  REST / JSON
        ▼
Backend (Spring Boot)
  ├─ Controller   → espone le API REST
  ├─ Service      → logica applicativa
  └─ Repository   → persistenza dati (Spring Data JPA)
        │
        ▼
Database MySQL
```

Spring Security, con un filtro JWT custom, protegge gli endpoint verificando token e ruoli prima che la richiesta raggiunga i controller.

---

## 🗂 Modello dei dati

Entità principali e relazioni (diagramma E-R disponibile nella presentazione del progetto):

```
Utente(id, ruolo, nickname, nome, cognome, eta, email, password, status)
Itinerario(id, titoloViaggio, like, dataInizioViaggio, dataFineViaggio, visibilita, idUtente*, idSpesa*)
Giorno(id, data, descrizioneAttivitaGiorno, idItinerario*)
Tappa(id, nome, recensione, idGiorno*)
Spesa(id, tipologia, costo, descrizione)
Destinazione(id, nome, idItinerario*)
```

- Un **Utente** organizza più **Itinerari**.
- Un **Itinerario** è composto da più **Giorni**, ha più **Destinazioni** e può includere più **Spese**.
- Ogni **Giorno** ha una o più **Tappe**.
- Ogni **Itinerario** ha una visibilità (`pubblico` / `privato`) e un contatore di like.

---

## 📁 Struttura del progetto

```
src/main/java/com/travelbuddy/
├── config/            # configurazione applicativa (es. ModelMapper bean)
├── controller/         # ItinerarioController, SpesaController, UtenteController
├── dto/                 # DTO di request/response per ogni entità
├── entity/              # Utente, Itinerario, Giorno, Tappa, Spesa, Destinazione
├── exception/            # eccezioni custom + GlobalExceptionHandler
├── listaenum/             # enum di dominio (ruoli, status, visibilità, tipologia spesa)
├── repository/             # interfacce Spring Data JPA
├── security/               # config Spring Security, filtro JWT, JwtService
└── service/                 # interfacce + implementazioni della logica di business
```

### Repository (data access layer)

Tutte le interfacce estendono `JpaRepository` e usano query derivate dal nome del metodo o query JPQL esplicite quando la derivazione non basta:

- **`UtenteRepository`** — ricerca per nome/cognome/nickname/email (case-insensitive), lookup di ruolo e status per id, controlli di unicità (`existsByNicknameAndIdNot`, `existsByEmailAndIdNot`) usati per validare gli aggiornamenti profilo senza collisioni.
- **`ItinerarioRepository`** — ricerca per titolo (esatta e "contains"), ricerca per nome destinazione, top 10 itinerari per like, lookup della sola visibilità per id (proiezione leggera, evita di caricare l'intera entità).
- **`GiornoRepository`** — recupero dei giorni di un itinerario.
- **`TappaRepository`** — tappe per giorno, e giorno/i in cui è stata effettuata una tappa.
- **`SpesaRepository`** — spese collegate a un itinerario.
- **`DestinazioneRepository`** — destinazioni di un itinerario.

Alcune query sono accompagnate da commenti che spiegano scelte progettuali (es. perché una ricerca per titolo non deduplicata sia stata scartata a favore di `distinct`, o perché un `JOIN` esplicito non sia necessario in JPQL) — documentazione utile per chi legge il codice per la prima volta.

### Exception handling

Le eccezioni custom (`BadRequestException`, `ResourceNotFoundException`, `NotFoundException`, `DuplicatedResourceException`, `UserAlreadyExistsException`) sono centralizzate in un `GlobalExceptionHandler` (`@ControllerAdvice`) che restituisce risposte di errore coerenti tramite `ApiErrorDto`.

---

## 🔐 Autenticazione e sicurezza

L'autenticazione è basata su **JWT stateless**, gestita da:

- `SecurityConfig` — definisce la `SecurityFilterChain`, disabilita CSRF (API stateless), configura CORS e le regole di autorizzazione per prefisso di URL, imposta la sessione come `STATELESS`.
- `JwtAuthenticationFilter` — filtro custom eseguito prima del filtro standard di autenticazione, valida il token e popola il contesto di sicurezza.
- `JwtService` — generazione e validazione dei token JWT.

La logica di autenticazione (endpoint `/api/v1/auth/register` e `/api/v1/auth/login`) è stata prototipata in un **progetto separato** ("Auth"), usato come base di riferimento per implementare login, registrazione, gestione ruoli JWT e permessi sulle API integrati poi in TravelBuddy.

> 🔒 **Nota di sicurezza**: la chiave `jwt.secret` in `application.properties` va gestita tramite variabile d'ambiente (o file escluso da Git) e mai committata in chiaro in un repository pubblico.

---

## ▶️ Come avviare il progetto

### Prerequisiti
- JDK 25
- Maven (oppure usare il wrapper incluso, `./mvnw`)
- MySQL in esecuzione su `localhost:3306`

### 1. Clona il repository
```bash
git clone <url-del-repository>
cd travelbuddy
```

### 2. Configura il database
Crea un database MySQL chiamato `travelbuddy` e imposta le credenziali in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/travelbuddy?useSSL=false&serverTimezone=UTC
spring.datasource.username=<tuo-utente>
spring.datasource.password=<tua-password>
jwt.secret=<una-chiave-segreta-generata-da-te>
```

Lo schema viene creato/aggiornato automaticamente all'avvio (`spring.jpa.hibernate.ddl-auto=update`).

### 3. Avvia l'applicazione
```bash
./mvnw spring-boot:run
```

L'API sarà disponibile su `http://localhost:8080`.

---

## 🔌 API principali

Base path: `/api/v1`

### Utenti (`UtenteController`)
| Metodo | Endpoint | Descrizione |
|---|---|---|
| `POST` | `/public/registrazione` | Registrazione di un nuovo utente |
| `GET` | `/public/nickname/{nickname}` | Ricerca utente per nickname |
| `GET` | `/admin/utenti` | Elenco di tutti gli utenti *(admin)* |
| `GET` | `/admin/utenti/{id}` | Dettaglio utente per id *(admin)* |
| `GET` | `/mod-content/email/{email}` | Ricerca utente per email *(moderator/admin)* |
| `PUT` | `/common/utenti/replace/{id}` | Sostituzione completa dei dati utente |
| `PATCH` | `/common/utenti/update/{id}` | Aggiornamento parziale dei dati utente |
| `PATCH` | `/common/disattivazione/account/{id}` | Disattivazione account |
| `PATCH` | `/common/riattivazione/account/{id}` | Riattivazione account |
| `PATCH` | `/admin/banAccount/{id}` | Ban di un account *(admin)* |
| `DELETE` | `/common/delete/account/{id}` | Cancellazione account |

### Itinerari (`ItinerarioController`)
| Metodo | Endpoint | Descrizione |
|---|---|---|
| `GET` | `/common/itinerari/{id}` | Dettaglio itinerario |
| `POST` | `/user/creazione/itinerario` | Creazione itinerario (con destinazioni) |
| `POST` | `/user/creazione/itinerario/con/giorni` | Creazione itinerario completo di giorni |
| `PUT` | `/user/modifica/itinerario/{idItinerario}` | Modifica itinerario |
| `DELETE` | `/user/itinerario/{idItinerario}` | Eliminazione itinerario |

### Spese (`SpesaController`)
| Metodo | Endpoint | Descrizione |
|---|---|---|
| `GET` | `/common/vedi/spese/itinerario/{id}` | Elenco spese di un itinerario |
| `GET` | `/common/vedi/spesa/{id}` | Dettaglio singola spesa |
| `POST` | `/common/aggiungi/spesa` | Aggiunta di una spesa |
| `DELETE` | `/common/elimina/spesa/{id}` | Eliminazione di una spesa |

### Autenticazione (progetto Auth)
| Metodo | Endpoint | Descrizione |
|---|---|---|
| `POST` | `/api/v1/auth/register` | Registrazione con emissione token |
| `POST` | `/api/v1/auth/login` | Login con emissione token JWT |

---

## 🚧 Roadmap / funzionalità future

- [ ] Modifica profilo utente con bio e foto
- [ ] Like e salvataggio degli itinerari preferiti
- [ ] Ricerca itinerario per luogo
- [ ] Segnalazione o ban di un utente (workflow completo di moderazione)
- [ ] Gestione avanzata delle spese con alert sul superamento del budget
- [ ] Aggiornamento/cancellazione di una recensione

---

## 🎓 Cosa abbiamo imparato

**Frontend**: HTML5, CSS3, JavaScript, Bootstrap 5
**Backend**: Java, Spring Boot, Spring Security, Spring Data JPA
**Database & strumenti**: SQL relazionale, JWT, Git/GitHub
**Soft skill**: lavoro di squadra, metodo Agile Scrum, ricerca e valutazione delle fonti

---

*Progetto realizzato nell'ambito del corso Junior Java Developer di Generation Italy.*
