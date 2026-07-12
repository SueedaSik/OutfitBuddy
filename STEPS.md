# OutfitBuddy – Wie das Projekt entstanden ist

Hier zeige ich wie das Projekt entstanden ist, von der ersten Datei bis zur fertigen App.

<br>


## Schritt 1: Projektstruktur mit Claude Code erstellt

Am Anfang stand die Frage: Wie baue ich das Projekt am besten auf? Ich habe mit Claude Code ein sauberes Grundgerüst erstellt:

- Spring Boot 3.3.2 mit Java 17 als Basis gewählt
- Maven als Build-Tool konfiguriert, damit sich Abhängigkeiten leicht verwalten lassen
- Die typische Projektstruktur nach Maven-Standard angelegt:
  - `src/main/java` – hierher gehören alle Java-Dateien
  - `src/main/resources` – Konfigurationsdateien, Datenbank-Startwerte und das Frontend
  - `src/test/java` – für spätere Tests
- Erste wichtige Dateien erstellt: `pom.xml` (die Steuerzentrale von Maven), `.gitignore` und die Startklasse `OutfitBuddyApplication.java`

Damit war das Fundament fertig und das Projekt ließ sich bereits starten.

<br>

**Claude Code wird installiert:**

<img width="1348" height="434" alt="Bild 09 07 26 um 19 04" src="https://github.com/user-attachments/assets/2724d25e-c684-4569-8b12-784516dde3ca" />

<br>

##

<br>



**Claude Code erstellt alle Projektdateien:**

<img width="2972" height="572" alt="Bild 09 07 26 um 19 14" src="https://github.com/user-attachments/assets/6d5b8b9a-21af-4507-bbc7-c75ab328d8ea" />

<br>
<br>

##


**Claude Code gestartet - eingeloggt als suedasik05@gmail.com:**

<img width="2972" height="444" alt="Bild 09 07 26 um 19 22" src="https://github.com/user-attachments/assets/0379551d-b956-4a3d-ab95-1bb81d59c7ad" />

<br>
<br>

##


**Maven und Java werden geprüft:**

<img width="2474" height="1536" alt="Bild 09 07 26 um 19 23" src="https://github.com/user-attachments/assets/6935f980-b2dd-4654-bbf3-acb08264a1d4" />

<br>
<br>

##


**Kilo Code erklärt die Projektstruktur (Backend):**

<img width="1722" height="852" alt="Bild 11 07 26 um 17 28" src="https://github.com/user-attachments/assets/85d67bec-3c8b-4e61-a9de-3c00e9e9065b" />

<br>
<br>

##


**Kilo Code erklärt die Projektstruktur (Fronted + Konfiguration):**

<img width="1722" height="582" alt="Bild 11 07 26 um 17 28 (1)" src="https://github.com/user-attachments/assets/a85f00ce-526a-4751-87b3-24a11b249834" />

<br>
<br>


## Schritt 2: Backend Spring Boot REST-API

Als Nächstes habe ich das Backend gebaut, also den Teil der im Hintergrund läuft:

- Ein Datenmodell `Outfit.java` angelegt mit den Feldern `id`, `name`, `category` und `lastWorn`. Die `id` wird automatisch vergeben.
- Ein Repository `OutfitRepository.java` erstellt, das alle Datenbankoperationen übernimmt. Ich musste dafür kaum etwas schreiben, weil Spring Data JPA vieles schon mitbringt.
- Eine Service-Schicht `OutfitService.java` gebaut, in der die eigentliche Logik steckt: Outfits suchen, anlegen, ändern und löschen.
- Den REST-Controller `OutfitController.java` geschrieben. Das ist die Schnittstelle nach außen, über die das Frontend später mit dem Backend sprechen wird. Es gibt Endpunkte wie:
  - `GET /api/outfits` – alle Outfits abrufen
  - `POST /api/outfits` – ein neues Outfit anlegen
  - `PUT /api/outfits/{id}` – ein Outfit bearbeiten
  - `DELETE /api/outfits/{id}` – ein Outfit löschen
- Eine kleine Fehlerbehandlung hinzugefügt, damit klar wird, wenn ein Outfit nicht gefunden wird.
- Als Datenbank habe ich H2 gewählt, weil sie einfach zu nutzen ist.
- In `data.sql` drei Beispiel-Outfits hinterlegt, damit direkt etwas zu sehen ist, wenn die App startet.

