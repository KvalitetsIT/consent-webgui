FROM adoptopenjdk/openjdk13-openj9:x86_64-alpine-jre-13.0.2_8_openj9-0.18.0
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
