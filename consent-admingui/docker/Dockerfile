FROM openjdk:8u212-jre-alpine
RUN apk update && \
    apk upgrade && \
    apk add --update gettext tzdata

COPY  entrypoint.sh /
RUN chmod +x entrypoint.sh
COPY config /consent
ADD /maven/consent-admingui.war /app/consent-admingui.war
COPY images /app/images
WORKDIR /app
ENTRYPOINT [ "/entrypoint.sh" ]
