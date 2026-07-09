# OutfitBuddy

REST-API zur Verwaltung von Outfits, gebaut mit Spring Boot.

## Module

- **Frontend**: statisches HTML/CSS/JS unter `src/main/resources/static`
- **Backend**: Spring Boot Controller & Service unter `src/main/java/com/outfitbuddy`
- **Datenbank**: H2 In-Memory-Datenbank

## Outfit-Felder

- `name` – Name des Outfits
- `category` – Kategorie (z.B. Casual, Business, Sport)
- `lastWorn` – Datum, an dem das Outfit zuletzt getragen wurde

## Starten

```bash
./mvnw spring-boot:run
```

Die App läuft dann unter http://localhost:8080

- Frontend: http://localhost:8080
- REST-API: http://localhost:8080/api/outfits
- H2-Konsole: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:outfitbuddy`, User: `sa`, kein Passwort)

## API-Endpunkte

| Methode | Pfad                | Beschreibung             |
|---------|---------------------|---------------------------|
| GET     | /api/outfits         | Alle Outfits abrufen      |
| GET     | /api/outfits/{id}    | Ein Outfit abrufen        |
| POST    | /api/outfits         | Neues Outfit anlegen      |
| PUT     | /api/outfits/{id}    | Outfit aktualisieren      |
| DELETE  | /api/outfits/{id}    | Outfit löschen            |
