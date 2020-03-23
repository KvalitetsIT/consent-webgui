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

Administration af samtykke er deployet i docker-compose setuppet på følgende endpoints:

http://localhost:8100/cag/

http://localhost:8099/cwg/


# Andre nyttige metadata

Her finder man apps'enes metadata
http://localhost:8082/appa/saml/metadata

http://localhost:8084/appb/saml/metadata

Her findes consent-sp ens forside
http://localhost:8092/consentidp/module.php/core/frontpage_welcome.php


 insert into consent_template (app_id,mime_type,content,friendly_name,notification_subject,version,active,municipality_id)  select app_id,mime_type,content,friendly_name,notification_subject,version,active,573 from consent_template;

mysql> select id, app_id,mime_type,friendly_name,notification_subject,version,active,municipality_id from consent_template;
