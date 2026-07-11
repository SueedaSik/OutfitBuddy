# OutfitBuddy – Wie das Projekt entstanden ist

Hier dokumentiere ich Schritt für Schritt, wie aus einer kleinen Idee die fertige OutfitBuddy-App geworden ist. Die Schritte sind so beschrieben, dass auch Einsteiger gut mitkommen.

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
- Als Datenbank die H2 In-Memory-Datenbank gewählt – sie ist einfach zu nutzen und braucht keine extra Installation.
- In `data.sql` drei Beispiel-Outfits hinterlegt, damit direkt etwas zu sehen ist, wenn die App startet.

## Schritt 3: Frontend als separater Java-Prozess für verteilte Architektur

Bisher lief alles in einem Prozess. Ich wollte ausprobieren wie es sich anfühlt wenn Backend und Frontend getrennt sind, so wie bei echten Anwendungen:

- Statt einer monolithischen Anwendung gibt es jetzt zwei eigenständige Java-Prozesse:
  - **Prozess 1: Backend:** Die Spring Boot REST API läuft auf Port 8080 und kümmert sich um alle Datenbankoperationen.
  - **Prozess 2: Frontend-Server:** Ein eigener kleiner Webserver liefert die HTML-Seite, das CSS und das JavaScript auf Port 3000 aus.
- Den `FrontendServer.java` geschrieben. Er nutzt nur Standard-JDK-Bibliotheken (`com.sun.net.httpserver.HttpServer`), braucht also keine extra Abhängigkeiten.
- In `app.js` die API-URL von einem relativen Pfad auf `http://localhost:8080/api/outfits` geändert, damit das Frontend das Backend auch von einem anderen Port aus erreicht.
- Im `OutfitController` CORS aktiviert, damit der Browser Anfragen vom Frontend-Port 3000 zum Backend-Port 8080 zulässt.
- Das Ergebnis: Zwei Prozesse die über HTTP miteinander reden. In der Praxis laufen sie sogar auf verschiedenen Servern.

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

## Schritt 5: Auf GitHub gepusht

Zum Schluss habe ich alles versioniert und veröffentlicht:

- Alle Änderungen zu einem Commit zusammengefasst
- Die Commit-Message so geschrieben, dass auch andere verstehen, was geändert wurde
- Auf den `main`-Branch von `https://github.com/SueedaSik/OutfitBuddy.git` gepusht
- Commit-Hash: `63178bc`
