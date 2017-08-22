FROM openjdk:8u111-jre-alpine
RUN apk update && \
    apk upgrade && \
    apk add --update gettext tzdata

COPY  entrypoint.sh /
RUN chmod +x entrypoint.sh
COPY config /consent
ADD /maven/consent-webgui.war /app/consent-webgui.war
COPY images /app/images
WORKDIR /app
ENTRYPOINT [ "/entrypoint.sh" ]
