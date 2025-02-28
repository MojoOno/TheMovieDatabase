# Java Applikation - Film Database

## Introduktion
Dette er en Java-applikation, der interagerer med en film-database via en ekstern API og en lokal database ved hjælp af Hibernate ORM. Applikationen henter filmdata fra API'en, gemmer dem i databasen og udfører forespørgsler på disse data.

## Funktionalitet
### Hovedkomponenter
- **APIReaderService**: Henter data fra en ekstern API og konverterer JSON-responser til Java-objekter.
- **DBReaderService**: Interagerer med den lokale database via Hibernate og udfører forespørgsler som gennemsnitlig rating og de mest/ mindst populære film.
- **HibernateConfig**: Konfigurerer Hibernate og opretter en `EntityManagerFactory`.
- **Main**: Hovedklassen, hvor applikationen starter, initialiserer services og behandler filmdata.
- **MovieService**: Kombinerer funktionaliteten fra `APIReaderService` og `DBReaderService` for at håndtere filmdata.

## Branch-struktur
Vi har oprettet en branch kaldet **test-kode**, hvor vi tester backend-funktionaliteten i henhold til opgavebeskrivelsen.

På grund af tidsbegrænsninger har vi foretaget følgende ændringer:
- Flyttet metoder fra DAO-klassen til `DBReaderService`.
- Indset, at vi burde have kaldt DAO-metoderne gennem en anden serviceklasse, men vi opdagede det for sent.
- Programmet opfylder alle krav, bortset fra case insensitivity i søgning på `String title`. Det kan være en bedre løsning at tillade søgning på `String keyword` i stedet.
- Branchen, der gennemgås i review på mandag, er **test-kode**.

## Opsætning af api_key
For at køre applikationen kræves en API-nøgle. Denne skal sættes op som en environment-variabel:

### IntelliJ
1. Gå til **Run > Edit Configurations**.
2. Under **Environment variables**, tilføj:
   ```shell
   api_key=din_api_nøgle
   ```

## Kørsel af applikationen
1. **Første gang programmet køres:**
    - Kør `service.createMovies()` for at hente og gemme filmdata.
    - Derefter skal denne linje **udkommenteres** for at undgå gentagelser.
    - Sæt `hibernateConfig` til `update` i stedet for `create` for at bevare data.

2. **Start applikationen via IntelliJ**
    - Sørg for, at API-nøglen er konfigureret.
    - Kør `Main` klassen for at starte applikationen.

## Teknologier
- **Java 17+**
- **Hibernate ORM**
- **Jackson til JSON-parsing**
- **PostgreSQL / MySQL** (eller en anden understøttet database)

## Forbedringsmuligheder
- Implementere case-insensitive søgning.
- Adskille DAO-metoder i en dedikeret serviceklasse for bedre arkitektur.
- Flere testcases for robusthed.

## Gruppemedlemmer
- **Idris Isci**
- **André Samuelsen**
- **Mathias Falcham**
- **Frederik Franck**