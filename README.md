# OutfitBuddy

OutfitBuddy ist eine einfache Web-App zum Verwalten von Outfits. Man kann Kleidungsstücke benennen, kategorisieren und festhalten, wann man sie zuletzt getragen hat. Die App wurde mit Java und Spring Boot gebaut und folgt einer echten verteilten Architektur mit zwei separaten Java-Prozessen.

**App starten und aufrufen:** http://localhost:3000

<br>


## Was kann die App?

- **Outfits anlegen:** Name, Kategorie und Datum speichern
- **Übersicht behalten:** Alle Outfits in einer Tabelle anzeigen
- **Bearbeiten und löschen:** Einträge direkt in der Oberfläche ändern oder entfernen
- **Daten bleiben im Speicher:** Die H2-Datenbank speichert alles während der Laufzeit

<br>

## Wie funktioniert die Architektur?

OutfitBuddy besteht aus zwei Java-Prozessen, die über HTTP miteinander reden.

<br>

### Prozess 1: Backend (Port 8080)

Das Backend ist eine Spring Boot REST-API. Es kümmert sich um:

- Die Datenbank (H2 In-Memory)
- Die Geschäftslogik (Service-Schicht)
- Die REST-Endpunkte, über die das Frontend Daten abrufen und ändern kann

<br>


### Prozess 2: Frontend-Server (Port 3000)

Der Frontend-Server ist ein eigener kleiner Java-HTTP-Server. Er tut nichts anderes, als die statischen Dateien auszuliefern:

- `index.html` – der Aufbau der Seite
- `css/style.css` – das Aussehen
- `js/app.js` – die Logik im Browser

Das JavaScript im Browser spricht dann über HTTP mit dem Backend auf Port 8080, um Daten zu laden, anzulegen, zu ändern oder zu löschen.

<br>


### Warum zwei Prozesse?

So lässt sich Frontend und Backend unabhängig voneinander entwickeln und austauschen. In der Praxis laufen sie sogar auf verschiedenen Servern, lokal läuft es einfach auf zwei verschiedenen Ports.

<br>


## Wie starte ich die App?

Man braucht Java 17 und Maven. Mit dem Maven-Wrapper (mvnw) braucht man nicht mal Maven extra zu installieren.

<br>

### 0. In den Projektordner navigieren

```bash
cd /Users/sueda/OutfitBuddy
```

<br>

### 1. Projekt bauen

```bash
./mvnw clean package -DskipTests
```

<br>

### 2. Backend starten

```bash
java -jar target/outfitbuddy-1.0.0.jar
```

Das Backend läuft jetzt unter **http://localhost:8080/api/outfits**

<br>

### 3. Frontend-Server starten

In einem zweiten Terminal:

```bash
cd /Users/sueda/OutfitBuddy
java -cp target/classes com.outfitbuddy.FrontendServer
```
<br>

Das Frontend ist jetzt unter **http://localhost:3000** erreichbar.

<br>

### 4. Browser öffnen

**http://localhost:3000** – die App ist bereit.

<br>

## Nützliche Adressen

| Dienst | Adresse |
|--------|---------|
| Frontend | http://localhost:3000 |
| REST API | http://localhost:8080/api/outfits |
| H2-Konsole | http://localhost:8080/h2-console |
| Datenbank-Zugang | JDBC URL: `jdbc:h2:mem:outfitbuddy`, User: `sa`, kein Passwort |

<br>

## API-Endpunkte

| Methode | Pfad | Beschreibung |
|---------|------|--------------|
| GET | /api/outfits | Alle Outfits abrufen |
| GET | /api/outfits/{id} | Einzelnes Outfit abrufen |
| POST | /api/outfits | Neues Outfit anlegen |
| PUT | /api/outfits/{id} | Outfit aktualisieren |
| DELETE | /api/outfits/{id} | Outfit löschen |

<br>

## Outfit-Felder

- `name` – Name des Outfits
- `category` – Kategorie (z.B. Casual, Business, Sport)
- `lastWorn` – Datum, an dem das Outfit zuletzt getragen wurde

<br>

## Roadmap

Was ich mir als nächste Schritte vorstellen könnte:

- Foto-Upload für Outfits
- Persistente Datenbank damit die Daten auch nach einem Neustart noch da sind
- Benutzer-Login damit mehrere Personen die App nutzen können

<br>

## Techstack

| Bereich | Technologie |
|---------|-------------|
| Sprache | Java 17 |
| Backend | Spring Boot 3.3.2|
| Datenbank | H2 In-Memory |
| Frontend | HTML, CSS, JavaScript |
| Build-Tool | Maven |
| KI-Tools | Claude Code CLI, Kilo Code|

<br>

## Umsetzung und Validierung

| Aufgabe | Umsetzung | Validierung |
|---------|-----------|-------------|
| REST-API | Spring Boot Controller | Maven Tests erfolgreich |
| Verteilte Architektur | Zwei separate Java-Prozesse | Im Browser getestet |
| Frontend | HTML/CSS/JS + Java HTTP-Server | Auf localhost:3000 getestet |

<br>

## Entstehung und Tools
Die genaue Entstehungsgeschichte steht in der Datei [STEPS.md](STEPS.md).

Für die Entwicklung wurden verwendet:

- **Claude Code CLI** – für die initiale Projektstruktur und das Backend
- **Kilo Code** (VS Code Plugin) – für die spätere Architektur-Umstellung und das Design
