# consent-webgui
Consent-webgui modulet, der tidligere lå i _consent_ projektet.

Fra _consent_ projektet: Denne IdP kan sættes mellem service providers (SPs) og en identity provider (IdP) med det formål at håndtere brugersamtykke.

## Test
I mappen _consent-compose_ findes et docker compose setup, hvorfra servicen kan testes. Dvs. fra roden af projektet køres
```
cd consent-compose/
docker compose up
```

Der udstilles følgende fire endpoints
* _http://localhost:8082/appa_ hørende til App A.
* _http://localhost:8084/appb_ hørende til App B.
* _http://localhost:8099/cwg_ hørende til administration af samtykke for borgeren.
* _http://localhost:8100/cag_ hørende til administration af samtykker til apps.


### App A
Kræver login ved en af de tre brugere. Efter login bliver man bedt om at afgive samtykke, hvis brugeren har samme kommune
som _FLYWAY\_PLACEHOLDERS\_MUNICIPALITY_ i _consent-service_ er sat til. Ellers bliver man blot logget direkte ind.

Man kan vælge ikke at afgive samtykke, hvorved man får mulighed for at logge ud af App A.

### App B
Kræver login ved en af de tre brugere. Herefter bliver man sendt direkte til appen, dvs. der kræves ikke samtykke.

### Administration af samtykke for borgeren
Kræver login ved en af de tre brugere. Efter login kan man se, hvilke samtykker denne bruger har afgivet. Hvis der er
afgivet et samtykke, kan man læse det og trække det tilbage. Trækker man samtykket tilbage, vil man i App A blive bedt
om at give det igen.

### Administration af samtykker til apps
Kræver ikke login. Her kan man se, hvilke samtykker der ligger på de forskellige apps. Man kan læse samtykkerne samt
erstatte dem. Ændres samtykket for App A (dev:kit:appa) vil man ikke længere kunne se, at der er afgivet samtykke 
under Administration af samtykke for borgeren, og ligeledes vil man blive bedt om at afgive samtykke i App A igen.

Ændres samtykket for dev:kit:api bør der ikke ske noget ved de øvrige endpoints.

### Brugere
Der er tre brugere
* Rita Nærø Ågesen Hansen, Esbjerg Kommune (561). 
  * Login: hansen/secret987
* Rune F. Petersen, Esbjerg Kommune (561). 
  * Login: petersen/secret987
* Nancy Ann Berggren, Fanø Kommune (563). 
  * Login: nancy/nancy

### Konfiguration
Ved at rette _FLYWAY\_PLACEHOLDERS\_MUNICIPALITY_ i _consent-service_ til en af de øvrige kommunekoder ændrer man, hvilken
kommune samtykkerne hører til under, og derved hvilken bruger, der skal afgive samtykke.

#### Uden kommuner
Der kan testes uden kommuner ved at rette _FLYWAY\_PLACEHOLDERS\_MUNICIPALITY_ i _consent-service_ til _0_ og fjerne
_SP\_SERVICE\_URL_ fra _consent-idp_.

Ved denne test vil alle brugere nu blive bedt om at afgive samtykke ved App A.

### Andre nyttige metadata

Her finder man apps'enes metadata
http://localhost:8082/appa/saml/metadata

http://localhost:8084/appb/saml/metadata

Her findes consent-sp ens forside
http://localhost:8092/consentidp/module.php/core/frontpage_welcome.php


insert into consent_template (app_id,mime_type,content,friendly_name,notification_subject,version,active,municipality_id)  select app_id,mime_type,content,friendly_name,notification_subject,version,active,573 from consent_template;

mysql> select id, app_id,mime_type,friendly_name,notification_subject,version,active,municipality_id from consent_template;


## Konfiguration

| Environment variable | Beskrivelse                                                                                                     | Påkrævet |
|----------------------|-----------------------------------------------------------------------------------------------------------------|----------|
| CONSENTSERVICE_URL   | Url til consent-service.                                                                                        | Ja       |
| CONTEXT              | Context path.                                                                                                   | Ja       |
| SERVER_PORT          | Server port. Defaulter til 8080.                                                                                | Nej      |
| LOG_LEVEL            | Log Level til applikation log. Defaulter til INFO.                                                              | Nej      |
| LOG_LEVEL_FRAMEWORK  | Log level til framework. Defaulter to INFO.                                                                     | Nej      |
| CORRELATION_ID       | HTTP header til at få correlation id fra. Benyttes til at korrelere log-beskeder. Defaulter til "x-request-id". | Nej      |
| SERVICE_ID           | Service id til log-beskeder. Defaulter til "consent-service".                                                   | Nej      |