<br>

**Tests laufen erfolgreich - Projekt kompiliert sauber:**

<img width="2900" height="288" alt="Bild 09 07 26 um 19 26" src="https://github.com/user-attachments/assets/d3fdc69f-e104-4e8e-ac23-9ef712fd67d6" />

<br>
<br>

##


**Kilo Code erklärt den OutfitController (Methoden Teil 1):**

<img width="1722" height="674" alt="Bild 11 07 26 um 17 29" src="https://github.com/user-attachments/assets/62819786-57b5-40fc-833e-217e4f28e7c7" />

<br>
<br>

##


**Kilo Code erklärt den OutfitController (Methoden Teil 2):**

<img width="1722" height="750" alt="Bild 11 07 26 um 17 30" src="https://github.com/user-attachments/assets/20743855-79c6-4ad3-8ce2-c6e17eb8cfd8" />

<br>
<br>
<br>
<br>



## Schritt 3: Frontend als separater Java-Prozess für verteilte Architektur

Bisher lief alles in einem Prozess. Ich wollte ausprobieren wie es sich anfühlt wenn Backend und Frontend getrennt sind, so wie bei echten Anwendungen:

- Statt einer monolithischen Anwendung gibt es jetzt zwei eigenständige Java-Prozesse:
  - **Prozess 1: Backend:** Die Spring Boot REST API läuft auf Port 8080 und kümmert sich um alle Datenbankoperationen.
  - **Prozess 2: Frontend-Server:** Ein eigener kleiner Webserver liefert die HTML-Seite, das CSS und das JavaScript auf Port 3000 aus.
- Den `FrontendServer.java` geschrieben. Er nutzt nur Standard-JDK-Bibliotheken (`com.sun.net.httpserver.HttpServer`), braucht also keine extra Abhängigkeiten.
- In `app.js` die API-URL von einem relativen Pfad auf `http://localhost:8080/api/outfits` geändert, damit das Frontend das Backend auch von einem anderen Port aus erreicht.
- Im `OutfitController` CORS aktiviert, damit der Browser Anfragen vom Frontend-Port 3000 zum Backend-Port 8080 zulässt.
- Das Ergebnis: Zwei unabhängige Prozesse mit getrennten Aufgaben. Der Browser lädt die Seite vom Frontend-Server auf Port 3000 und ruft danach eigenständig die API auf dem Backend-Server auf Port 8080 auf. In der Praxis könnten diese sogar auf verschiedenen Servern laufen.

<br>

**App startet erfolgreich auf Port8080:**

<img width="1616" height="200" alt="Bild 09 07 26 um 19 28" src="https://github.com/user-attachments/assets/72f2f984-e5af-4efc-a876-b407ba246f43" />

<br>
<br>

##


**REST-API funktioniert - alle Outfits werden abgerufen:**

<img width="1678" height="280" alt="Bild 09 07 26 um 19 30" src="https://github.com/user-attachments/assets/cadb934f-afb9-4ff1-8aa8-f6c0a2abf455" />

<br>
<br>

##


**Alle CRUD-Operationen funktionieren korrekt:**

<img width="1678" height="58" alt="Bild 09 07 26 um 19 30" src="https://github.com/user-attachments/assets/e53ff612-6064-4972-9f57-773413e71cc3" />

<br>
<br>

##


**Claude Code Zusammenfassung des fertigen Projekts:**

<img width="1924" height="422" alt="Bild 09 07 26 um 19 32" src="https://github.com/user-attachments/assets/3bb3419a-e233-400c-bc02-18eb6e522907" />

<br>
<br>

##


**Kilo Code baut die verteilte Architektur um (Teil 1):**

<img width="1722" height="850" alt="Bild 11 07 26 um 17 49" src="https://github.com/user-attachments/assets/92ec5588-6e3e-4a79-9b0c-4f0d913f6069" />

<br>
<br>

##


**Kilo Code baut die verteilte Architektur um (Teil 2 - Startbefehle):**

<img width="1722" height="690" alt="Bild 11 07 26 um 17 50" src="https://github.com/user-attachments/assets/7f3ca368-1e38-4739-a066-a3b84286787b" />

