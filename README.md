# En proxy IdP med samtykke
Denne IdP kan sættes mellem service providers (SPs) og en identity provider (IdP) med det formål at håndtere brugersamtykke.

Løsningen kan prøves af ved at køre
```
mvn clean install (i roden)
./consent-developent/docker-compose up
```

Når alt er startet op kan man tilgå de to apps A og B på URL'er:

http://localhost:8082/appa/
http://localhost:8084/appb/

Login: hansen/secret987

# Andre nyttige metadata

Her finder man apps'enes metadata
http://localhost:8082/appa/saml/metadata
http://localhost:8084/appb/saml/metadata

Her findes consent-sp ens forside
http://localhost:8092/consentidp/module.php/core/frontpage_welcome.php