<br>
<br>

##



**Erste Version der App (lila Design):**

<img width="1722" height="1590" alt="Bild 11 07 26 um 17 50" src="https://github.com/user-attachments/assets/a5603844-d0eb-41d0-9982-72cef8be4dfa" />

<br>
<br>

##


**Finale Version der App (salbeigrünes Design):**

<img width="1722" height="1544" alt="Bild 11 07 26 um 18 00" src="https://github.com/user-attachments/assets/2a93ad56-5ce2-47f0-8efe-60b8a2aed2e0" />

<br>
<br>





## Schritt 4: Design angepasst mit Kilo Code

Die App sollte nicht nur funktionieren, sondern auch gut aussehen. Ich habe mich für einen ruhigen, modernen Look entschieden, der zu einer Fashion-App passt:

- Die Farbe Lila komplett durch Salbeigrün ersetzt:
  - Buttons und Akzente in `#8A9A84` (Salbeigrün)
  - Überschriften in dunkleren Grüntönen (`#3D4F43` und `#4A5D52`)
  - Hintergrund in einem sanften Hellgrün (`#f4f6f3`)
- Das Design minimalistisch gehalten: feine Schatten, abgerundete Ecken, viel Weißraum
- Eine moderne System-Schriftart gewählt, die auf Mac, Windows und Android gleich gut aussieht
- Kleine Details verbessert: sanfte Hover-Effekte, ein dezenter Fokus-Rahmen bei Eingabefeldern und ein zurückhaltender Löschen-Button statt einem knalligen Rot
- Die Dateien sauber getrennt: `index.html` für den Aufbau, `style.css` für das Aussehen

<br>

**Kilo Code passt das Farbschema an:**

<img width="1722" height="388" alt="Bild 11 07 26 um 17 59" src="https://github.com/user-attachments/assets/1b60c77d-bdfc-4ee2-a19a-026f5c596760" />

<br>
<br>

##



**Kilo Code verbessert die Gestaltung:**

<img width="1722" height="412" alt="Bild 11 07 26 um 17 59 (1)" src="https://github.com/user-attachments/assets/d95b3f7d-cc90-4383-a19a-f99345997ee3" />

<br>
<br>

##


**Finale App mit Salbeigrün-Design:**

<img width="1722" height="1544" alt="Bild 11 07 26 um 18 00" src="https://github.com/user-attachments/assets/17210e8a-5982-4ed0-bf10-4132219ada4c" />

<br>
<br>




## Schritt 5: Auf GitHub gepusht

Zum Schluss habe ich alles versioniert und veröffentlicht:

- Alle Änderungen zu einem Commit zusammengefasst
- Die Commit-Message so geschrieben, dass auch andere verstehen, was geändert wurde
- Auf den `main`-Branch von `https://github.com/SueedaSik/OutfitBuddy.git` gepusht
- Commit-Hash: `63178bc`

<br>
<br>

##

**Git Repository wird initialisiert:**

<img width="1924" height="428" alt="Bild 09 07 26 um 19 34" src="https://github.com/user-attachments/assets/b26c8ba1-5a13-4ff2-8a45-a5e8ce1eaf4d" />

<br>
<br>

##




**Erster Commit mit 18 Dateien:**

<img width="1808" height="266" alt="Bild 09 07 26 um 19 35" src="https://github.com/user-attachments/assets/f55c8d36-dc20-4655-a833-3cda59058844" />

<br>
<br>

##




**GitHub Login erfolgreich:**

<img width="750" height="178" alt="Bild 09 07 26 um 19 42" src="https://github.com/user-attachments/assets/68e41f66-44d6-4328-925a-1761043228c8" />

<br>
<br>

##


**GitHub Repository erstellt und Code gepusht:**

<img width="1438" height="392" alt="Bild 09 07 26 um 19 43" src="https://github.com/user-attachments/assets/f82ec1e3-edec-4aa3-81bd-d87dbc1e9c72" />

<br>

##



**Kilo Code erstellt und pusht die STEPS.md:**

<img width="979" height="58" alt="Bild 11 07 26 um 18 18" src="https://github.com/user-attachments/assets/f632cd55-0676-417f-9fe4-4f7f62f5dceb" />

